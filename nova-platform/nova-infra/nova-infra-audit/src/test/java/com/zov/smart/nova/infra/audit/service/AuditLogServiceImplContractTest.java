package com.zov.smart.nova.infra.audit.service;

import com.zov.smart.nova.infra.audit.service.impl.AuditLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditLogServiceImplContractTest {

    @Test
    void asyncSaveLogUsesIndependentTransaction() throws Exception {
        Method method = AuditLogServiceImpl.class.getMethod("asyncSaveLog", com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO.class);
        Async async = method.getAnnotation(Async.class);
        Transactional transactional = method.getAnnotation(Transactional.class);
        assertNotNull(async);
        assertEquals("auditAsyncExecutor", async.value());
        assertNotNull(transactional);
        assertEquals(Propagation.REQUIRES_NEW, transactional.propagation());
    }
}
