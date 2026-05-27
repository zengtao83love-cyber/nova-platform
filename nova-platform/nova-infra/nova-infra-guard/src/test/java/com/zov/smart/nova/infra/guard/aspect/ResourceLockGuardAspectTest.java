package com.zov.smart.nova.infra.guard.aspect;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.guard.annotation.ResourceLockGuard;
import com.zov.smart.nova.infra.guard.error.GuardErrorCode;
import com.zov.smart.nova.infra.guard.key.GuardKeyGenerator;
import com.zov.smart.nova.infra.guard.properties.NovaGuardProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ResourceLockGuardAspectTest {

    @Test
    void lockFailedReturnsCode() throws Throwable {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(any(String.class), any(String.class), eq(30L), eq(TimeUnit.SECONDS))).thenReturn(false);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> new ResourceLockGuardAspect(redis, new GuardKeyGenerator(), new NovaGuardProperties()).around(point(), annotation()));
        assertEquals(GuardErrorCode.GUARD_LOCK_FAILED.getCode(), ex.getCode());
        verify(redis, never()).delete(any(String.class));
    }

    @Test
    void lockedMethodReleasesOwnedLock() throws Throwable {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(any(String.class), any(String.class), eq(30L), eq(TimeUnit.SECONDS))).thenReturn(true);
        when(valueOperations.get(any(String.class))).thenReturn("not-current-owner");
        ProceedingJoinPoint point = point();
        when(point.proceed()).thenReturn("ok");

        Object result = new ResourceLockGuardAspect(redis, new GuardKeyGenerator(), new NovaGuardProperties()).around(point, annotation());
        assertEquals("ok", result);
    }

    private ProceedingJoinPoint point() {
        ProceedingJoinPoint point = mock(ProceedingJoinPoint.class);
        Signature sign = mock(Signature.class);
        when(sign.toShortString()).thenReturn("Demo.update(..)");
        when(point.getSignature()).thenReturn(sign);
        when(point.getArgs()).thenReturn(new Object[]{1L});
        return point;
    }

    private ResourceLockGuard annotation() {
        ResourceLockGuard guard = mock(ResourceLockGuard.class);
        when(guard.lockSeconds()).thenReturn(30L);
        when(guard.message()).thenReturn("当前资源正在处理中，请稍后重试");
        when(guard.key()).thenReturn("");
        return guard;
    }
}
