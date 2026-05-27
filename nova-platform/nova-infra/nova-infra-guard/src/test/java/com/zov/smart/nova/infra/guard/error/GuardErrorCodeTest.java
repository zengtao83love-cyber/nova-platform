package com.zov.smart.nova.infra.guard.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuardErrorCodeTest {

    @Test
    void guardErrorsUseExpectedBusinessHttpStatus() {
        assertEquals("GUARD_REPEAT_SUBMIT", GuardErrorCode.GUARD_REPEAT_SUBMIT.getCode());
        assertEquals(200, GuardErrorCode.GUARD_REPEAT_SUBMIT.getHttpStatus());
        assertEquals("GUARD_LOCK_FAILED", GuardErrorCode.GUARD_LOCK_FAILED.getCode());
        assertEquals(200, GuardErrorCode.GUARD_LOCK_FAILED.getHttpStatus());
    }
}
