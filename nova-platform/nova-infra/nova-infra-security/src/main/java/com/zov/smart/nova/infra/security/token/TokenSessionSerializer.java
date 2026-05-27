package com.zov.smart.nova.infra.security.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zov.smart.nova.infra.security.model.TokenSessionDO;

/** Explicit serializer for TokenSessionDO. No default typing or polymorphic deserialization is used. */
public class TokenSessionSerializer {
    private final ObjectMapper objectMapper;

    public TokenSessionSerializer() {
        this(new ObjectMapper().registerModule(new JavaTimeModule()));
    }

    public TokenSessionSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serialize(TokenSessionDO session) {
        if (session == null) { return null; }
        try {
            return objectMapper.writeValueAsString(session);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Serialize TokenSessionDO failed", e);
        }
    }

    public TokenSessionDO deserialize(String json) {
        if (json == null || json.trim().isEmpty()) { return null; }
        try {
            return objectMapper.readValue(json, TokenSessionDO.class);
        } catch (Exception e) {
            throw new IllegalStateException("Deserialize TokenSessionDO failed", e);
        }
    }
}
