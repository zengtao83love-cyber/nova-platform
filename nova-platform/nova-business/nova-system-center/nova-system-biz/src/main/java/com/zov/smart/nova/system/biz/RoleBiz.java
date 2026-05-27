package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.role.UpdateRoleCommand;
import com.zov.smart.nova.system.api.dto.role.RoleDTO;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;
import com.zov.smart.nova.system.api.vo.role.RoleVO;

import java.util.List;

/** Business orchestration boundary for role management. */
public interface RoleBiz {
    RoleVO getRoleDetail(Long roleId);
    RoleDTO getRoleById(Long roleId);
    List<RoleDTO> listRolesByUserId(Long userId);
    List<RoleDTO> listEnabledRolesByUserId(Long userId);
    PageResult<RoleVO> pageRoles(RolePageQuery query);
    RoleVO createRole(CreateRoleCommand command);
    RoleVO updateRole(UpdateRoleCommand command);
    void deleteRole(Long roleId);
    void assignMenus(AssignRoleMenusCommand command);
    void changeRoleStatus(Long roleId, String status);
}
