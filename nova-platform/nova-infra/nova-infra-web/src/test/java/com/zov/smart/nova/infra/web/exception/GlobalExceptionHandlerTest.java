package com.zov.smart.nova.infra.web.exception;

import com.zov.smart.nova.common.context.TraceContext;
import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.error.CommonErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.core.response.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    @AfterEach
    void tearDown() {
        TraceContext.clear();
    }

    @Test
    void shouldMapBusinessExceptionWithSpecifiedHttpStatus() {
        TraceContext.set("trace-forbidden");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<Result<Void>> entity = handler.handleBusinessException(new BusinessException(AuthErrorCode.AUTH_FORBIDDEN));

        assertEquals(403, entity.getStatusCodeValue());
        assertNotNull(entity.getBody());
        assertFalse(entity.getBody().isSuccess());
        assertEquals(AuthErrorCode.AUTH_FORBIDDEN.getCode(), entity.getBody().getCode());
        assertEquals("trace-forbidden", entity.getBody().getTraceId());
    }

    @Test
    void shouldKeepBusinessExpectedErrorAsHttp200WhenErrorMatrixRequiresIt() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<Result<Void>> entity = handler.handleBusinessException(new BusinessException(AuthErrorCode.AUTH_ACCOUNT_LOCKED));

        assertEquals(200, entity.getStatusCodeValue());
        assertEquals(AuthErrorCode.AUTH_ACCOUNT_LOCKED.getCode(), entity.getBody().getCode());
    }

    @Test
    void shouldMapBindExceptionToParamInvalid() {
        TraceContext.set("trace-bind");
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        BindException exception = new BindException(new DemoCommand(), "demoCommand");
        exception.rejectValue("name", "NotBlank", "名称不能为空");

        ResponseEntity<Result<Void>> entity = handler.handleBindException(exception);

        assertEquals(400, entity.getStatusCodeValue());
        assertEquals(CommonErrorCode.COMMON_PARAM_INVALID.getCode(), entity.getBody().getCode());
        assertEquals("名称不能为空", entity.getBody().getMessage());
        assertEquals("trace-bind", entity.getBody().getTraceId());
    }

    static class DemoCommand {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
