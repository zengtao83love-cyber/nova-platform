package com.zov.smart.nova.infra.guard.aspect;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.guard.annotation.ResourceLockGuard;
import com.zov.smart.nova.infra.guard.error.GuardErrorCode;
import com.zov.smart.nova.infra.guard.key.GuardKeyGenerator;
import com.zov.smart.nova.infra.guard.properties.NovaGuardProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis-backed resource lock aspect.
 */
@Aspect
public class ResourceLockGuardAspect {

    private final StringRedisTemplate stringRedisTemplate;
    private final GuardKeyGenerator keyGenerator;
    private final NovaGuardProperties properties;

    public ResourceLockGuardAspect(StringRedisTemplate stringRedisTemplate,
                                   GuardKeyGenerator keyGenerator,
                                   NovaGuardProperties properties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.keyGenerator = keyGenerator;
        this.properties = properties;
    }

    @Around("@annotation(resourceLockGuard)")
    public Object around(ProceedingJoinPoint point, ResourceLockGuard resourceLockGuard) throws Throwable {
        if (!properties.isResourceLockEnabled()) {
            return point.proceed();
        }
        String key = keyGenerator.resourceLockKey(properties.getResourceLockPrefix(), point, resourceLockGuard.key());
        String lockValue = UUID.randomUUID().toString();
        long lockSeconds = Math.max(1L, resourceLockGuard.lockSeconds());
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key, lockValue, lockSeconds, TimeUnit.SECONDS);
        if (!Boolean.TRUE.equals(locked)) {
            throw new BusinessException(GuardErrorCode.GUARD_LOCK_FAILED, resourceLockGuard.message());
        }
        try {
            return point.proceed();
        } finally {
            releaseOwnedLock(key, lockValue);
        }
    }

    private void releaseOwnedLock(String key, String lockValue) {
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if (lockValue.equals(currentValue)) {
                stringRedisTemplate.delete(key);
            }
        } catch (Exception ignored) {
            // Releasing a guard lock must not affect the already completed business method.
        }
    }
}
