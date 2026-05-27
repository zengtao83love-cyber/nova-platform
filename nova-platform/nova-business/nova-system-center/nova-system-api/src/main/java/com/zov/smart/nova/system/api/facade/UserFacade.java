package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.command.user.ResetPasswordCommand;
import com.zov.smart.nova.system.api.command.user.UpdateUserCommand;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import java.util.List;

/** User contract exposed by system center. */
public interface UserFacade {
    UserDTO getUserById(Long userId);
    UserDTO getUserByUsername(String username);
    List<UserDTO> listUsersByIds(List<Long> userIds);
    PageResult<UserDTO> pageUsers(UserPageQuery query);
    boolean existsUser(Long userId);
    boolean isUserEnabled(Long userId);
    Long createUser(CreateUserCommand command);
    void updateUser(UpdateUserCommand command);
    void deleteUser(Long userId);
    void resetPassword(ResetPasswordCommand command);
    void assignRoles(AssignUserRolesCommand command);
    void changeUserStatus(Long userId, String status);
    void unlockUser(Long userId);
}
