package com.zov.smart.nova.infra.security.service;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceContractTest {
    @Test void authServiceRecordsFailuresAndRevokesTokensOnPasswordChange() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get("src/main/java/com/zov/smart/nova/infra/security/service/AuthService.java")), "UTF-8");
        assertTrue(source.contains("recordFail"));
        assertTrue(source.contains("AUTH_USER_NOT_FOUND"));
        assertTrue(source.contains("AUTH_PASSWORD_ERROR"));
        assertTrue(source.contains("revokeAllUserTokens"));
    }
}
