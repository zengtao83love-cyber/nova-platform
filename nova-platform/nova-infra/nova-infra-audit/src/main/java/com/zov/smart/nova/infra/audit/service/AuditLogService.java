package com.zov.smart.nova.infra.audit.service;

import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;

/**
 * Write-side audit log service.
 */
public interface AuditLogService {

    /**
     * Saves a fully captured audit log snapshot asynchronously in an independent transaction.
     */
    void asyncSaveLog(AuditOperationLogDO logDO);
}
