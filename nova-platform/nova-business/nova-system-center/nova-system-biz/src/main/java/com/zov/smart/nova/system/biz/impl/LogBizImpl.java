package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.system.api.query.log.AuditLogPageQuery;
import com.zov.smart.nova.system.api.query.log.LoginLogPageQuery;
import com.zov.smart.nova.system.api.vo.log.AuditLogVO;
import com.zov.smart.nova.system.api.vo.log.LoginLogVO;
import com.zov.smart.nova.system.biz.LogBiz;
import com.zov.smart.nova.system.converter.LogConverter;
import com.zov.smart.nova.system.service.LogQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** Read-only log business implementation. */
@Service
public class LogBizImpl implements LogBiz {

    private final LogQueryService logQueryService;
    private final LogConverter logConverter;

    public LogBizImpl(LogQueryService logQueryService, LogConverter logConverter) {
        this.logQueryService = logQueryService;
        this.logConverter = logConverter;
    }

    @Override
    public PageResult<LoginLogVO> pageLoginLogs(LoginLogPageQuery query) {
        PageResult<SysLoginLogDO> page = logQueryService.pageLoginLogs(query);
        List<LoginLogVO> records = new ArrayList<LoginLogVO>();
        for (SysLoginLogDO item : page.getRecords()) {
            records.add(logConverter.toLoginLogVO(item));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public PageResult<AuditLogVO> pageAuditLogs(AuditLogPageQuery query) {
        PageResult<AuditOperationLogDO> page = logQueryService.pageAuditLogs(query);
        List<AuditLogVO> records = new ArrayList<AuditLogVO>();
        for (AuditOperationLogDO item : page.getRecords()) {
            records.add(logConverter.toAuditLogVO(item));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }
}
