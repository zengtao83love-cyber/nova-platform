package com.zov.smart.nova.infra.security.token;

import com.zov.smart.nova.infra.security.model.TokenSessionDO;
import com.zov.smart.nova.infra.security.support.SecurityRedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/** Redis store for refresh token sessions. Refresh TTL is read but never extended by refresh flow. */
public class RefreshTokenCacheStore {
    private final StringRedisTemplate redisTemplate;
    private final TokenSessionSerializer serializer;

    public RefreshTokenCacheStore(StringRedisTemplate redisTemplate, TokenSessionSerializer serializer) {
        this.redisTemplate = redisTemplate;
        this.serializer = serializer;
    }

    public void save(String refreshTokenId, TokenSessionDO session, long ttlSeconds) {
        redisTemplate.opsForValue().set(SecurityRedisKeys.refresh(refreshTokenId), serializer.serialize(session), ttlSeconds, TimeUnit.SECONDS);
    }

    public TokenSessionDO get(String refreshTokenId) {
        return serializer.deserialize(redisTemplate.opsForValue().get(SecurityRedisKeys.refresh(refreshTokenId)));
    }

    public Long ttlSeconds(String refreshTokenId) {
        return redisTemplate.getExpire(SecurityRedisKeys.refresh(refreshTokenId), TimeUnit.SECONDS);
    }

    public void delete(String refreshTokenId) {
        redisTemplate.delete(SecurityRedisKeys.refresh(refreshTokenId));
    }
}
