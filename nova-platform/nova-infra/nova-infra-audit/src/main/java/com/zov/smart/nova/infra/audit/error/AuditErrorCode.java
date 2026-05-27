package com.zov.smart.nova.infra.audit.error;

import com.zov.smart.nova.common.core.error.ErrorCode;

/**
 * Audit-domain error codes from the SDD error-code matrix.
 */
public enum AuditErrorCode implements ErrorCode {

    AUDIT_LOG_NOT_FOUND("AUDIT_LOG_NOT_FOUND", "操作日志不存在", 404);

    private final String code;
    private final String message;
    private final int httpStatus;

    AuditErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
