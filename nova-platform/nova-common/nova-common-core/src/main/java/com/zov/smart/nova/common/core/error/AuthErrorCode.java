package com.zov.smart.nova.common.core.error;

/**
 * Authentication and authorization error codes from the SDD error-code matrix.
 *
 * <p>This enum contains only stable error-code metadata. It intentionally does
 * not contain authentication, token, Redis, or database logic.</p>
 */
public enum AuthErrorCode implements ErrorCode {

    AUTH_UNAUTHORIZED("AUTH_UNAUTHORIZED", "未登录或登录已过期", 401),
    AUTH_FORBIDDEN("AUTH_FORBIDDEN", "无操作权限", 403),
    AUTH_TOKEN_EXPIRED("AUTH_TOKEN_EXPIRED", "访问令牌已过期", 401),
    AUTH_TOKEN_INVALID("AUTH_TOKEN_INVALID", "无效的访问令牌", 401),
    AUTH_REFRESH_TOKEN_EXPIRED("AUTH_REFRESH_TOKEN_EXPIRED", "刷新令牌已过期，请重新登录", 401),
    AUTH_USER_NOT_FOUND("AUTH_USER_NOT_FOUND", "用户不存在或密码错误", 200),
    AUTH_PASSWORD_ERROR("AUTH_PASSWORD_ERROR", "用户不存在或密码错误", 200),
    AUTH_USER_DISABLED("AUTH_USER_DISABLED", "用户已被停用", 200),
    AUTH_ACCOUNT_LOCKED("AUTH_ACCOUNT_LOCKED", "账号已被锁定，请稍后重试", 200),
    AUTH_OLD_PASSWORD_ERROR("AUTH_OLD_PASSWORD_ERROR", "原密码不正确", 200),
    AUTH_PASSWORD_NOT_MATCH("AUTH_PASSWORD_NOT_MATCH", "新密码与确认密码不一致", 200),
    AUTH_PASSWORD_STRENGTH_INVALID("AUTH_PASSWORD_STRENGTH_INVALID", "新密码强度不符合要求", 200);

    private final String code;
    private final String message;
    private final int httpStatus;

    AuthErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getMessage() { return message; }

    @Override
    public int getHttpStatus() { return httpStatus; }
}
