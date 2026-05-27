package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.role.UpdateRoleCommand;
import com.zov.smart.nova.system.api.dto.role.RoleDTO;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;
import java.util.List;

/** Role contract exposed by system center. */
public interface RoleFacade {
    RoleDTO getRoleById(Long roleId);
    List<RoleDTO> listRolesByUserId(Long userId);
    List<RoleDTO> listEnabledRolesByUserId(Long userId);
    PageResult<RoleDTO> pageRoles(RolePageQuery query);
    Long createRole(CreateRoleCommand command);
    void updateRole(UpdateRoleCommand command);
    void deleteRole(Long roleId);
    void assignMenus(AssignRoleMenusCommand command);
    void changeRoleStatus(Long roleId, String status);
}
