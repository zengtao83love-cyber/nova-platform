package com.zov.smart.nova.infra.redis.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

/**
 * Explicit-type JSON Redis serializer.
 *
 * <p>This serializer intentionally does not enable Jackson default typing and
 * does not write polymorphic class metadata. Callers must provide the concrete
 * target type when they need reliable deserialization, for example TokenSession
 * in the security module.</p>
 *
 * @param <T> concrete value type
 */
public class NovaRedisJsonSerializer<T> implements RedisSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> targetType;

    public NovaRedisJsonSerializer(ObjectMapper objectMapper, Class<T> targetType) {
        if (objectMapper == null) {
            throw new IllegalArgumentException("objectMapper must not be null");
        }
        if (targetType == null) {
            throw new IllegalArgumentException("targetType must not be null");
        }
        this.objectMapper = objectMapper;
        this.targetType = targetType;
    }

    @Override
    public byte[] serialize(T value) throws SerializationException {
        if (value == null) {
            return new byte[0];
        }
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (IOException ex) {
            throw new SerializationException("Failed to serialize Redis value as JSON", ex);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, targetType);
        } catch (IOException ex) {
            throw new SerializationException("Failed to deserialize Redis JSON value as " + targetType.getName(), ex);
        }
    }
}
