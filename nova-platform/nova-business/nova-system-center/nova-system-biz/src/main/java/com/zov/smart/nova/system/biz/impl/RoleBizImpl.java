package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.infra.security.service.SecurityCacheService;
import com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.role.UpdateRoleCommand;
import com.zov.smart.nova.system.api.dto.role.RoleDTO;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;
import com.zov.smart.nova.system.api.vo.role.RoleVO;
import com.zov.smart.nova.system.biz.RoleBiz;
import com.zov.smart.nova.system.converter.RoleConverter;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.RoleService;
import com.zov.smart.nova.system.support.StatusValidators;
import com.zov.smart.nova.system.support.TransactionAfterCommitExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Role business orchestration. */
@Service
public class RoleBizImpl implements RoleBiz {

    private final RoleService roleService;
    private final RoleConverter roleConverter;
    private final SecurityCacheService securityCacheService;

    public RoleBizImpl(RoleService roleService,
                       RoleConverter roleConverter,
                       SecurityCacheService securityCacheService) {
        this.roleService = roleService;
        this.roleConverter = roleConverter;
        this.securityCacheService = securityCacheService;
    }

    @Override
    public RoleVO getRoleDetail(Long roleId) {
        SysRoleDO role = roleService.getByIdRequired(roleId);
        return roleConverter.toVO(role, roleService.listMenuIds(roleId));
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        return roleConverter.toDTO(roleService.getByIdRequired(roleId), roleService.listMenuIds(roleId));
    }

    @Override
    public List<RoleDTO> listRolesByUserId(Long userId) {
        return roleConverter.toDTOList(roleService.listRolesByUserId(userId));
    }

    @Override
    public List<RoleDTO> listEnabledRolesByUserId(Long userId) {
        return roleConverter.toDTOList(roleService.listEnabledRolesByUserId(userId));
    }

    @Override
    public PageResult<RoleVO> pageRoles(RolePageQuery query) {
        PageResult<SysRoleDO> page = roleService.pageRoles(query);
        List<RoleVO> records = new ArrayList<RoleVO>();
        for (SysRoleDO item : page.getRecords()) {
            records.add(roleConverter.toVO(item, Collections.<Long>emptyList()));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleVO createRole(CreateRoleCommand command) {
        if (roleService.roleCodeExists(command.getRoleCode(), null)) {
            throw new BusinessException(SystemErrorCode.ROLE_CODE_EXISTS);
        }
        SysRoleDO role = roleConverter.toCreateDO(command);
        Long roleId = roleService.createRole(role);
        return roleConverter.toVO(roleService.getByIdRequired(roleId), roleService.listMenuIds(roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleVO updateRole(UpdateRoleCommand command) {
        SysRoleDO role = roleService.getByIdRequired(command.getId());
        roleConverter.updateDO(command, role);
        roleService.updateRole(role);
        scheduleRoleRelatedPermissionEviction(role.getId());
        return roleConverter.toVO(roleService.getByIdRequired(role.getId()), roleService.listMenuIds(role.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {
        roleService.getByIdRequired(roleId);
        if (roleService.isRoleInUse(roleId)) {
            throw new BusinessException(SystemErrorCode.ROLE_IN_USE);
        }
        roleService.logicDeleteRole(roleId);
        scheduleRoleRelatedPermissionEviction(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenus(AssignRoleMenusCommand command) {
        roleService.getByIdRequired(command.getRoleId());
        List<Long> menuIds = command.getMenuIds() == null ? Collections.<Long>emptyList() : command.getMenuIds();
        if (!roleService.existsAllMenuIds(menuIds)) {
            throw new BusinessException(SystemErrorCode.MENU_NOT_FOUND);
        }
        roleService.replaceRoleMenus(command.getRoleId(), menuIds);
        scheduleRoleRelatedPermissionEviction(command.getRoleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeRoleStatus(final Long roleId, String status) {
        roleService.getByIdRequired(roleId);
        RoleStatusEnum targetStatus = StatusValidators.requiredRoleStatus(status);
        roleService.updateStatus(roleId, targetStatus);
        scheduleRoleRelatedPermissionEviction(roleId);
    }

    private void scheduleRoleRelatedPermissionEviction(final Long roleId) {
        final List<Long> affectedUserIds = roleService.listUserIdsByRoleId(roleId);
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearRoleRelatedPermissions(roleId, affectedUserIds);
            }
        });
    }
}
