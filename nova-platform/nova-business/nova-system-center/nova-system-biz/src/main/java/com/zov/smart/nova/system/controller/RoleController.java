package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.role.UpdateRoleCommand;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;
import com.zov.smart.nova.system.api.vo.role.RoleVO;
import com.zov.smart.nova.system.biz.RoleBiz;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/** Role management HTTP API. Controller only calls Biz. */
@Validated
@RestController
@RequestMapping("/api/system/roles")
public class RoleController {

    private final RoleBiz roleBiz;

    public RoleController(RoleBiz roleBiz) {
        this.roleBiz = roleBiz;
    }

    @GetMapping
    @RequirePermission(SystemPermissionConstants.ROLE_LIST)
    public Result<PageResult<RoleVO>> pageRoles(@Valid @RequestBody RolePageQuery query) {
        return Result.success(roleBiz.pageRoles(query));
    }

    @GetMapping("/{id}")
    @RequirePermission(SystemPermissionConstants.ROLE_LIST)
    public Result<RoleVO> getRole(@PathVariable("id") Long id) {
        return Result.success(roleBiz.getRoleDetail(id));
    }

    @PostMapping
    @RepeatSubmitGuard
    @OperationLog(name = "新增角色", type = AuditOperationTypeEnum.CREATE)
    @RequirePermission(SystemPermissionConstants.ROLE_CREATE)
    public Result<RoleVO> createRole(@Valid @RequestBody CreateRoleCommand command) {
        return Result.success(roleBiz.createRole(command));
    }

    @PutMapping("/{id}")
    @RepeatSubmitGuard
    @OperationLog(name = "修改角色", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.ROLE_UPDATE)
    public Result<RoleVO> updateRole(@PathVariable("id") Long id,
                                     @Valid @RequestBody UpdateRoleCommand command) {
        command.setId(id);
        return Result.success(roleBiz.updateRole(command));
    }

    @DeleteMapping("/{id}")
    @OperationLog(name = "删除角色", type = AuditOperationTypeEnum.DELETE)
    @RequirePermission(SystemPermissionConstants.ROLE_DELETE)
    public Result<Void> deleteRole(@PathVariable("id") Long id) {
        roleBiz.deleteRole(id);
        return Result.success(null);
    }

    @PutMapping("/{id}/status")
    @RepeatSubmitGuard
    @OperationLog(name = "修改角色状态", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.ROLE_UPDATE)
    public Result<Void> changeStatus(@PathVariable("id") Long id,
                                     @RequestParam("status") String status) {
        roleBiz.changeRoleStatus(id, status);
        return Result.success(null);
    }

    @PutMapping("/{id}/menus")
    @RepeatSubmitGuard
    @OperationLog(name = "分配角色菜单", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.ROLE_ASSIGN_MENU)
    public Result<Void> assignMenus(@PathVariable("id") Long id,
                                    @Valid @RequestBody AssignRoleMenusCommand command) {
        command.setRoleId(id);
        roleBiz.assignMenus(command);
        return Result.success(null);
    }
}
