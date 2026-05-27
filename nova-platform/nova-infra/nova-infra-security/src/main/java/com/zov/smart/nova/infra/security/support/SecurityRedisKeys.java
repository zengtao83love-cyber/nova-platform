package com.zov.smart.nova.infra.security.support;

/** Stable Redis key namespace for security infrastructure. */
public final class SecurityRedisKeys {
    private SecurityRedisKeys() {}

    public static final String ACCESS = "nova:security:access:";
    public static final String REFRESH = "nova:security:refresh:";
    public static final String USER_TOKENS = "nova:security:user-tokens:";
    public static final String PERMISSIONS = "nova:security:permissions:";
    public static final String MENUS = "nova:security:menus:";
    public static final String LOGIN_FAIL = "nova:security:login-fail:";

    public static String access(String tokenId) { return ACCESS + tokenId; }
    public static String refresh(String refreshTokenId) { return REFRESH + refreshTokenId; }
    public static String userTokens(Long userId) { return USER_TOKENS + userId; }
    public static String permissions(Long userId) { return PERMISSIONS + userId; }
    public static String menus(Long userId) { return MENUS + userId; }
    public static String loginFail(String username) { return LOGIN_FAIL + normalize(username); }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
