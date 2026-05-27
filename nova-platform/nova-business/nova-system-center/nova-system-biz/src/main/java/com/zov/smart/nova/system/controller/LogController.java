package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.log.AuditLogPageQuery;
import com.zov.smart.nova.system.api.query.log.LoginLogPageQuery;
import com.zov.smart.nova.system.api.vo.log.AuditLogVO;
import com.zov.smart.nova.system.api.vo.log.LoginLogVO;
import com.zov.smart.nova.system.biz.LogBiz;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/** Read-only login/audit log HTTP API. */
@Validated
@RestController
@RequestMapping("/api/system")
public class LogController {

    private final LogBiz logBiz;

    public LogController(LogBiz logBiz) {
        this.logBiz = logBiz;
    }

    @GetMapping("/login-logs")
    @RequirePermission(SystemPermissionConstants.LOGIN_LOG_LIST)
    public Result<PageResult<LoginLogVO>> pageLoginLogs(@Valid LoginLogPageQuery query) {
        return Result.success(logBiz.pageLoginLogs(query));
    }

    @GetMapping("/audit-logs")
    @RequirePermission(SystemPermissionConstants.AUDIT_LOG_LIST)
    public Result<PageResult<AuditLogVO>> pageAuditLogs(@Valid AuditLogPageQuery query) {
        return Result.success(logBiz.pageAuditLogs(query));
    }
}
