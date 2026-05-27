package com.zov.smart.nova.infra.web.exception;

import com.zov.smart.nova.common.context.TraceContext;
import com.zov.smart.nova.common.core.error.CommonErrorCode;
import com.zov.smart.nova.common.core.error.ErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.core.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

/**
 * Unified API exception mapping.
 *
 * <p>This class converts predictable exceptions to the platform {@link Result}
 * envelope and keeps HTTP status separate from {@code Result.code}. It must not
 * implement authentication, authorization, persistence, or business logic.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException ex) {
        return build(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return build(CommonErrorCode.COMMON_PARAM_INVALID, firstFieldErrorMessage(ex.getBindingResult().getFieldError()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<Void>> handleBindException(BindException ex) {
        return build(CommonErrorCode.COMMON_PARAM_INVALID, firstFieldErrorMessage(ex.getBindingResult().getFieldError()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = CommonErrorCode.COMMON_PARAM_INVALID.getMessage();
        Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();
        if (iterator.hasNext()) {
            ConstraintViolation<?> violation = iterator.next();
            if (violation.getMessage() != null && !violation.getMessage().trim().isEmpty()) {
                message = violation.getMessage();
            }
        }
        return build(CommonErrorCode.COMMON_PARAM_INVALID, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return build(CommonErrorCode.COMMON_PARAM_MISSING, "缺少必要参数: " + ex.getParameterName());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Result<Void>> handleBadRequestException(Exception ex) {
        return build(CommonErrorCode.COMMON_PARAM_INVALID, CommonErrorCode.COMMON_PARAM_INVALID.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Result<Void>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return build(CommonErrorCode.COMMON_NOT_FOUND, CommonErrorCode.COMMON_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<Void>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return build(CommonErrorCode.COMMON_PARAM_INVALID, "请求方法不支持: " + ex.getMethod());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Result<Void>> handleThrowable(Throwable ex) {
        String traceId = TraceContext.get();
        log.error("Unhandled system exception, traceId={}", traceId, ex);
        return build(CommonErrorCode.COMMON_SYSTEM_ERROR, CommonErrorCode.COMMON_SYSTEM_ERROR.getMessage());
    }

    private ResponseEntity<Result<Void>> build(ErrorCode errorCode, String message) {
        Result<Void> result = Result.<Void>fail(errorCode.getCode(), chooseMessage(errorCode, message))
                .withTraceId(TraceContext.get());
        HttpStatus status = HttpStatus.valueOf(errorCode.getHttpStatus());
        return ResponseEntity.status(status).body(result);
    }

    private static String chooseMessage(ErrorCode errorCode, String message) {
        if (message == null || message.trim().isEmpty()) {
            return errorCode.getMessage();
        }
        return message;
    }

    private static String firstFieldErrorMessage(FieldError fieldError) {
        if (fieldError == null || fieldError.getDefaultMessage() == null || fieldError.getDefaultMessage().trim().isEmpty()) {
            return CommonErrorCode.COMMON_PARAM_INVALID.getMessage();
        }
        return fieldError.getDefaultMessage();
    }
}
