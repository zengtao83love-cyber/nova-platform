package com.zov.smart.nova.infra.security.token;

import com.zov.smart.nova.infra.security.model.TokenSessionDO;
import com.zov.smart.nova.infra.security.support.SecurityRedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/** Redis store for access token sessions. */
public class AccessTokenCacheStore {
    private final StringRedisTemplate redisTemplate;
    private final TokenSessionSerializer serializer;

    public AccessTokenCacheStore(StringRedisTemplate redisTemplate, TokenSessionSerializer serializer) {
        this.redisTemplate = redisTemplate;
        this.serializer = serializer;
    }

    public void save(String accessTokenId, TokenSessionDO session, long ttlSeconds) {
        redisTemplate.opsForValue().set(SecurityRedisKeys.access(accessTokenId), serializer.serialize(session), ttlSeconds, TimeUnit.SECONDS);
    }

    public TokenSessionDO get(String accessTokenId) {
        return serializer.deserialize(redisTemplate.opsForValue().get(SecurityRedisKeys.access(accessTokenId)));
    }

    public void delete(String accessTokenId) {
        redisTemplate.delete(SecurityRedisKeys.access(accessTokenId));
    }
}
