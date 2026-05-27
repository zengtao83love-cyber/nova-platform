package com.zov.smart.nova.infra.audit.aspect;

import com.zov.smart.nova.common.context.LoginUserContext;
import com.zov.smart.nova.common.context.TraceContext;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.audit.service.AuditLogService;
import com.zov.smart.nova.infra.audit.support.AuditPayloadMasker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuditAspectTest {

    @AfterEach
    void tearDown() {
        LoginUserContext.clear();
        TraceContext.clear();
    }

    @Test
    void capturesContextInMainThreadAndMasksParams() throws Throwable {
        AuditLogService service = mock(AuditLogService.class);
        AuditAspect aspect = new AuditAspect(service, new AuditPayloadMasker());
        TraceContext.set("trace-1");
        LoginUserContext.set(new LoginUserContext.CurrentUser(1L, "admin", "系统管理员", true));
        ProceedingJoinPoint point = mock(ProceedingJoinPoint.class);
        when(point.getArgs()).thenReturn(new Object[]{"password=secret"});
        when(point.proceed()).thenReturn("ok");

        Object result = aspect.around(point, annotation(true, true));

        assertEquals("ok", result);
        ArgumentCaptor<AuditOperationLogDO> captor = ArgumentCaptor.forClass(AuditOperationLogDO.class);
        verify(service).asyncSaveLog(captor.capture());
        AuditOperationLogDO log = captor.getValue();
        assertEquals("trace-1", log.getTraceId());
        assertEquals(1L, log.getOperatorId());
        assertEquals(AuditOperationTypeEnum.CREATE, log.getOperationType());
        assertEquals(1, log.getSuccessFlag());
        assertFalse(log.getRequestParams().contains("secret"));
    }

    private OperationLog annotation(boolean recordParams, boolean recordResult) {
        OperationLog annotation = mock(OperationLog.class);
        when(annotation.name()).thenReturn("新增用户");
        when(annotation.type()).thenReturn(AuditOperationTypeEnum.CREATE);
        when(annotation.recordParams()).thenReturn(recordParams);
        when(annotation.recordResult()).thenReturn(recordResult);
        return annotation;
    }
}
