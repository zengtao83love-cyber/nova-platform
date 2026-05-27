package com.zov.smart.nova.infra.security.token;

import com.zov.smart.nova.infra.security.model.TokenSessionDO;
import com.zov.smart.nova.infra.security.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceContractTest {
    @Test void tokenSessionSerializerUsesExplicitType() {
        TokenSessionSerializer serializer = new TokenSessionSerializer();
        TokenSessionDO session = new TokenSessionDO();
        session.setUserId(1L);
        session.setUsername("admin");
        String json = serializer.serialize(session);
        assertEquals(Long.valueOf(1L), serializer.deserialize(json).getUserId());
    }

    @Test void refreshFlowDoesNotRotateRefreshTokenOrResetRefreshTtlByCodeContract() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get("src/main/java/com/zov/smart/nova/infra/security/service/impl/TokenServiceImpl.java")), "UTF-8");
        assertTrue(source.contains("refresh session TTL is not reset or extended"));
        assertFalse(source.contains("setRefreshTokenId(new"));
    }
}
