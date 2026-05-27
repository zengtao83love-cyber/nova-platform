package com.zov.smart.nova.infra.security.model;

import java.io.Serializable;

public class RefreshTokenResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private Long expiresIn;
    public RefreshTokenResponse() {}
    public RefreshTokenResponse(String accessToken, Long expiresIn) { this.accessToken = accessToken; this.expiresIn = expiresIn; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
}
