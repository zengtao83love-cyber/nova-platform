package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.context.LoginUserContext;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import com.zov.smart.nova.infra.security.service.SecurityCacheService;
import com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.command.user.ResetPasswordCommand;
import com.zov.smart.nova.system.api.command.user.UpdateUserCommand;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import com.zov.smart.nova.system.api.vo.user.UserDetailVO;
import com.zov.smart.nova.system.api.vo.user.UserPageVO;
import com.zov.smart.nova.system.biz.UserBiz;
import com.zov.smart.nova.system.converter.UserConverter;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.RoleService;
import com.zov.smart.nova.system.service.UserService;
import com.zov.smart.nova.system.support.StatusValidators;
import com.zov.smart.nova.system.support.TransactionAfterCommitExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User business orchestration. All user write transactions are declared here.
 */
@Service
public class UserBizImpl implements UserBiz {

    private final UserService userService;
    private final RoleService roleService;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final SecurityCacheService securityCacheService;

    public UserBizImpl(UserService userService,
                       RoleService roleService,
                       UserConverter userConverter,
                       PasswordEncoder passwordEncoder,
                       SecurityCacheService securityCacheService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.securityCacheService = securityCacheService;
    }

    @Override
    public UserDetailVO getUserDetail(Long userId) {
        SysUserDO user = userService.getByIdRequired(userId);
        return userConverter.toDetailVO(user, userService.listRoleIds(userId));
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userConverter.toDTO(userService.getByIdRequired(userId));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        SysUserDO user = userService.getByUsername(username);
        if (user == null) {
            throw new BusinessException(SystemErrorCode.USER_NOT_FOUND);
        }
        return userConverter.toDTO(user);
    }

    @Override
    public List<UserDTO> listUsersByIds(List<Long> userIds) {
        return userConverter.toDTOList(userService.listByIds(userIds));
    }

    @Override
    public PageResult<UserPageVO> pageUsers(UserPageQuery query) {
        PageResult<SysUserDO> page = userService.pageUsers(query);
        List<UserPageVO> records = new ArrayList<UserPageVO>();
        for (SysUserDO item : page.getRecords()) {
            records.add(userConverter.toPageVO(item));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public boolean existsUser(Long userId) {
        return userService.existsUser(userId);
    }

    @Override
    public boolean isUserEnabled(Long userId) {
        SysUserDO user = userService.findById(userId);
        return user != null && UserStatusEnum.ENABLED.equals(user.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailVO createUser(CreateUserCommand command) {
        assertUsernameAvailable(command.getUsername(), null);
        SysUserDO user = userConverter.toCreateDO(command);
        user.setPassword(passwordEncoder.encode(command.getPassword()));
        Long userId = userService.createUser(user);
        List<Long> roleIds = command.getRoleIds() == null ? Collections.<Long>emptyList() : command.getRoleIds();
        if (!roleService.existsAllRoleIds(roleIds)) {
            throw new BusinessException(SystemErrorCode.ROLE_NOT_FOUND);
        }
        userService.replaceUserRoles(userId, roleIds);
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearUserPermissions(userId);
                securityCacheService.clearUserMenus(userId);
            }
        });
        return userConverter.toDetailVO(userService.getByIdRequired(userId), userService.listRoleIds(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetailVO updateUser(UpdateUserCommand command) {
        SysUserDO user = userService.getByIdRequired(command.getId());
        userConverter.updateDO(command, user);
        userService.updateUser(user);
        if (UserStatusEnum.DISABLED.equals(user.getStatus())) {
            protectSelfDisable(user.getId());
            protectLastEnabledSuperAdmin(user);
            scheduleUserDisabledCacheEviction(user.getId());
        } else {
            scheduleUserPermissionEviction(user.getId());
        }
        return userConverter.toDetailVO(userService.getByIdRequired(user.getId()), userService.listRoleIds(user.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        SysUserDO user = userService.getByIdRequired(userId);
        protectSelfDelete(userId);
        protectSuperAdminDelete(user);
        userService.logicDeleteUser(userId);
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearUserAccessTokens(userId);
                securityCacheService.clearUserPermissions(userId);
                securityCacheService.clearUserMenus(userId);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(ResetPasswordCommand command) {
        userService.getByIdRequired(command.getUserId());
        userService.updatePassword(command.getUserId(), passwordEncoder.encode(command.getNewPassword()));
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearUserAccessTokens(command.getUserId());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(AssignUserRolesCommand command) {
        userService.getByIdRequired(command.getUserId());
        List<Long> roleIds = command.getRoleIds() == null ? Collections.<Long>emptyList() : command.getRoleIds();
        if (!roleService.existsAllRoleIds(roleIds)) {
            throw new BusinessException(SystemErrorCode.ROLE_NOT_FOUND);
        }
        userService.replaceUserRoles(command.getUserId(), roleIds);
        scheduleUserPermissionEviction(command.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeUserStatus(final Long userId, String status) {
        SysUserDO user = userService.getByIdRequired(userId);
        UserStatusEnum targetStatus = StatusValidators.requiredUserStatus(status);
        if (UserStatusEnum.DISABLED.equals(targetStatus)) {
            protectSelfDisable(userId);
            protectLastEnabledSuperAdmin(user);
        }
        userService.updateStatus(userId, targetStatus);
        if (UserStatusEnum.DISABLED.equals(targetStatus)) {
            scheduleUserDisabledCacheEviction(userId);
        } else {
            scheduleUserPermissionEviction(userId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockUser(final Long userId) {
        userService.getByIdRequired(userId);
        userService.updateLoginLockFlag(userId, 0);
    }

    private void assertUsernameAvailable(String username, Long excludeUserId) {
        SysUserDO existing = userService.getByUsername(username);
        if (existing == null) {
            return;
        }
        if (excludeUserId == null || !excludeUserId.equals(existing.getId())) {
            throw new BusinessException(SystemErrorCode.USER_USERNAME_EXISTS);
        }
    }

    private void protectSelfDelete(Long targetUserId) {
        Long currentUserId = LoginUserContext.currentUserId();
        if (currentUserId != null && currentUserId.equals(targetUserId)) {
            throw new BusinessException(SystemErrorCode.USER_SUPER_ADMIN_PROTECTED, "不允许删除当前登录用户");
        }
    }

    private void protectSelfDisable(Long targetUserId) {
        Long currentUserId = LoginUserContext.currentUserId();
        if (currentUserId != null && currentUserId.equals(targetUserId)) {
            throw new BusinessException(SystemErrorCode.USER_SUPER_ADMIN_PROTECTED, "不允许禁用当前登录用户");
        }
    }

    private void protectSuperAdminDelete(SysUserDO user) {
        if (Integer.valueOf(1).equals(user.getSuperAdminFlag())) {
            throw new BusinessException(SystemErrorCode.USER_SUPER_ADMIN_PROTECTED);
        }
    }

    private void protectLastEnabledSuperAdmin(SysUserDO user) {
        if (!Integer.valueOf(1).equals(user.getSuperAdminFlag())) {
            return;
        }
        if (userService.countEnabledSuperAdmins() <= 1) {
            throw new BusinessException(SystemErrorCode.USER_LAST_ADMIN_FORBIDDEN);
        }
    }

    private void scheduleUserPermissionEviction(final Long userId) {
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearUserPermissions(userId);
                securityCacheService.clearUserMenus(userId);
            }
        });
    }

    private void scheduleUserDisabledCacheEviction(final Long userId) {
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearUserAccessTokens(userId);
                securityCacheService.clearUserPermissions(userId);
                securityCacheService.clearUserMenus(userId);
            }
        });
    }
}
