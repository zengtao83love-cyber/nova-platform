package com.zov.smart.nova.infra.guard.support;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuardModuleBoundaryTest {

    @Test
    void guardModuleDoesNotDependOnDataAccessOrJakarta() throws IOException {
        Path module = Paths.get("src/main/java");
        String combined = readAll(module);
        assertFalse(combined.contains("nova.data.access"));
        assertFalse(combined.contains("jakarta" + "."));
        String factories = new String(Files.readAllBytes(Paths.get("src/main/resources/META-INF/spring.factories")), StandardCharsets.UTF_8);
        assertTrue(factories.contains("NovaGuardAutoConfiguration"));
    }

    private String readAll(Path root) throws IOException {
        if (!Files.exists(root)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Files.walk(root)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {
                    try {
                        builder.append(new String(Files.readAllBytes(path), StandardCharsets.UTF_8)).append('\n');
                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                });
        return builder.toString();
    }
}
