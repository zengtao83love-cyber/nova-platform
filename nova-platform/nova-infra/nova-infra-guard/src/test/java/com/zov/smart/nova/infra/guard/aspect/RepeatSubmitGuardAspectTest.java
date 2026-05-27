package com.zov.smart.nova.infra.guard.aspect;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RepeatSubmitGuardAspectTest {

    @Test
    void repeatSubmitBlocked() throws Throwable {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(any(String.class), eq("1"), eq(3L), eq(TimeUnit.SECONDS))).thenReturn(false);

        RepeatSubmitGuardAspect aspect = new RepeatSubmitGuardAspect(redis, new GuardKeyGenerator(), new NovaGuardProperties());
        BusinessException ex = assertThrows(BusinessException.class, () -> aspect.around(point(), annotation()));
        assertEquals(GuardErrorCode.GUARD_REPEAT_SUBMIT.getCode(), ex.getCode());
    }

    @Test
    void firstSubmitProceeds() throws Throwable {
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(any(String.class), eq("1"), eq(3L), eq(TimeUnit.SECONDS))).thenReturn(true);
        ProceedingJoinPoint point = point();
        when(point.proceed()).thenReturn("ok");

        Object result = new RepeatSubmitGuardAspect(redis, new GuardKeyGenerator(), new NovaGuardProperties()).around(point, annotation());
        assertEquals("ok", result);
        verify(point).proceed();
    }

    private ProceedingJoinPoint point() {
        ProceedingJoinPoint point = mock(ProceedingJoinPoint.class);
        Signature sign = mock(Signature.class);
        when(sign.toShortString()).thenReturn("Demo.save(..)");
        when(point.getSignature()).thenReturn(sign);
        when(point.getArgs()).thenReturn(new Object[]{1L});
        return point;
    }

    private RepeatSubmitGuard annotation() {
        RepeatSubmitGuard guard = mock(RepeatSubmitGuard.class);
        when(guard.intervalSeconds()).thenReturn(3L);
        when(guard.message()).thenReturn("请勿重复提交");
        when(guard.key()).thenReturn("");
        return guard;
    }
}
