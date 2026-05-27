package com.zov.smart.nova.bootstrap;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/** Contract tests for bootstrap-owned application.yml. */
class ApplicationYamlContractTest {

    @Test
    void applicationYamlContainsRequiredRuntimeSections() throws Exception {
        InputStream input = getClass().getClassLoader().getResourceAsStream("application.yml");
        assertNotNull(input);
        String yaml = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        assertTrue(yaml.contains("NOVA_SECURITY_JWT_SECRET"));
        assertTrue(yaml.contains("classpath*:/mapper/**/*.xml"));
        assertTrue(yaml.contains("X-Request-Id") || yaml.contains("Authorization"));
        assertTrue(yaml.contains("nova:"));
        assertTrue(yaml.contains("mybatis-plus:"));
    }
}
