package com.zov.smart.nova.system.service;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;

import java.util.Collection;
import java.util.List;

/** Atomic persistence operations for sys_role and sys_role_menu. */
public interface RoleService {
    SysRoleDO findById(Long roleId);
    SysRoleDO getByIdRequired(Long roleId);
    List<SysRoleDO> listByIds(Collection<Long> roleIds);
    List<SysRoleDO> listRolesByUserId(Long userId);
    List<SysRoleDO> listEnabledRolesByUserId(Long userId);
    PageResult<SysRoleDO> pageRoles(RolePageQuery query);
    boolean existsRole(Long roleId);
    boolean existsAllRoleIds(Collection<Long> roleIds);
    Long createRole(SysRoleDO role);
    void updateRole(SysRoleDO role);
    void logicDeleteRole(Long roleId);
    void updateStatus(Long roleId, RoleStatusEnum status);
    boolean roleCodeExists(String roleCode, Long excludeRoleId);
    boolean isRoleInUse(Long roleId);
    List<Long> listMenuIds(Long roleId);
    void replaceRoleMenus(Long roleId, Collection<Long> menuIds);
    boolean existsAllMenuIds(Collection<Long> menuIds);
    List<Long> listUserIdsByRoleId(Long roleId);
}
