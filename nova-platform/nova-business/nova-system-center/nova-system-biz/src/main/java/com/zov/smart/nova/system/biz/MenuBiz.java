package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.command.menu.UpdateMenuCommand;
import com.zov.smart.nova.system.api.dto.menu.MenuDTO;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import com.zov.smart.nova.system.api.vo.menu.MenuTreeVO;
import com.zov.smart.nova.system.api.vo.menu.MenuVO;

import java.util.List;
import java.util.Set;

/** Menu business orchestration. */
public interface MenuBiz {
    MenuVO getMenu(Long menuId);
    MenuDTO getMenuDTO(Long menuId);
    List<MenuDTO> listMenusByUserId(Long userId);
    List<MenuDTO> listMenuTreeByUserId(Long userId);
    List<MenuTreeVO> listMenuTree(MenuTreeQuery query);
    Set<String> listPermissionCodesByUserId(Long userId);
    boolean hasPermission(Long userId, String permissionCode);
    Long createMenu(CreateMenuCommand command);
    void updateMenu(UpdateMenuCommand command);
    void deleteMenu(Long menuId);
}
