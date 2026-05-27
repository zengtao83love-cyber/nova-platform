package com.zov.smart.nova.bootstrap.integration;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * T-131 authentication integration contract checks.
 * These checks are intentionally static and environment-free: they verify that the generated
 * runtime assembly exposes the required Auth APIs and wiring without requiring MySQL/Redis.
 */
class AuthIntegrationContractTest {

    private static Path findProjectRoot() {
        Path current = Paths.get("").toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
            Path pom = current.resolve("pom.xml");
            if (Files.exists(pom)) {
                try {
                    String text = new String(Files.readAllBytes(pom), StandardCharsets.UTF_8);
                    if (text.contains("<artifactId>nova-platform</artifactId>")) {
                        return current;
                    }
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }
            }
            current = current.getParent();
        }
        throw new IllegalStateException("Cannot locate nova-platform project root");
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static List<Path> javaFiles(Path root) throws IOException {
        try (Stream<Path> stream = Files.walk(root)) {
            return stream.filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        }
    }


    @Test
    void authControllerMustExposeAllAuthenticationEndpoints() throws IOException {
        Path root = findProjectRoot();
        String controller = read(root.resolve("nova-infra/nova-infra-security/src/main/java/com/zov/smart/nova/infra/security/controller/AuthController.java"));
        assertTrue(controller.contains("/api/auth/login"));
        assertTrue(controller.contains("/api/auth/logout"));
        assertTrue(controller.contains("/api/auth/refresh"));
        assertTrue(controller.contains("/api/auth/me"));
        assertTrue(controller.contains("/api/auth/me/profile"));
        assertTrue(controller.contains("/api/auth/me/password"));
        assertTrue(controller.contains("/api/auth/menus"));
        assertTrue(controller.contains("/api/auth/permissions"));
    }

    @Test
    void tokenServiceMustSupportAccessAndRefreshSessions() throws IOException {
        Path root = findProjectRoot();
        String service = read(root.resolve("nova-infra/nova-infra-security/src/main/java/com/zov/smart/nova/infra/security/service/impl/TokenServiceImpl.java"));
        String keys = read(root.resolve("nova-infra/nova-infra-security/src/main/java/com/zov/smart/nova/infra/security/support/SecurityRedisKeys.java"));
        assertTrue(service.contains("refresh"), "refresh token strategy must be implemented");
        assertTrue(keys.contains("nova:security:access:"));
        assertTrue(keys.contains("nova:security:refresh:"));
        assertTrue(keys.contains("nova:security:user-tokens:"));
    }
}
