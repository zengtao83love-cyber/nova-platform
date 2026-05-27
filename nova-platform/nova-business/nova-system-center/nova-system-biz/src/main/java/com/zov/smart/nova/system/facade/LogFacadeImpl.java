package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.facade.LogFacade;
import com.zov.smart.nova.system.api.query.log.AuditLogPageQuery;
import com.zov.smart.nova.system.api.query.log.LoginLogPageQuery;
import com.zov.smart.nova.system.api.vo.log.AuditLogVO;
import com.zov.smart.nova.system.api.vo.log.LoginLogVO;
import com.zov.smart.nova.system.biz.LogBiz;
import org.springframework.stereotype.Service;

/** Log facade implementation. */
@Service
public class LogFacadeImpl implements LogFacade {
    private final LogBiz logBiz;
    public LogFacadeImpl(LogBiz logBiz) { this.logBiz = logBiz; }
    @Override public PageResult<LoginLogVO> pageLoginLogs(LoginLogPageQuery query) { return logBiz.pageLoginLogs(query); }
    @Override public PageResult<AuditLogVO> pageAuditLogs(AuditLogPageQuery query) { return logBiz.pageAuditLogs(query); }
}
