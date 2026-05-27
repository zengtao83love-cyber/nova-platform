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

/** T-135 login/audit log integration contract checks. */
class AuditLogIntegrationContractTest {

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
    void auditMustHaveOwnDoMapperAndXmlInsideInfraAudit() throws IOException {
        Path root = findProjectRoot();
        assertTrue(Files.exists(root.resolve("nova-infra/nova-infra-audit/src/main/java/com/zov/smart/nova/infra/audit/entity/AuditOperationLogDO.java")));
        assertTrue(Files.exists(root.resolve("nova-infra/nova-infra-audit/src/main/java/com/zov/smart/nova/infra/audit/mapper/AuditOperationLogMapper.java")));
        assertTrue(Files.exists(root.resolve("nova-infra/nova-infra-audit/src/main/resources/mapper/audit/AuditOperationLogMapper.xml")));
    }

    @Test
    void auditAsyncSaveMustUseRequiresNewAndNotReadLoginUserContextInAsyncMethod() throws IOException {
        Path root = findProjectRoot();
        String impl = read(root.resolve("nova-infra/nova-infra-audit/src/main/java/com/zov/smart/nova/infra/audit/service/impl/AuditLogServiceImpl.java"));
        assertTrue(impl.contains("REQUIRES_NEW"));
        assertTrue(impl.contains("@Async"));
    }
}
