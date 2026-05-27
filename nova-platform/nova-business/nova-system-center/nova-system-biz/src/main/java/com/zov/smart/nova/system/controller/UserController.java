package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.command.user.ResetPasswordCommand;
import com.zov.smart.nova.system.api.command.user.UpdateUserCommand;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import com.zov.smart.nova.system.api.vo.user.UserDetailVO;
import com.zov.smart.nova.system.api.vo.user.UserPageVO;
import com.zov.smart.nova.system.biz.UserBiz;
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

/** User management HTTP API. Controller only calls Biz. */
@Validated
@RestController
@RequestMapping("/api/system/users")
public class UserController {

    private final UserBiz userBiz;

    public UserController(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    @GetMapping
    @RequirePermission(SystemPermissionConstants.USER_LIST)
    public Result<PageResult<UserPageVO>> pageUsers(@Valid  @RequestBody UserPageQuery query) {
        return Result.success(userBiz.pageUsers(query));
    }

    @GetMapping("/{id}")
    @RequirePermission(SystemPermissionConstants.USER_LIST)
    public Result<UserDetailVO> getUser(@PathVariable("id") Long id) {
        return Result.success(userBiz.getUserDetail(id));
    }

    @PostMapping
    @RepeatSubmitGuard
    @OperationLog(name = "新增用户", type = AuditOperationTypeEnum.CREATE)
    @RequirePermission(SystemPermissionConstants.USER_CREATE)
    public Result<UserDetailVO> createUser(@Valid @RequestBody CreateUserCommand command) {
        return Result.success(userBiz.createUser(command));
    }

    @PutMapping("/{id}")
    @RepeatSubmitGuard
    @OperationLog(name = "修改用户", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.USER_UPDATE)
    public Result<UserDetailVO> updateUser(@PathVariable("id") Long id,
                                           @Valid @RequestBody UpdateUserCommand command) {
        command.setId(id);
        return Result.success(userBiz.updateUser(command));
    }

    @DeleteMapping("/{id}")
    @OperationLog(name = "删除用户", type = AuditOperationTypeEnum.DELETE)
    @RequirePermission(SystemPermissionConstants.USER_DELETE)
    public Result<Void> deleteUser(@PathVariable("id") Long id) {
        userBiz.deleteUser(id);
        return Result.success(null);
    }

    @PutMapping("/{id}/status")
    @RepeatSubmitGuard
    @OperationLog(name = "修改用户状态", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.USER_UPDATE)
    public Result<Void> changeStatus(@PathVariable("id") Long id,
                                     @RequestParam("status") String status) {
        userBiz.changeUserStatus(id, status);
        return Result.success(null);
    }

    @PutMapping("/{id}/password/reset")
    @RepeatSubmitGuard
    @OperationLog(name = "重置用户密码", type = AuditOperationTypeEnum.UPDATE, recordResult = false)
    @RequirePermission(SystemPermissionConstants.USER_RESET_PASSWORD)
    public Result<Void> resetPassword(@PathVariable("id") Long id,
                                      @Valid @RequestBody ResetPasswordCommand command) {
        command.setUserId(id);
        userBiz.resetPassword(command);
        return Result.success(null);
    }

    @PutMapping("/{id}/roles")
    @RepeatSubmitGuard
    @OperationLog(name = "分配用户角色", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.USER_ASSIGN_ROLE)
    public Result<Void> assignRoles(@PathVariable("id") Long id,
                                    @Valid @RequestBody AssignUserRolesCommand command) {
        command.setUserId(id);
        userBiz.assignRoles(command);
        return Result.success(null);
    }

    @PutMapping("/{id}/unlock")
    @RepeatSubmitGuard
    @OperationLog(name = "解锁用户", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.USER_UPDATE)
    public Result<Void> unlockUser(@PathVariable("id") Long id) {
        userBiz.unlockUser(id);
        return Result.success(null);
    }
}
