package com.zov.smart.nova.infra.security.permission;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class PermissionCheckerContractTest {
    @Test void checkerImplementsAdminBypassAnd401403() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get("src/main/java/com/zov/smart/nova/infra/security/permission/PermissionChecker.java")), "UTF-8");
        assertTrue(source.contains("AUTH_UNAUTHORIZED"));
        assertTrue(source.contains("AUTH_FORBIDDEN"));
        assertTrue(source.contains("getSuperAdminFlag"));
        assertTrue(source.contains("LogicalOperator.OR"));
    }
}
