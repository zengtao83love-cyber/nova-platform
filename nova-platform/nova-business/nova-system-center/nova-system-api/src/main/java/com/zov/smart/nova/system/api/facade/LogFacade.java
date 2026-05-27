package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.query.log.AuditLogPageQuery;
import com.zov.smart.nova.system.api.query.log.LoginLogPageQuery;
import com.zov.smart.nova.system.api.vo.log.AuditLogVO;
import com.zov.smart.nova.system.api.vo.log.LoginLogVO;

/** Read-only log query contract. */
public interface LogFacade {
    PageResult<LoginLogVO> pageLoginLogs(LoginLogPageQuery query);
    PageResult<AuditLogVO> pageAuditLogs(AuditLogPageQuery query);
}
