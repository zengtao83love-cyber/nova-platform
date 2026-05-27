package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.infra.security.service.SecurityCacheService;
import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.command.menu.UpdateMenuCommand;
import com.zov.smart.nova.system.api.dto.menu.MenuDTO;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import com.zov.smart.nova.system.api.vo.menu.MenuTreeVO;
import com.zov.smart.nova.system.api.vo.menu.MenuVO;
import com.zov.smart.nova.system.biz.MenuBiz;
import com.zov.smart.nova.system.converter.MenuConverter;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.MenuService;
import com.zov.smart.nova.system.support.TransactionAfterCommitExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Menu business implementation. */
@Service
public class MenuBizImpl implements MenuBiz {

    private final MenuService menuService;
    private final MenuConverter menuConverter;
    private final SecurityCacheService securityCacheService;

    public MenuBizImpl(MenuService menuService, MenuConverter menuConverter, SecurityCacheService securityCacheService) {
        this.menuService = menuService;
        this.menuConverter = menuConverter;
        this.securityCacheService = securityCacheService;
    }

    @Override
    public MenuVO getMenu(Long menuId) {
        return menuConverter.toVO(menuService.getByIdRequired(menuId));
    }

    @Override
    public MenuDTO getMenuDTO(Long menuId) {
        return menuConverter.toDTO(menuService.getByIdRequired(menuId));
    }

    @Override
    public List<MenuDTO> listMenusByUserId(Long userId) {
        return menuConverter.toDTOList(menuService.listMenusByUserId(userId));
    }

    @Override
    public List<MenuDTO> listMenuTreeByUserId(Long userId) {
        return menuConverter.toDTOList(menuService.listMenusByUserId(userId));
    }

    @Override
    public List<MenuTreeVO> listMenuTree(MenuTreeQuery query) {
        return buildTree(menuService.listMenus(query));
    }

    @Override
    public Set<String> listPermissionCodesByUserId(Long userId) {
        return menuService.listPermissionCodesByUserId(userId);
    }

    @Override
    public boolean hasPermission(Long userId, String permissionCode) {
        return permissionCode != null && listPermissionCodesByUserId(userId).contains(permissionCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMenu(CreateMenuCommand command) {
        SysMenuDO menu = menuConverter.toCreateDO(command);
        validateParent(menu.getParentId());
        validateButtonPermission(menu);
        validatePermissionUnique(menu.getPermissionCode(), null);
        Long id = menuService.createMenu(menu);
        clearAllPermissionCacheAfterCommit();
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(UpdateMenuCommand command) {
        SysMenuDO menu = menuService.getByIdRequired(command.getId());
        menuConverter.updateDO(command, menu);
        validateParent(menu.getParentId());
        validateButtonPermission(menu);
        validatePermissionUnique(menu.getPermissionCode(), menu.getId());
        menuService.updateMenu(menu);
        clearAllPermissionCacheAfterCommit();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        menuService.getByIdRequired(menuId);
        if (menuService.countChildren(menuId) > 0) {
            throw new BusinessException(SystemErrorCode.MENU_HAS_CHILDREN);
        }
        menuService.logicDeleteMenu(menuId);
        clearAllPermissionCacheAfterCommit();
    }

    private List<MenuTreeVO> buildTree(List<SysMenuDO> menus) {
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, MenuTreeVO> index = new LinkedHashMap<Long, MenuTreeVO>();
        for (SysMenuDO menu : menus) {
            index.put(menu.getId(), menuConverter.toTreeVO(menu));
        }
        List<MenuTreeVO> roots = new ArrayList<MenuTreeVO>();
        for (MenuTreeVO node : index.values()) {
            Long parentId = node.getParentId();
            if (parentId == null || parentId == 0L || !index.containsKey(parentId)) {
                roots.add(node);
            } else {
                index.get(parentId).getChildren().add(node);
            }
        }
        return roots;
    }

    private void validateParent(Long parentId) {
        if (parentId != null && parentId > 0 && menuService.findById(parentId) == null) {
            throw new BusinessException(SystemErrorCode.MENU_PARENT_NOT_FOUND);
        }
    }

    private void validateButtonPermission(SysMenuDO menu) {
        if (MenuTypeEnum.BUTTON.equals(menu.getMenuType()) && (menu.getPermissionCode() == null || menu.getPermissionCode().trim().isEmpty())) {
            throw new BusinessException(SystemErrorCode.MENU_BUTTON_PERMISSION_REQUIRED);
        }
    }

    private void validatePermissionUnique(String permissionCode, Long excludeId) {
        if (menuService.existsPermissionCode(permissionCode, excludeId)) {
            throw new BusinessException(SystemErrorCode.MENU_PERMISSION_CODE_EXISTS);
        }
    }

    private void clearAllPermissionCacheAfterCommit() {
        TransactionAfterCommitExecutor.runAfterCommit(new Runnable() {
            @Override
            public void run() {
                securityCacheService.clearAllPermissionsCache();
            }
        });
    }
}
