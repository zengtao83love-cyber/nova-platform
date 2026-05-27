package com.zov.smart.nova.infra.security.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Redis-backed security session.
 *
 * <p>The Redis value stores only session metadata and token ids. It must never
 * store the raw accessToken or raw refreshToken string.</p>
 */
public class TokenSessionDO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private String realName;
    private String nickname;
    private String mobile;
    private String email;
    private String avatar;
    private Boolean superAdminFlag = Boolean.FALSE;
    private Set<String> roles = new LinkedHashSet<String>();
    private Set<String> permissions = new LinkedHashSet<String>();
    private String clientIp;
    private String userAgent;
    private LocalDateTime issuedAt;
    private String accessTokenId;
    private String refreshTokenId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public Boolean getSuperAdminFlag() { return superAdminFlag; }
    public void setSuperAdminFlag(Boolean superAdminFlag) { this.superAdminFlag = Boolean.TRUE.equals(superAdminFlag); }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(roles); }
    public Set<String> getPermissions() { return permissions; }
    public void setPermissions(Set<String> permissions) { this.permissions = permissions == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(permissions); }
    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    public String getAccessTokenId() { return accessTokenId; }
    public void setAccessTokenId(String accessTokenId) { this.accessTokenId = accessTokenId; }
    public String getRefreshTokenId() { return refreshTokenId; }
    public void setRefreshTokenId(String refreshTokenId) { this.refreshTokenId = refreshTokenId; }
}
