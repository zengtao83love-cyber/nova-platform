package com.zov.smart.nova.common.core.exception;

import com.zov.smart.nova.common.core.error.ErrorCode;

/**
 * Exception for predictable business-rule failures.
 *
 * <p>A BusinessException must carry a stable {@link ErrorCode}; callers should not
 * throw arbitrary message-only runtime exceptions for expected business rules.</p>
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;
    private final String code;
    private final int httpStatus;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode == null ? null : errorCode.getMessage(), null);
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode == null ? null : errorCode.getMessage(), cause);
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        if (errorCode == null) {
            throw new IllegalArgumentException("errorCode must not be null");
        }
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
