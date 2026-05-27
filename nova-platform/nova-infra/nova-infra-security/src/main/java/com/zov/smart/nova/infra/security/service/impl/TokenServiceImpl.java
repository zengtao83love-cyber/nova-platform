package com.zov.smart.nova.infra.security.service.impl;

import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.security.model.RefreshTokenResponse;
import com.zov.smart.nova.infra.security.model.TokenPair;
import com.zov.smart.nova.infra.security.model.TokenSessionDO;
import com.zov.smart.nova.infra.security.properties.NovaSecurityProperties;
import com.zov.smart.nova.infra.security.service.TokenService;
import com.zov.smart.nova.infra.security.support.SecurityRedisKeys;
import com.zov.smart.nova.infra.security.token.AccessTokenCacheStore;
import com.zov.smart.nova.infra.security.token.RefreshTokenCacheStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/** Default JWT + Redis TokenService implementation. */
public class TokenServiceImpl implements TokenService {
    private static final String CLAIM_ACCESS_TOKEN_ID = "tokenId";
    private static final String CLAIM_REFRESH_TOKEN_ID = "refreshTokenId";

    private final StringRedisTemplate redisTemplate;
    private final AccessTokenCacheStore accessStore;
    private final RefreshTokenCacheStore refreshStore;
    private final NovaSecurityProperties properties;
    private final SecretKey signingKey;

    public TokenServiceImpl(StringRedisTemplate redisTemplate,
                            AccessTokenCacheStore accessStore,
                            RefreshTokenCacheStore refreshStore,
                            NovaSecurityProperties properties) {
        this.redisTemplate = redisTemplate;
        this.accessStore = accessStore;
        this.refreshStore = refreshStore;
        this.properties = properties;
        this.properties.validateJwtSecret();
        this.signingKey = Keys.hmacShaKeyFor(properties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public TokenPair createTokenPair(TokenSessionDO session) {
        if (session == null || session.getUserId() == null) {
            throw new IllegalArgumentException("TokenSessionDO.userId must not be null");
        }
        if (properties.isSingleDeviceLogin()) {
            revokeAllUserTokens(session.getUserId());
        }
        String accessTokenId = UUID.randomUUID().toString().replace("-", "");
        String refreshTokenId = UUID.randomUUID().toString().replace("-", "");
        session.setAccessTokenId(accessTokenId);
        session.setRefreshTokenId(refreshTokenId);
        session.setIssuedAt(LocalDateTime.now());

        String accessToken = createJwt(CLAIM_ACCESS_TOKEN_ID, accessTokenId, properties.getAccessTokenExpireSeconds());
        String refreshToken = createJwt(CLAIM_REFRESH_TOKEN_ID, refreshTokenId, properties.getRefreshTokenExpireSeconds());

        accessStore.save(accessTokenId, session, properties.getAccessTokenExpireSeconds());
        refreshStore.save(refreshTokenId, session, properties.getRefreshTokenExpireSeconds());
        redisTemplate.opsForSet().add(SecurityRedisKeys.userTokens(session.getUserId()), accessTokenId);
        redisTemplate.expire(SecurityRedisKeys.userTokens(session.getUserId()), properties.getRefreshTokenExpireSeconds(), TimeUnit.SECONDS);
        trimUserTokensIfNeeded(session.getUserId());
        return new TokenPair(accessToken, refreshToken, properties.getAccessTokenExpireSeconds(), accessTokenId, refreshTokenId);
    }

    @Override
    public TokenSessionDO loadByAccessToken(String accessToken) {
        String tokenId = parseAccessTokenId(accessToken);
        TokenSessionDO session = accessStore.get(tokenId);
        if (session == null) {
            throw new BusinessException(AuthErrorCode.AUTH_TOKEN_EXPIRED);
        }
        return session;
    }

    @Override
    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        String refreshTokenId = parseRefreshTokenId(refreshToken);
        TokenSessionDO session = refreshStore.get(refreshTokenId);
        if (session == null) {
            throw new BusinessException(AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
        }
        Long refreshTtl = refreshStore.ttlSeconds(refreshTokenId);
        if (refreshTtl == null || refreshTtl <= 0) {
            throw new BusinessException(AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
        }
        if (session.getAccessTokenId() != null) {
            accessStore.delete(session.getAccessTokenId());
            redisTemplate.opsForSet().remove(SecurityRedisKeys.userTokens(session.getUserId()), session.getAccessTokenId());
        }
        String newAccessTokenId = UUID.randomUUID().toString().replace("-", "");
        session.setAccessTokenId(newAccessTokenId);
        String newAccessToken = createJwt(CLAIM_ACCESS_TOKEN_ID, newAccessTokenId, properties.getAccessTokenExpireSeconds());
        accessStore.save(newAccessTokenId, session, properties.getAccessTokenExpireSeconds());
        redisTemplate.opsForSet().add(SecurityRedisKeys.userTokens(session.getUserId()), newAccessTokenId);
        redisTemplate.expire(SecurityRedisKeys.userTokens(session.getUserId()), refreshTtl, TimeUnit.SECONDS);
        // Spec: refresh session TTL is not reset or extended; refresh token is not rotated.
        return new RefreshTokenResponse(newAccessToken, properties.getAccessTokenExpireSeconds());
    }

    @Override
    public void revokeAccessToken(String accessToken) {
        String accessTokenId = parseAccessTokenId(accessToken);
        TokenSessionDO session = accessStore.get(accessTokenId);
        accessStore.delete(accessTokenId);
        if (session != null && session.getUserId() != null) {
            redisTemplate.opsForSet().remove(SecurityRedisKeys.userTokens(session.getUserId()), accessTokenId);
        }
    }

    @Override
    public void revokeAllUserTokens(Long userId) {
        if (userId == null) { return; }
        String indexKey = SecurityRedisKeys.userTokens(userId);
        Set<String> accessTokenIds = redisTemplate.opsForSet().members(indexKey);
        if (accessTokenIds != null) {
            for (String tokenId : accessTokenIds) {
                accessStore.delete(tokenId);
            }
        }
        redisTemplate.delete(indexKey);
        redisTemplate.delete(SecurityRedisKeys.permissions(userId));
        redisTemplate.delete(SecurityRedisKeys.menus(userId));
    }

    @Override
    public String parseAccessTokenId(String accessToken) {
        return parseClaim(accessToken, CLAIM_ACCESS_TOKEN_ID, AuthErrorCode.AUTH_TOKEN_EXPIRED);
    }

    @Override
    public String parseRefreshTokenId(String refreshToken) {
        return parseClaim(refreshToken, CLAIM_REFRESH_TOKEN_ID, AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
    }

    private String createJwt(String claimName, String id, long ttlSeconds) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ttlSeconds * 1000L);
        return Jwts.builder().setIssuedAt(now).setExpiration(expiry).claim(claimName, id)
                .signWith(signingKey, SignatureAlgorithm.HS256).compact();
    }

    private String parseClaim(String jwt, String claimName, AuthErrorCode errorCode) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(jwt).getBody();
            Object value = claims.get(claimName);
            if (value == null) { throw new BusinessException(errorCode); }
            return String.valueOf(value);
        } catch (BusinessException e) {
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(errorCode, e);
        }
    }

    private void trimUserTokensIfNeeded(Long userId) {
        int maxDevices = properties.getMaxLoginDevices();
        if (maxDevices <= 0) { return; }
        String key = SecurityRedisKeys.userTokens(userId);
        Long size = redisTemplate.opsForSet().size(key);
        while (size != null && size > maxDevices) {
            String removed = redisTemplate.opsForSet().pop(key);
            if (removed == null) { break; }
            accessStore.delete(removed);
            size--;
        }
    }
}
