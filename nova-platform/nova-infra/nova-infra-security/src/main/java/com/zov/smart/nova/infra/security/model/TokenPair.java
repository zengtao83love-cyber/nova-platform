package com.zov.smart.nova.infra.security.model;

import java.io.Serializable;

/** Pair generated on successful login. */
public class TokenPair implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String accessTokenId;
    private String refreshTokenId;

    public TokenPair() {}
    public TokenPair(String accessToken, String refreshToken, Long expiresIn, String accessTokenId, String refreshTokenId) {
        this.accessToken = accessToken; this.refreshToken = refreshToken; this.expiresIn = expiresIn;
        this.accessTokenId = accessTokenId; this.refreshTokenId = refreshTokenId;
    }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
    public String getAccessTokenId() { return accessTokenId; }
    public void setAccessTokenId(String accessTokenId) { this.accessTokenId = accessTokenId; }
    public String getRefreshTokenId() { return refreshTokenId; }
    public void setRefreshTokenId(String refreshTokenId) { this.refreshTokenId = refreshTokenId; }
}
