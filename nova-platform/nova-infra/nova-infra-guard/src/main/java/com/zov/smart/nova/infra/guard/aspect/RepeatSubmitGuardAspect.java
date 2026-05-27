package com.zov.smart.nova.infra.guard.aspect;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
import com.zov.smart.nova.infra.guard.error.GuardErrorCode;
import com.zov.smart.nova.infra.guard.key.GuardKeyGenerator;
import com.zov.smart.nova.infra.guard.properties.NovaGuardProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis-backed repeat-submit protection aspect.
 */
@Aspect
public class RepeatSubmitGuardAspect {

    private final StringRedisTemplate stringRedisTemplate;
    private final GuardKeyGenerator keyGenerator;
    private final NovaGuardProperties properties;

    public RepeatSubmitGuardAspect(StringRedisTemplate stringRedisTemplate,
                                   GuardKeyGenerator keyGenerator,
                                   NovaGuardProperties properties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.keyGenerator = keyGenerator;
        this.properties = properties;
    }

    @Around("@annotation(repeatSubmitGuard)")
    public Object around(ProceedingJoinPoint point, RepeatSubmitGuard repeatSubmitGuard) throws Throwable {
        if (!properties.isRepeatSubmitEnabled()) {
            return point.proceed();
        }
        long intervalSeconds = Math.max(1L, repeatSubmitGuard.intervalSeconds());
        String key = keyGenerator.repeatSubmitKey(properties.getRepeatSubmitPrefix(), point, repeatSubmitGuard.key());
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", intervalSeconds, TimeUnit.SECONDS);
        if (!Boolean.TRUE.equals(locked)) {
            throw new BusinessException(GuardErrorCode.GUARD_REPEAT_SUBMIT, repeatSubmitGuard.message());
        }
        return point.proceed();
    }
}
