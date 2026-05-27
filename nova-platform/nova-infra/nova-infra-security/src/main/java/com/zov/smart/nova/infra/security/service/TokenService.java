package com.zov.smart.nova.infra.security.service;

import com.zov.smart.nova.infra.security.model.RefreshTokenResponse;
import com.zov.smart.nova.infra.security.model.TokenPair;
import com.zov.smart.nova.infra.security.model.TokenSessionDO;

public interface TokenService {
    TokenPair createTokenPair(TokenSessionDO session);
    TokenSessionDO loadByAccessToken(String accessToken);
    RefreshTokenResponse refreshAccessToken(String refreshToken);
    void revokeAccessToken(String accessToken);
    void revokeAllUserTokens(Long userId);
    String parseAccessTokenId(String accessToken);
    String parseRefreshTokenId(String refreshToken);
}
