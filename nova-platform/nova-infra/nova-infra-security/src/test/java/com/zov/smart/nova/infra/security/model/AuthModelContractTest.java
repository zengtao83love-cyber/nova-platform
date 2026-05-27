package com.zov.smart.nova.infra.security.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthModelContractTest {
    @Test void loginResponseUsesBearerAndRefreshResponseDoesNotExposeRefreshToken() throws Exception {
        LoginResponse login = new LoginResponse("a", "r", 7200L);
        assertEquals("Bearer", login.getTokenType());
        assertNotNull(RefreshTokenResponse.class.getDeclaredField("accessToken"));
        assertThrows(NoSuchFieldException.class, () -> RefreshTokenResponse.class.getDeclaredField("refreshToken"));
    }

    @Test void tokenSessionDoesNotStoreRawTokenStrings() {
        for (java.lang.reflect.Field f : TokenSessionDO.class.getDeclaredFields()) {
            assertNotEquals("accessToken", f.getName());
            assertNotEquals("refreshToken", f.getName());
        }
    }
}
