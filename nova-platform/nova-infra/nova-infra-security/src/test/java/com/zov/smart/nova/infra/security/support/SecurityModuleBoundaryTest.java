package com.zov.smart.nova.infra.security.support;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SecurityModuleBoundaryTest {
    @Test void securityModuleDoesNotDependOnSystemBizOrForbiddenNamespaceAndHasSpringFactories() throws Exception {
        Path module = Paths.get("");
        try (Stream<Path> files = Files.walk(module)) {
            files.filter(Files::isRegularFile).forEach(path -> {
                try {
                    String text = new String(Files.readAllBytes(path), "UTF-8");
                    assertFalse(text.contains("nova-system-biz"), path.toString());
                    assertFalse(text.contains("jakarta" + "."), path.toString());
                    assertFalse(text.contains("WebSecurityConfigurer" + "Adapter"), path.toString());
                } catch (IOException e) { throw new RuntimeException(e); }
            });
        }
        String factories = new String(Files.readAllBytes(Paths.get("src/main/resources/META-INF/spring.factories")), "UTF-8");
        assertTrue(factories.contains("NovaSecurityAutoConfiguration"));
    }
}
