package com.zov.smart.nova.infra.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Security configuration properties.
 *
 * <p>The JWT secret is bound from {@code nova.security.jwt-secret}. The runtime
 * configuration should inject it through environment variable
 * {@code NOVA_SECURITY_JWT_SECRET}, as confirmed in the SDD ambiguity pass.</p>
 */
@ConfigurationProperties(prefix = "nova.security")
public class NovaSecurityProperties {

    public static final String ENV_JWT_SECRET = "NOVA_SECURITY_JWT_SECRET";

    private String tokenHeader = "Authorization";
    private String tokenPrefix = "Bearer";
    private String jwtSecret = "your-256bit-or-longer-secret-key-here";
    private long accessTokenExpireSeconds = 7200L;
    private long refreshTokenExpireSeconds = 604800L;
    private long permissionCacheTtlSeconds = 3600L;
    private boolean singleDeviceLogin = false;
    private int maxLoginDevices = 5;
    private String passwordStrengthRegex = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$";
    private Login login = new Login();
    private List<String> ignoreUrls = new ArrayList<String>(Arrays.asList(
            "/api/auth/login",
            "/api/auth/refresh",
            "/doc.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/actuator/health",
            "/api/health"
    ));

    public String[] getIgnoreUrlsArray() {
        return ignoreUrls.toArray(new String[0]);
    }

    public void validateJwtSecret() {
        if (jwtSecret == null || jwtSecret.trim().length() < 32) {
            throw new IllegalStateException("nova.security.jwt-secret must be at least 32 characters and should be injected by " + ENV_JWT_SECRET);
        }
    }

    public String normalizeTokenPrefix() {
        return tokenPrefix == null ? "Bearer" : tokenPrefix.trim();
    }

    public String getTokenHeader() { return tokenHeader; }
    public void setTokenHeader(String tokenHeader) { this.tokenHeader = tokenHeader; }
    public String getTokenPrefix() { return tokenPrefix; }
    public void setTokenPrefix(String tokenPrefix) { this.tokenPrefix = tokenPrefix; }
    public String getJwtSecret() { return jwtSecret; }
    public void setJwtSecret(String jwtSecret) { this.jwtSecret = jwtSecret; }
    public long getAccessTokenExpireSeconds() { return accessTokenExpireSeconds; }
    public void setAccessTokenExpireSeconds(long accessTokenExpireSeconds) { this.accessTokenExpireSeconds = accessTokenExpireSeconds; }
    public long getRefreshTokenExpireSeconds() { return refreshTokenExpireSeconds; }
    public void setRefreshTokenExpireSeconds(long refreshTokenExpireSeconds) { this.refreshTokenExpireSeconds = refreshTokenExpireSeconds; }
    public long getPermissionCacheTtlSeconds() { return permissionCacheTtlSeconds; }
    public void setPermissionCacheTtlSeconds(long permissionCacheTtlSeconds) { this.permissionCacheTtlSeconds = permissionCacheTtlSeconds; }
    public boolean isSingleDeviceLogin() { return singleDeviceLogin; }
    public void setSingleDeviceLogin(boolean singleDeviceLogin) { this.singleDeviceLogin = singleDeviceLogin; }
    public int getMaxLoginDevices() { return maxLoginDevices; }
    public void setMaxLoginDevices(int maxLoginDevices) { this.maxLoginDevices = maxLoginDevices; }
    public String getPasswordStrengthRegex() { return passwordStrengthRegex; }
    public void setPasswordStrengthRegex(String passwordStrengthRegex) { this.passwordStrengthRegex = passwordStrengthRegex; }
    public Login getLogin() { return login; }
    public void setLogin(Login login) { this.login = login; }
    public List<String> getIgnoreUrls() { return ignoreUrls; }
    public void setIgnoreUrls(List<String> ignoreUrls) { this.ignoreUrls = ignoreUrls == null ? new ArrayList<String>() : ignoreUrls; }

    public static class Login {
        private int maxFailCount = 5;
        private long lockWindowSeconds = 1800L;

        public int getMaxFailCount() { return maxFailCount; }
        public void setMaxFailCount(int maxFailCount) { this.maxFailCount = maxFailCount; }
        public long getLockWindowSeconds() { return lockWindowSeconds; }
        public void setLockWindowSeconds(long lockWindowSeconds) { this.lockWindowSeconds = lockWindowSeconds; }
    }
}
