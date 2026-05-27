package com.zov.smart.nova.infra.security.annotation;

import com.zov.smart.nova.infra.security.permission.LogicalOperator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    String[] value();
    LogicalOperator logical() default LogicalOperator.AND;
}
