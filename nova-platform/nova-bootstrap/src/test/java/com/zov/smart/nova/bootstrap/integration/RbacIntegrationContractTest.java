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

/** T-132 RBAC integration contract checks. */
class RbacIntegrationContractTest {

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
    void rbacMustExposePermissionAnnotationAndChecker() throws IOException {
        Path root = findProjectRoot();
        String annotation = read(root.resolve("nova-infra/nova-infra-security/src/main/java/com/zov/smart/nova/infra/security/annotation/RequirePermission.java"));
        String checker = read(root.resolve("nova-infra/nova-infra-security/src/main/java/com/zov/smart/nova/infra/security/permission/PermissionChecker.java"));
        assertTrue(annotation.contains("String[] value"));
        assertTrue(checker.contains("super") || checker.contains("Super"));
        assertTrue(checker.contains("AUTH_FORBIDDEN"));
    }

    @Test
    void systemControllersMustDeclarePermissionCodes() throws IOException {
        Path root = findProjectRoot();
        List<Path> controllers = javaFiles(root.resolve("nova-business/nova-system-center/nova-system-biz/src/main/java/com/zov/smart/nova/system/controller"));
        String joined = controllers.stream().map(path -> {
            try { return read(path); } catch (IOException ex) { throw new IllegalStateException(ex); }
        }).collect(Collectors.joining("\n"));
        assertTrue(joined.contains("system:user:list"));
        assertTrue(joined.contains("system:role:list"));
        assertTrue(joined.contains("system:menu:list"));
        assertTrue(joined.contains("system:dict:list"));
        assertTrue(joined.contains("system:config:list"));
    }
}
