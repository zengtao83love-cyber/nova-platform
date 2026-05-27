package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.command.user.ResetPasswordCommand;
import com.zov.smart.nova.system.api.command.user.UpdateUserCommand;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import com.zov.smart.nova.system.api.vo.user.UserDetailVO;
import com.zov.smart.nova.system.api.vo.user.UserPageVO;

import java.util.List;

/** Business orchestration boundary for user management. */
public interface UserBiz {
    UserDetailVO getUserDetail(Long userId);
    UserDTO getUserById(Long userId);
    UserDTO getUserByUsername(String username);
    List<UserDTO> listUsersByIds(List<Long> userIds);
    PageResult<UserPageVO> pageUsers(UserPageQuery query);
    boolean existsUser(Long userId);
    boolean isUserEnabled(Long userId);
    UserDetailVO createUser(CreateUserCommand command);
    UserDetailVO updateUser(UpdateUserCommand command);
    void deleteUser(Long userId);
    void resetPassword(ResetPasswordCommand command);
    void assignRoles(AssignUserRolesCommand command);
    void changeUserStatus(Long userId, String status);
    void unlockUser(Long userId);
}
