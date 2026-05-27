package com.zov.smart.nova.infra.security.properties;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NovaSecurityPropertiesTest {
    @Test void defaultValuesMatchSpec() {
        NovaSecurityProperties p = new NovaSecurityProperties();
        assertEquals("Authorization", p.getTokenHeader());
        assertEquals("Bearer", p.getTokenPrefix());
        assertEquals(7200L, p.getAccessTokenExpireSeconds());
        assertEquals(604800L, p.getRefreshTokenExpireSeconds());
        assertEquals(5, p.getLogin().getMaxFailCount());
        assertEquals(1800L, p.getLogin().getLockWindowSeconds());
        assertEquals("NOVA_SECURITY_JWT_SECRET", NovaSecurityProperties.ENV_JWT_SECRET);
    }
}
