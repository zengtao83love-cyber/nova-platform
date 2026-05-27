package com.zov.smart.nova.infra.redis.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Factory methods for Redis serializers used by infrastructure and later modules.
 */
public final class NovaRedisSerializers {

    private NovaRedisSerializers() {
    }

    public static <T> RedisSerializer<T> json(ObjectMapper objectMapper, Class<T> targetType) {
        return new NovaRedisJsonSerializer<T>(objectMapper, targetType);
    }
}
