package com.zov.smart.nova.infra.security.config;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class NovaSecurityAutoConfigurationContractTest {
    @Test void securityUsesSecurityFilterChainAndNoLegacyAdapter() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get("src/main/java/com/zov/smart/nova/infra/security/config/NovaSecurityAutoConfiguration.java")), "UTF-8");
        assertTrue(source.contains("SecurityFilterChain"));
        assertFalse(source.contains("WebSecurityConfigurer" + "Adapter"));
        assertTrue(source.contains("SessionCreationPolicy.STATELESS"));
        assertTrue(source.contains("UsernamePasswordAuthenticationFilter"));
    }
}
