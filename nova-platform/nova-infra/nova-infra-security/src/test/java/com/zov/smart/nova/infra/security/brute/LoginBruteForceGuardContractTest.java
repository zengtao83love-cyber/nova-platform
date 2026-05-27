package com.zov.smart.nova.infra.security.brute;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class LoginBruteForceGuardContractTest {
    @Test void guardUsesConfirmedRedisKeyAndAccountLockedError() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get("src/main/java/com/zov/smart/nova/infra/security/brute/LoginBruteForceGuard.java")), "UTF-8");
        assertTrue(source.contains("SecurityRedisKeys.loginFail"));
        assertTrue(source.contains("AUTH_ACCOUNT_LOCKED"));
    }
}
