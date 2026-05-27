package com.zov.smart.nova.common.core.response;

import com.zov.smart.nova.common.core.error.ErrorCode;

import java.io.Serializable;

/**
 * Unified response envelope for Controller APIs.
 *
 * <p>Only {@code code = "0"} represents success. All failure instances should be
 * created from the error-code matrix through {@link #fail(ErrorCode)} or by
 * passing a known code explicitly when the caller already converted an exception.</p>
 *
 * @param <T> payload type
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MESSAGE = "success";

    private String code;
    private String message;
    private boolean success;
    private T data;
    private long timestamp;
    private String traceId;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(SUCCESS_MESSAGE);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String code, String message) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        if (errorCode == null) {
            throw new IllegalArgumentException("errorCode must not be null");
        }
        return fail(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * Fluent helper used by web infrastructure after the trace id is generated.
     *
     * @param traceId current request trace id
     * @return current result instance
     */
    public Result<T> withTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
