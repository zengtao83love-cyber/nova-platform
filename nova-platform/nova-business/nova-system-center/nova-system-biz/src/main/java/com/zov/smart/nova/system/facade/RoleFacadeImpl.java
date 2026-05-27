package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.role.UpdateRoleCommand;
import com.zov.smart.nova.system.api.dto.role.RoleDTO;
import com.zov.smart.nova.system.api.facade.RoleFacade;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;
import com.zov.smart.nova.system.biz.RoleBiz;
import org.springframework.stereotype.Service;

import java.util.List;

/** Role facade implementation. It does not access Mapper directly. */
@Service
public class RoleFacadeImpl implements RoleFacade {

    private final RoleBiz roleBiz;

    public RoleFacadeImpl(RoleBiz roleBiz) {
        this.roleBiz = roleBiz;
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        return roleBiz.getRoleById(roleId);
    }

    @Override
    public List<RoleDTO> listRolesByUserId(Long userId) {
        return roleBiz.listRolesByUserId(userId);
    }

    @Override
    public List<RoleDTO> listEnabledRolesByUserId(Long userId) {
        return roleBiz.listEnabledRolesByUserId(userId);
    }

    @Override
    public PageResult<RoleDTO> pageRoles(RolePageQuery query) {
        PageResult<com.zov.smart.nova.system.api.vo.role.RoleVO> page = roleBiz.pageRoles(query);
        java.util.List<RoleDTO> records = new java.util.ArrayList<RoleDTO>();
        for (com.zov.smart.nova.system.api.vo.role.RoleVO item : page.getRecords()) {
            RoleDTO dto = new RoleDTO();
            dto.setId(item.getId());
            dto.setRoleCode(item.getRoleCode());
            dto.setRoleName(item.getRoleName());
            dto.setSortOrder(item.getSortOrder());
            dto.setStatus(item.getStatus());
            dto.setRemark(item.getRemark());
            dto.setMenuIds(item.getMenuIds());
            dto.setCreatedAt(item.getCreatedAt());
            dto.setUpdatedAt(item.getUpdatedAt());
            records.add(dto);
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public Long createRole(CreateRoleCommand command) {
        return roleBiz.createRole(command).getId();
    }

    @Override
    public void updateRole(UpdateRoleCommand command) {
        roleBiz.updateRole(command);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleBiz.deleteRole(roleId);
    }

    @Override
    public void assignMenus(AssignRoleMenusCommand command) {
        roleBiz.assignMenus(command);
    }

    @Override
    public void changeRoleStatus(Long roleId, String status) {
        roleBiz.changeRoleStatus(roleId, status);
    }
}
