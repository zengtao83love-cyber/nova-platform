package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.command.menu.UpdateMenuCommand;
import com.zov.smart.nova.system.api.dto.menu.MenuDTO;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import java.util.List;

/** Menu contract exposed by system center. */
public interface MenuFacade {
    MenuDTO getMenuById(Long menuId);
    List<MenuDTO> listMenusByUserId(Long userId);
    List<MenuDTO> listMenuTreeByUserId(Long userId);
    List<MenuDTO> listMenuTree(MenuTreeQuery query);
    Long createMenu(CreateMenuCommand command);
    void updateMenu(UpdateMenuCommand command);
    void deleteMenu(Long menuId);
}
