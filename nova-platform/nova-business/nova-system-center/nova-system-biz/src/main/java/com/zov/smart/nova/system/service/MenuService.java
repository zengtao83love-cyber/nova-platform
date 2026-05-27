package com.zov.smart.nova.system.service;

import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/** Atomic menu persistence service. */
public interface MenuService {
    SysMenuDO findById(Long menuId);
    SysMenuDO getByIdRequired(Long menuId);
    List<SysMenuDO> listMenus(MenuTreeQuery query);
    List<SysMenuDO> listMenusByUserId(Long userId);
    List<SysMenuDO> listMenusByRoleId(Long roleId);
    Set<String> listPermissionCodesByUserId(Long userId);
    boolean existsAllMenuIds(Collection<Long> menuIds);
    boolean existsPermissionCode(String permissionCode, Long excludeId);
    int countChildren(Long parentId);
    Long createMenu(SysMenuDO menu);
    void updateMenu(SysMenuDO menu);
    void logicDeleteMenu(Long menuId);
}
