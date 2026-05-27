package com.zov.smart.nova.infra.audit.annotation;

import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an application operation that should be written to audit_operation_log.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /** Operation display name, for example "新增用户". */
    String name();

    /** Stable operation type persisted as enum code. */
    AuditOperationTypeEnum type() default AuditOperationTypeEnum.OTHER;

    /** Whether method arguments should be serialized and masked. */
    boolean recordParams() default true;

    /** Whether the returned value should be serialized and masked into response_body. */
    boolean recordResult() default true;
}
