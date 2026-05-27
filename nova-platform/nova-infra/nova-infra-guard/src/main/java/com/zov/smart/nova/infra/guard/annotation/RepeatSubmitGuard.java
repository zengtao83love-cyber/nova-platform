package com.zov.smart.nova.infra.guard.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Prevents accidental repeated submission within a short Redis-backed time window.
 *
 * <p>This guard uses Redis SET NX EX semantics. It is a UX/concurrency protection
 * layer and must not replace database unique constraints.</p>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmitGuard {

    /**
     * Lock window in seconds. During this interval, the same user and method key
     * is rejected as a repeated submit.
     */
    long intervalSeconds() default 3L;

    /**
     * Message returned when the repeated submit is blocked.
     */
    String message() default "请勿重复提交";

    /**
     * Optional business key. When empty, the guard uses method signature and
     * arguments hash. The current increment intentionally does not evaluate SpEL
     * to avoid hidden runtime magic outside the Spec.
     */
    String key() default "";
}
