package com.zov.smart.nova.infra.guard.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Serializes concurrent processing of the same business resource by Redis SET NX EX.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceLockGuard {

    /**
     * Resource key. When empty, method signature and arguments hash are used.
     */
    String key() default "";

    /**
     * Lock TTL in seconds, used as a safety net when the process crashes.
     */
    long lockSeconds() default 30L;

    /**
     * Message returned when the resource is already being processed.
     */
    String message() default "当前资源正在处理中，请稍后重试";
}
