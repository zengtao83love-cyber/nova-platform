package com.zov.smart.nova.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.data.access.system.mapper.SysMenuMapper;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.MenuService;
import com.zov.smart.nova.system.support.StatusValidators;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Atomic menu persistence implementation. */
@Service
public class MenuServiceImpl implements MenuService {

    private final SysMenuMapper menuMapper;

    public MenuServiceImpl(SysMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public SysMenuDO findById(Long menuId) {
        return menuId == null ? null : menuMapper.selectById(menuId);
    }

    @Override
    public SysMenuDO getByIdRequired(Long menuId) {
        SysMenuDO menu = findById(menuId);
        if (menu == null) {
            throw new BusinessException(SystemErrorCode.MENU_NOT_FOUND);
        }
        return menu;
    }

    @Override
    public List<SysMenuDO> listMenus(MenuTreeQuery query) {
        MenuTreeQuery safe = query == null ? new MenuTreeQuery() : query;
        LambdaQueryWrapper<SysMenuDO> wrapper = new LambdaQueryWrapper<SysMenuDO>()
                .orderByAsc(SysMenuDO::getSortOrder)
                .orderByAsc(SysMenuDO::getId);
        if (safe.getStatus() != null && !safe.getStatus().trim().isEmpty()) {
            wrapper.eq(SysMenuDO::getStatus, StatusValidators.requiredMenuStatus(safe.getStatus()));
        }
        if (safe.getVisibleFlag() != null) {
            wrapper.eq(SysMenuDO::getVisibleFlag, safe.getVisibleFlag());
        }
        if (!Boolean.TRUE.equals(safe.getIncludeButton())) {
            wrapper.ne(SysMenuDO::getMenuType, MenuTypeEnum.BUTTON);
        }
        return menuMapper.selectList(wrapper);
    }

    @Override
    public List<SysMenuDO> listMenusByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return menuMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<SysMenuDO> listMenusByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }
        return menuMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public Set<String> listPermissionCodesByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptySet();
        }
        List<String> codes = menuMapper.selectPermissionCodesByUserId(userId);
        Set<String> result = new LinkedHashSet<String>();
        if (codes != null) {
            for (String code : codes) {
                if (code != null && !code.trim().isEmpty()) {
                    result.add(code.trim());
                }
            }
        }
        return result;
    }

    @Override
    public boolean existsAllMenuIds(Collection<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return true;
        }
        Set<Long> ids = sanitizeIds(menuIds);
        if (ids.isEmpty()) {
            return true;
        }
        List<SysMenuDO> menus = menuMapper.selectBatchIds(ids);
        return menus != null && menus.size() == ids.size();
    }

    @Override
    public boolean existsPermissionCode(String permissionCode, Long excludeId) {
        if (permissionCode == null || permissionCode.trim().isEmpty()) {
            return false;
        }
        return menuMapper.countByPermissionCode(permissionCode.trim(), excludeId) > 0;
    }

    @Override
    public int countChildren(Long parentId) {
        return parentId == null ? 0 : menuMapper.countChildren(parentId);
    }

    @Override
    public Long createMenu(SysMenuDO menu) {
        menuMapper.insert(menu);
        return menu.getId();
    }

    @Override
    public void updateMenu(SysMenuDO menu) {
        menuMapper.updateById(menu);
    }

    @Override
    public void logicDeleteMenu(Long menuId) {
        menuMapper.deleteById(menuId);
    }

    private Set<Long> sanitizeIds(Collection<Long> ids) {
        Set<Long> result = new LinkedHashSet<Long>();
        for (Long id : ids) {
            if (id != null) {
                result.add(id);
            }
        }
        return result;
    }
}
