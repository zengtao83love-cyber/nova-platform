package com.zov.smart.nova.system.service;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.system.api.query.log.AuditLogPageQuery;
import com.zov.smart.nova.system.api.query.log.LoginLogPageQuery;

/** Read-only log query service. */
public interface LogQueryService {
    PageResult<SysLoginLogDO> pageLoginLogs(LoginLogPageQuery query);
    PageResult<AuditOperationLogDO> pageAuditLogs(AuditLogPageQuery query);
}
