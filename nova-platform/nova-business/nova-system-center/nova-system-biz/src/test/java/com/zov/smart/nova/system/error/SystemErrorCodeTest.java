package com.zov.smart.nova.system.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemErrorCodeTest {
    @Test
    void userAndRoleErrorCodesFollowSpecHttpStatus() {
        assertEquals(200, SystemErrorCode.USER_USERNAME_EXISTS.getHttpStatus());
        assertEquals(404, SystemErrorCode.USER_NOT_FOUND.getHttpStatus());
        assertEquals(200, SystemErrorCode.ROLE_CODE_EXISTS.getHttpStatus());
        assertEquals(404, SystemErrorCode.ROLE_NOT_FOUND.getHttpStatus());
        assertEquals(200, SystemErrorCode.ROLE_IN_USE.getHttpStatus());
    }
}
