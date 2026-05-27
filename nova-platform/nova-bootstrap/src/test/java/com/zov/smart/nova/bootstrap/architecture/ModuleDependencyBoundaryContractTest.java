package com.zov.smart.nova.bootstrap.architecture;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** T-136 module dependency boundary checks. */
class ModuleDependencyBoundaryContractTest {

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
    void dataAccessMustNotDependOnInfraOrBusiness() throws IOException {
        Path root = findProjectRoot();
        String pom = read(root.resolve("nova-data-access/pom.xml"));
        assertFalse(pom.contains("nova-infra-"));
        assertFalse(pom.contains("nova-system-api"));
        assertFalse(pom.contains("nova-system-biz"));
        assertFalse(pom.contains("nova-bootstrap"));
    }

    @Test
    void systemApiMustStayAsPureContractModule() throws IOException {
        Path root = findProjectRoot();
        String pom = read(root.resolve("nova-business/nova-system-center/nova-system-api/pom.xml"));
        assertFalse(pom.contains("nova-data-access"));
        assertFalse(pom.contains("nova-infra-"));
        assertFalse(pom.contains("spring-boot-starter-web"));
        assertFalse(pom.contains("mybatis"));
    }

    @Test
    void authControllerMustOnlyExistInsideInfraSecurity() throws IOException {
        Path root = findProjectRoot();
        List<Path> authControllers = javaFiles(root).stream()
                .filter(path -> path.getFileName().toString().equals("AuthController.java"))
                .collect(Collectors.toList());
        assertTrue(authControllers.size() == 1, "AuthController must be unique");
        assertTrue(authControllers.get(0).toString().contains("nova-infra-security"));
    }
}
