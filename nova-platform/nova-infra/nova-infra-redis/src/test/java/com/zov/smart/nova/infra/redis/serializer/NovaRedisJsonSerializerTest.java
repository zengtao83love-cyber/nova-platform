package com.zov.smart.nova.infra.redis.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class NovaRedisJsonSerializerTest {

    @Test
    void shouldSerializeAndDeserializeWithExplicitType() {
        RedisSerializer<SessionFixture> serializer = NovaRedisSerializers.json(new ObjectMapper(), SessionFixture.class);
        SessionFixture fixture = new SessionFixture();
        fixture.setUserId(1001L);
        fixture.setUsername("admin");

        byte[] bytes = serializer.serialize(fixture);
        SessionFixture actual = serializer.deserialize(bytes);

        assertEquals(1001L, actual.getUserId());
        assertEquals("admin", actual.getUsername());
    }

    @Test
    void shouldNotWritePolymorphicClassMetadata() {
        RedisSerializer<SessionFixture> serializer = NovaRedisSerializers.json(new ObjectMapper(), SessionFixture.class);
        SessionFixture fixture = new SessionFixture();
        fixture.setUserId(1001L);
        fixture.setUsername("admin");

        String json = new String(serializer.serialize(fixture), StandardCharsets.UTF_8);

        assertFalse(json.contains("@class"));
        assertFalse(json.contains(SessionFixture.class.getName()));
    }

    @Test
    void shouldReturnNullForEmptyPayload() {
        RedisSerializer<SessionFixture> serializer = NovaRedisSerializers.json(new ObjectMapper(), SessionFixture.class);

        assertEquals(0, serializer.serialize(null).length);
        assertNull(serializer.deserialize(null));
        assertNull(serializer.deserialize(new byte[0]));
    }

    public static class SessionFixture {
        private Long userId;
        private String username;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
