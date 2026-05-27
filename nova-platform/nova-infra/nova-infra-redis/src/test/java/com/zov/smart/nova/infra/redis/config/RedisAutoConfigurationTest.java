package com.zov.smart.nova.infra.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisAutoConfigurationTest {

    @Test
    void shouldCreateObjectMapperWithoutDefaultTyping() throws Exception {
        ObjectMapper objectMapper = new RedisAutoConfiguration().novaRedisObjectMapper();

        assertNotNull(objectMapper);
        String serializedMapper = objectMapper.getSerializationConfig().toString();
        assertFalse(serializedMapper.contains("DefaultTypeResolverBuilder"));
    }

    @Test
    void shouldConfigureStringKeysAndJsonValues() throws Exception {
        RedisAutoConfiguration configuration = new RedisAutoConfiguration();
        ObjectMapper objectMapper = configuration.novaRedisObjectMapper();
        RedisTemplate<String, Object> template = configuration.redisTemplate(new NoopRedisConnectionFactory(), objectMapper);

        assertTrue(template.getKeySerializer() instanceof StringRedisSerializer);
        assertTrue(template.getHashKeySerializer() instanceof StringRedisSerializer);
        assertTrue(template.getValueSerializer() instanceof Jackson2JsonRedisSerializer);
        assertTrue(template.getHashValueSerializer() instanceof Jackson2JsonRedisSerializer);
    }

    /**
     * Minimal test double. The RedisTemplate only stores the reference during this
     * unit test; no Redis operation is executed.
     */
    static class NoopRedisConnectionFactory implements RedisConnectionFactory {
        @Override
        public org.springframework.data.redis.connection.RedisConnection getConnection() {
            throw new UnsupportedOperationException("No Redis connection in unit test");
        }

        @Override
        public org.springframework.data.redis.connection.RedisClusterConnection getClusterConnection() {
            throw new UnsupportedOperationException("No Redis cluster connection in unit test");
        }

        @Override
        public boolean getConvertPipelineAndTxResults() {
            return false;
        }
    }
}
