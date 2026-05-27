package com.zov.smart.nova.common.context;

/**
 * RequestId 访问门面。
 *
 * <p>项目统一使用 X-Request-Id 请求头。由于请求链路的 requestId 与 traceId 在当前阶段保持一致，
 * 本类委托 {@link TraceContext} 存取，避免出现两个 ThreadLocal 值不一致。</p>
 */
public final class RequestIdHolder {
    public static final String HEADER_NAME = "X-Request-Id";

    private RequestIdHolder() {
    }

    public static void set(String requestId) {
        TraceContext.set(requestId);
    }

    public static String get() {
        return TraceContext.get();
    }

    public static String peek() {
        return TraceContext.peek();
    }

    public static void clear() {
        TraceContext.clear();
    }
}
