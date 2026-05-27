package com.zov.smart.nova.common.core.error;

/**
 * Common-domain error codes from the SDD error-code matrix.
 */
public enum CommonErrorCode implements ErrorCode {

    COMMON_PARAM_INVALID("COMMON_PARAM_INVALID", "参数校验失败", 400),
    COMMON_PARAM_MISSING("COMMON_PARAM_MISSING", "缺少必要参数", 400),
    COMMON_NOT_FOUND("COMMON_NOT_FOUND", "资源不存在", 404),
    COMMON_CONCURRENT_CONFLICT("COMMON_CONCURRENT_CONFLICT", "数据已被其他人修改，请刷新后重试", 409),
    COMMON_ENUM_NOT_FOUND("COMMON_ENUM_NOT_FOUND", "枚举不存在", 200),
    COMMON_SYSTEM_ERROR("COMMON_SYSTEM_ERROR", "系统出现未知异常，请联系管理员", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    CommonErrorCode(String code, String message, int httpStatus) {
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
