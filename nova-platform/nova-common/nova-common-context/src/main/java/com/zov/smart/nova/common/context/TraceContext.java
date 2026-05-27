package com.zov.smart.nova.common.context;

import java.util.concurrent.ThreadLocalRandom;

/**
 * TraceId 线程上下文。
 *
 * <p>TraceId 由 Web 过滤器写入，在 Result、日志、审计中透传；请求结束必须 clear。</p>
 */
public final class TraceContext {
    private static final ThreadLocal<String> TRACE_HOLDER = new ThreadLocal<>();

    private TraceContext() {
    }

    public static void set(String traceId) {
        if (isBlank(traceId)) {
            clear();
            return;
        }
        TRACE_HOLDER.set(traceId.trim());
    }

    public static String get() {
        String traceId = TRACE_HOLDER.get();
        if (isBlank(traceId)) {
            traceId = generateFastTraceId();
            TRACE_HOLDER.set(traceId);
        }
        return traceId;
    }

    public static String peek() {
        return TRACE_HOLDER.get();
    }

    public static void clear() {
        TRACE_HOLDER.remove();
    }

    /**
     * 使用 ThreadLocalRandom 生成 32 位十六进制 TraceId，避免 UUID.randomUUID 的 SecureRandom 锁竞争。
     */
    public static String generateFastTraceId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long mostSigBits = random.nextLong();
        long leastSigBits = random.nextLong();
        return digits(mostSigBits >> 32, 8)
                + digits(mostSigBits >> 16, 4)
                + digits(mostSigBits, 4)
                + digits(leastSigBits >> 48, 4)
                + digits(leastSigBits, 12);
    }

    private static String digits(long value, int digits) {
        long high = 1L << (digits * 4);
        return Long.toHexString(high | (value & (high - 1))).substring(1);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
