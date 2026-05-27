package com.zov.smart.nova.common.core.response;

import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.error.CommonErrorCode;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResultTest {

    @Test
    void successWithoutDataUsesOnlySuccessCodeZero() {
        Result<Void> result = Result.success();

        assertTrue(result.isSuccess());
        assertEquals("0", result.getCode());
        assertEquals("success", result.getMessage());
        assertNull(result.getData());
        assertTrue(result.getTimestamp() > 0);
        assertTrue(result instanceof Serializable);
    }

    @Test
    void successWithDataKeepsPayload() {
        String payload = "ok";

        Result<String> result = Result.success(payload);

        assertTrue(result.isSuccess());
        assertEquals("0", result.getCode());
        assertSame(payload, result.getData());
    }

    @Test
    void failWithRawCodeAndMessageCreatesFailureResult() {
        Result<Void> result = Result.fail(CommonErrorCode.COMMON_PARAM_INVALID.getCode(), CommonErrorCode.COMMON_PARAM_INVALID.getMessage());

        assertFalse(result.isSuccess());
        assertEquals("COMMON_PARAM_INVALID", result.getCode());
        assertEquals("参数校验失败", result.getMessage());
        assertNull(result.getData());
        assertTrue(result.getTimestamp() > 0);
    }

    @Test
    void failWithErrorCodeUsesMatrixMessage() {
        Result<Void> result = Result.fail(AuthErrorCode.AUTH_FORBIDDEN);

        assertFalse(result.isSuccess());
        assertEquals("AUTH_FORBIDDEN", result.getCode());
        assertEquals("无操作权限", result.getMessage());
    }

    @Test
    void withTraceIdSetsCurrentTraceIdAndKeepsSameInstance() {
        Result<String> result = Result.success("hello");

        Result<String> same = result.withTraceId("trace-001");

        assertSame(result, same);
        assertEquals("trace-001", result.getTraceId());
    }

    @Test
    void failWithNullErrorCodeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Result.fail(null));
        assertNotNull(Result.fail(CommonErrorCode.COMMON_SYSTEM_ERROR));
    }
}
