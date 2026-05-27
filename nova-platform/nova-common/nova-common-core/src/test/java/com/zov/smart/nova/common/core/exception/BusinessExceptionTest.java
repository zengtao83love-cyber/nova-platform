package com.zov.smart.nova.common.core.exception;

import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.error.CommonErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessExceptionTest {

    @Test
    void businessExceptionCarriesErrorCodeMessageAndHttpStatus() {
        BusinessException exception = new BusinessException(CommonErrorCode.COMMON_CONCURRENT_CONFLICT);

        assertSame(CommonErrorCode.COMMON_CONCURRENT_CONFLICT, exception.getErrorCode());
        assertEquals("COMMON_CONCURRENT_CONFLICT", exception.getCode());
        assertEquals("数据已被其他人修改，请刷新后重试", exception.getMessage());
        assertEquals(409, exception.getHttpStatus());
    }

    @Test
    void businessExceptionAllowsDynamicMessageWhileKeepingStableCode() {
        BusinessException exception = new BusinessException(AuthErrorCode.AUTH_FORBIDDEN, "当前用户缺少 system:user:list 权限");

        assertSame(AuthErrorCode.AUTH_FORBIDDEN, exception.getErrorCode());
        assertEquals("AUTH_FORBIDDEN", exception.getCode());
        assertEquals("当前用户缺少 system:user:list 权限", exception.getMessage());
        assertEquals(403, exception.getHttpStatus());
    }

    @Test
    void businessExceptionKeepsCause() {
        IllegalStateException cause = new IllegalStateException("db conflict");

        BusinessException exception = new BusinessException(CommonErrorCode.COMMON_SYSTEM_ERROR, cause);

        assertSame(cause, exception.getCause());
        assertEquals(500, exception.getHttpStatus());
    }

    @Test
    void nullErrorCodeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BusinessException(null));
    }
}
