package com.zov.smart.nova.infra.audit.aspect;

import com.zov.smart.nova.common.context.LoginUserContext;
import com.zov.smart.nova.common.context.TraceContext;
import com.zov.smart.nova.common.util.IpUtils;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.infra.audit.service.AuditLogService;
import com.zov.smart.nova.infra.audit.support.AuditPayloadMasker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Captures audit information in the request thread and passes a completed
 * AuditOperationLogDO snapshot to the async persistence service.
 */
@Aspect
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    private final AuditLogService auditLogService;
    private final AuditPayloadMasker auditPayloadMasker;

    public AuditAspect(AuditLogService auditLogService, AuditPayloadMasker auditPayloadMasker) {
        this.auditLogService = auditLogService;
        this.auditPayloadMasker = auditPayloadMasker;
    }

    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        long start = System.currentTimeMillis();
        AuditOperationLogDO auditLog = createBaseLog(operationLog);
        captureCurrentUser(auditLog);
        captureHttpRequest(auditLog);
        if (operationLog.recordParams()) {
            auditLog.setRequestParams(auditPayloadMasker.maskObject(point.getArgs()));
        }

        Object result = null;
        Throwable failure = null;
        try {
            result = point.proceed();
            auditLog.setSuccessFlag(1);
            if (operationLog.recordResult()) {
                auditLog.setResponseBody(auditPayloadMasker.maskObject(result));
            }
            return result;
        } catch (Throwable ex) {
            failure = ex;
            auditLog.setSuccessFlag(0);
            auditLog.setErrorMessage(auditPayloadMasker.truncate(ex.getMessage(), 500));
            throw ex;
        } finally {
            auditLog.setCostTimeMs(System.currentTimeMillis() - start);
            trySave(auditLog, failure);
        }
    }

    private AuditOperationLogDO createBaseLog(OperationLog operationLog) {
        AuditOperationLogDO auditLog = new AuditOperationLogDO();
        auditLog.setTraceId(TraceContext.peek());
        auditLog.setOperationName(operationLog.name());
        auditLog.setOperationType(operationLog.type());
        auditLog.setSuccessFlag(1);
        auditLog.setCreatedAt(LocalDateTime.now());
        auditLog.setDeleteFlag(0);
        auditLog.setVersion(0);
        return auditLog;
    }

    private void captureCurrentUser(AuditOperationLogDO auditLog) {
        LoginUserContext.CurrentUser user = LoginUserContext.get();
        if (user == null) {
            return;
        }
        auditLog.setOperatorId(user.getUserId());
        auditLog.setOperatorName(user.getRealName() == null ? user.getUsername() : user.getRealName());
    }

    private void captureHttpRequest(AuditOperationLogDO auditLog) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof ServletRequestAttributes)) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        auditLog.setRequestMethod(request.getMethod());
        auditLog.setRequestUri(request.getRequestURI());
        auditLog.setClientIp(IpUtils.getIpAddr(request));
        auditLog.setUserAgent(auditPayloadMasker.truncate(auditPayloadMasker.maskText(request.getHeader("User-Agent")), 500));
    }

    private void trySave(AuditOperationLogDO auditLog, Throwable failure) {
        try {
            auditLogService.asyncSaveLog(auditLog);
        } catch (Exception ex) {
            log.warn("Failed to submit audit operation log. traceId={}, operationName={}, businessFailed={}",
                    auditLog.getTraceId(), auditLog.getOperationName(), failure != null, ex);
        }
    }
}
