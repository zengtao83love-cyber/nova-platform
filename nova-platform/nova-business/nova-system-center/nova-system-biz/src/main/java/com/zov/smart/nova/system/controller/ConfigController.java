package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.command.config.CreateConfigCommand;
import com.zov.smart.nova.system.api.command.config.UpdateConfigCommand;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.config.ConfigPageQuery;
import com.zov.smart.nova.system.api.vo.config.ConfigVO;
import com.zov.smart.nova.system.api.vo.config.ConfigValueResponse;
import com.zov.smart.nova.system.biz.ConfigBiz;
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

/** System configuration HTTP API. */
@Validated
@RestController
@RequestMapping("/api/system/configs")
public class ConfigController {

    private final ConfigBiz configBiz;

    public ConfigController(ConfigBiz configBiz) {
        this.configBiz = configBiz;
    }

    @GetMapping
    @RequirePermission(SystemPermissionConstants.CONFIG_LIST)
    public Result<PageResult<ConfigVO>> pageConfigs(@Valid ConfigPageQuery query) {
        return Result.success(configBiz.pageConfigs(query));
    }

    @GetMapping("/value")
    @RequirePermission(SystemPermissionConstants.CONFIG_LIST)
    public Result<ConfigValueResponse> getValue(@RequestParam("configKey") String configKey) {
        ConfigValueResponse response = new ConfigValueResponse();
        response.setConfigKey(configKey);
        response.setConfigValue(configBiz.getConfigValue(configKey));
        return Result.success(response);
    }

    @PostMapping
    @RepeatSubmitGuard
    @OperationLog(name = "新增系统参数", type = AuditOperationTypeEnum.CREATE)
    @RequirePermission(SystemPermissionConstants.CONFIG_CREATE)
    public Result<Long> createConfig(@Valid @RequestBody CreateConfigCommand command) {
        return Result.success(configBiz.createConfig(command));
    }

    @PutMapping("/{id}")
    @RepeatSubmitGuard
    @OperationLog(name = "修改系统参数", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.CONFIG_UPDATE)
    public Result<Void> updateConfig(@PathVariable("id") Long id, @Valid @RequestBody UpdateConfigCommand command) {
        command.setId(id);
        configBiz.updateConfig(command);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    @OperationLog(name = "删除系统参数", type = AuditOperationTypeEnum.DELETE)
    @RequirePermission(SystemPermissionConstants.CONFIG_DELETE)
    public Result<Void> deleteConfig(@PathVariable("id") Long id) {
        configBiz.deleteConfig(id);
        return Result.success(null);
    }
}
