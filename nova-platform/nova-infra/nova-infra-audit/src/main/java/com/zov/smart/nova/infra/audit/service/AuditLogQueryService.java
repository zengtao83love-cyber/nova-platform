package com.zov.smart.nova.infra.audit.service;

import com.zov.smart.nova.common.model.PageParam;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.infra.audit.model.AuditLogQueryCriteria;

/**
 * Read-side audit log service for system-biz log query screens.
 */
public interface AuditLogQueryService {

    PageResult<AuditOperationLogDO> page(AuditLogQueryCriteria criteria, PageParam pageParam);

    AuditOperationLogDO findById(Long id);
}
