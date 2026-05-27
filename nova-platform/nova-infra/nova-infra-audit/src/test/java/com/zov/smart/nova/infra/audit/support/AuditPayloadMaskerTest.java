package com.zov.smart.nova.infra.audit.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditPayloadMaskerTest {

    @Test
    void masksPasswordTokenAndAuthorization() {
        String input = "{\"username\":\"admin\",\"password\":\"secret\",\"accessToken\":\"abcdef1234567890\",\"Authorization\":\"Bearer abc.def.ghi\"}";
        String masked = new AuditPayloadMasker().maskText(input);
        assertFalse(masked.contains("secret"));
        assertFalse(masked.contains("abcdef1234567890"));
        assertFalse(masked.contains("abc.def.ghi"));
        assertTrue(masked.contains("******"));
    }
}
