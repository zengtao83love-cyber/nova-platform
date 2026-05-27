package com.zov.smart.nova.infra.audit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageParam;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.infra.audit.error.AuditErrorCode;
import com.zov.smart.nova.infra.audit.mapper.AuditOperationLogMapper;
import com.zov.smart.nova.infra.audit.model.AuditLogQueryCriteria;
import com.zov.smart.nova.infra.audit.service.AuditLogQueryService;
import com.zov.smart.nova.infra.audit.service.AuditLogService;
import com.zov.smart.nova.infra.mybatisplus.page.MybatisPageHelper;
import com.zov.smart.nova.infra.mybatisplus.page.PageConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Audit log persistence service.
 */
public class AuditLogServiceImpl implements AuditLogService, AuditLogQueryService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogServiceImpl.class);

    private final AuditOperationLogMapper auditOperationLogMapper;

    public AuditLogServiceImpl(AuditOperationLogMapper auditOperationLogMapper) {
        this.auditOperationLogMapper = auditOperationLogMapper;
    }

    @Async("auditAsyncExecutor")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void asyncSaveLog(AuditOperationLogDO logDO) {
        if (logDO == null) {
            return;
        }
        try {
            auditOperationLogMapper.insert(logDO);
        } catch (Exception ex) {
            log.warn("Failed to persist audit operation log. traceId={}, operationName={}",
                    logDO.getTraceId(), logDO.getOperationName(), ex);
        }
    }

    @Override
    public PageResult<AuditOperationLogDO> page(AuditLogQueryCriteria criteria, PageParam pageParam) {
        Page<AuditOperationLogDO> page = MybatisPageHelper.toPage(pageParam);
        Page<AuditOperationLogDO> resultPage = auditOperationLogMapper.selectPageByCriteria(page, criteria);
        return PageConvertor.convert(resultPage);
    }

    @Override
    public AuditOperationLogDO findById(Long id) {
        if (id == null) {
            throw new BusinessException(AuditErrorCode.AUDIT_LOG_NOT_FOUND);
        }
        AuditOperationLogDO auditLog = auditOperationLogMapper.selectOneById(id);
        if (auditLog == null) {
            throw new BusinessException(AuditErrorCode.AUDIT_LOG_NOT_FOUND);
        }
        return auditLog;
    }
}
