package com.zov.smart.nova.infra.audit.support;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditModuleBoundaryTest {

    @Test
    void auditModuleOwnsMapperAndDoesNotUseDataAccessMapperScanOrJakarta() throws IOException {
        String combined = readAll(Paths.get("src/main/java"));
        assertFalse(combined.contains("nova.data.access"));
        assertFalse(combined.contains("@MapperScan"));
        assertFalse(combined.contains("jakarta" + "."));
        assertTrue(Files.exists(Paths.get("src/main/resources/mapper/audit/AuditOperationLogMapper.xml")));
        String factories = new String(Files.readAllBytes(Paths.get("src/main/resources/META-INF/spring.factories")), StandardCharsets.UTF_8);
        assertTrue(factories.contains("NovaAuditAutoConfiguration"));
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
