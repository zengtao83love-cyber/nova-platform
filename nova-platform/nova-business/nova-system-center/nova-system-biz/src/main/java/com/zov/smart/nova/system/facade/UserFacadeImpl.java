package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.command.user.ResetPasswordCommand;
import com.zov.smart.nova.system.api.command.user.UpdateUserCommand;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.facade.UserFacade;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import com.zov.smart.nova.system.biz.UserBiz;
import org.springframework.stereotype.Service;

import java.util.List;

/** User facade implementation. It does not access Mapper directly. */
@Service
public class UserFacadeImpl implements UserFacade {

    private final UserBiz userBiz;

    public UserFacadeImpl(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userBiz.getUserById(userId);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userBiz.getUserByUsername(username);
    }

    @Override
    public List<UserDTO> listUsersByIds(List<Long> userIds) {
        return userBiz.listUsersByIds(userIds);
    }

    @Override
    public PageResult<UserDTO> pageUsers(UserPageQuery query) {
        PageResult<com.zov.smart.nova.system.api.vo.user.UserPageVO> page = userBiz.pageUsers(query);
        java.util.List<UserDTO> records = new java.util.ArrayList<UserDTO>();
        for (com.zov.smart.nova.system.api.vo.user.UserPageVO item : page.getRecords()) {
            UserDTO dto = new UserDTO();
            dto.setId(item.getId());
            dto.setUsername(item.getUsername());
            dto.setRealName(item.getRealName());
            dto.setNickname(item.getNickname());
            dto.setMobile(item.getMobile());
            dto.setEmail(item.getEmail());
            //dto.setAvatar(item.getAvatar());
            dto.setStatus(item.getStatus());
            dto.setSuperAdminFlag(item.getSuperAdminFlag());
            dto.setLastLoginAt(item.getLastLoginAt());
            records.add(dto);
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public boolean existsUser(Long userId) {
        return userBiz.existsUser(userId);
    }

    @Override
    public boolean isUserEnabled(Long userId) {
        return userBiz.isUserEnabled(userId);
    }

    @Override
    public Long createUser(CreateUserCommand command) {
        return userBiz.createUser(command).getId();
    }

    @Override
    public void updateUser(UpdateUserCommand command) {
        userBiz.updateUser(command);
    }

    @Override
    public void deleteUser(Long userId) {
        userBiz.deleteUser(userId);
    }

    @Override
    public void resetPassword(ResetPasswordCommand command) {
        userBiz.resetPassword(command);
    }

    @Override
    public void assignRoles(AssignUserRolesCommand command) {
        userBiz.assignRoles(command);
    }

    @Override
    public void changeUserStatus(Long userId, String status) {
        userBiz.changeUserStatus(userId, status);
    }

    @Override
    public void unlockUser(Long userId) {
        userBiz.unlockUser(userId);
    }
}
