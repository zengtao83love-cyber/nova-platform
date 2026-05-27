package com.zov.smart.nova.infra.security.permission;

import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/** AOP implementation for @RequirePermission. */
@Aspect
public class PermissionAspect {
    private final PermissionChecker permissionChecker;

    public PermissionAspect(PermissionChecker permissionChecker) { this.permissionChecker = permissionChecker; }

    @Around("@within(com.zov.smart.nova.infra.security.annotation.RequirePermission) || @annotation(com.zov.smart.nova.infra.security.annotation.RequirePermission)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequirePermission annotation = resolveAnnotation(joinPoint);
        if (annotation != null) {
            permissionChecker.check(annotation.value(), annotation.logical());
        }
        return joinPoint.proceed();
    }

    private RequirePermission resolveAnnotation(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RequirePermission annotation = AnnotatedElementUtils.findMergedAnnotation(method, RequirePermission.class);
        if (annotation != null) { return annotation; }
        return AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), RequirePermission.class);
    }
}
