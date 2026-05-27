package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.command.menu.UpdateMenuCommand;
import com.zov.smart.nova.system.api.dto.menu.MenuDTO;
import com.zov.smart.nova.system.api.facade.MenuFacade;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import com.zov.smart.nova.system.biz.MenuBiz;
import org.springframework.stereotype.Service;

import java.util.List;

/** Menu facade implementation. */
@Service
public class MenuFacadeImpl implements MenuFacade {

    private final MenuBiz menuBiz;

    public MenuFacadeImpl(MenuBiz menuBiz) {
        this.menuBiz = menuBiz;
    }

    @Override public MenuDTO getMenuById(Long menuId) { return menuBiz.getMenuDTO(menuId); }
    @Override public List<MenuDTO> listMenusByUserId(Long userId) { return menuBiz.listMenusByUserId(userId); }
    @Override public List<MenuDTO> listMenuTreeByUserId(Long userId) { return menuBiz.listMenuTreeByUserId(userId); }
    @Override public List<MenuDTO> listMenuTree(MenuTreeQuery query) { return com.zov.smart.nova.system.converter.StaticMenuTreeFlattener.flatten(menuBiz.listMenuTree(query)); }
    @Override public Long createMenu(CreateMenuCommand command) { return menuBiz.createMenu(command); }
    @Override public void updateMenu(UpdateMenuCommand command) { menuBiz.updateMenu(command); }
    @Override public void deleteMenu(Long menuId) { menuBiz.deleteMenu(menuId); }
}
