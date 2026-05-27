package com.zov.smart.nova.infra.guard.error;

import com.zov.smart.nova.common.core.error.ErrorCode;

/**
 * Guard-domain error codes from the SDD error-code matrix.
 */
public enum GuardErrorCode implements ErrorCode {

    GUARD_REPEAT_SUBMIT("GUARD_REPEAT_SUBMIT", "请勿重复提交", 200),
    GUARD_LOCK_FAILED("GUARD_LOCK_FAILED", "当前资源正在处理中，请稍后重试", 200);

    private final String code;
    private final String message;
    private final int httpStatus;

    GuardErrorCode(String code, String message, int httpStatus) {
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
