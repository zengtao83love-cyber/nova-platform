package com.zov.smart.nova.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import com.zov.smart.nova.data.access.system.mapper.SysLoginLogMapper;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.audit.model.AuditLogQueryCriteria;
import com.zov.smart.nova.infra.audit.service.AuditLogQueryService;
import com.zov.smart.nova.system.api.query.log.AuditLogPageQuery;
import com.zov.smart.nova.system.api.query.log.LoginLogPageQuery;
import com.zov.smart.nova.system.service.LogQueryService;
import org.springframework.stereotype.Service;

/** Read-only log query implementation. */
@Service
public class LogQueryServiceImpl implements LogQueryService {

    private final SysLoginLogMapper loginLogMapper;
    private final AuditLogQueryService auditLogQueryService;

    public LogQueryServiceImpl(SysLoginLogMapper loginLogMapper, AuditLogQueryService auditLogQueryService) {
        this.loginLogMapper = loginLogMapper;
        this.auditLogQueryService = auditLogQueryService;
    }

    @Override
    public PageResult<SysLoginLogDO> pageLoginLogs(LoginLogPageQuery query) {
        LoginLogPageQuery safe = query == null ? new LoginLogPageQuery() : query;
        IPage<SysLoginLogDO> page = new Page<SysLoginLogDO>(safe.getPageNo(), safe.getPageSize());
        IPage<SysLoginLogDO> result = loginLogMapper.selectLoginLogPage(page, safe.getUsername(), safe.getLoginResult(), safe.getStartTime(), safe.getEndTime());
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }

    @Override
    public PageResult<AuditOperationLogDO> pageAuditLogs(AuditLogPageQuery query) {
        AuditLogPageQuery safe = query == null ? new AuditLogPageQuery() : query;
        AuditLogQueryCriteria criteria = new AuditLogQueryCriteria();
        criteria.setOperationName(safe.getOperationName());
        criteria.setOperationType(toAuditType(safe.getOperationType()));
        criteria.setSuccessFlag(safe.getSuccessFlag());
        criteria.setStartTime(safe.getStartTime());
        criteria.setEndTime(safe.getEndTime());
        return auditLogQueryService.page(criteria, safe);
    }

    private AuditOperationTypeEnum toAuditType(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        for (AuditOperationTypeEnum item : AuditOperationTypeEnum.values()) {
            if (item.getCode().equals(code.trim())) {
                return item;
            }
        }
        return null;
    }
}
