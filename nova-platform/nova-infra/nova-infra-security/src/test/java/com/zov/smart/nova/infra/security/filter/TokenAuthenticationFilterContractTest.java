package com.zov.smart.nova.infra.security.filter;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class TokenAuthenticationFilterContractTest {
    @Test void filterWritesAndClearsLoginUserContext() throws Exception {
        String source = new String(Files.readAllBytes(Paths.get("src/main/java/com/zov/smart/nova/infra/security/filter/TokenAuthenticationFilter.java")), "UTF-8");
        assertTrue(source.contains("LoginUserContext.set"));
        assertTrue(source.contains("LoginUserContext.clear"));
        assertTrue(source.contains("SecurityContextHolder.clearContext"));
    }
}
