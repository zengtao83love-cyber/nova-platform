package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.command.menu.UpdateMenuCommand;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import com.zov.smart.nova.system.api.vo.menu.MenuTreeVO;
import com.zov.smart.nova.system.api.vo.menu.MenuVO;
import com.zov.smart.nova.system.biz.MenuBiz;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/** Menu management HTTP API. Controller only calls Biz. */
@Validated
@RestController
@RequestMapping("/api/system/menus")
public class MenuController {

    private final MenuBiz menuBiz;

    public MenuController(MenuBiz menuBiz) {
        this.menuBiz = menuBiz;
    }

    @GetMapping
    @RequirePermission(SystemPermissionConstants.MENU_LIST)
    public Result<List<MenuTreeVO>> listTree(@Valid MenuTreeQuery query) {
        return Result.success(menuBiz.listMenuTree(query));
    }

    @GetMapping("/{id}")
    @RequirePermission(SystemPermissionConstants.MENU_LIST)
    public Result<MenuVO> getMenu(@PathVariable("id") Long id) {
        return Result.success(menuBiz.getMenu(id));
    }

    @PostMapping
    @RepeatSubmitGuard
    @OperationLog(name = "新增菜单", type = AuditOperationTypeEnum.CREATE)
    @RequirePermission(SystemPermissionConstants.MENU_CREATE)
    public Result<Long> createMenu(@Valid @RequestBody CreateMenuCommand command) {
        return Result.success(menuBiz.createMenu(command));
    }

    @PutMapping("/{id}")
    @RepeatSubmitGuard
    @OperationLog(name = "修改菜单", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.MENU_UPDATE)
    public Result<Void> updateMenu(@PathVariable("id") Long id, @Valid @RequestBody UpdateMenuCommand command) {
        command.setId(id);
        menuBiz.updateMenu(command);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    @OperationLog(name = "删除菜单", type = AuditOperationTypeEnum.DELETE)
    @RequirePermission(SystemPermissionConstants.MENU_DELETE)
    public Result<Void> deleteMenu(@PathVariable("id") Long id) {
        menuBiz.deleteMenu(id);
        return Result.success(null);
    }
}
