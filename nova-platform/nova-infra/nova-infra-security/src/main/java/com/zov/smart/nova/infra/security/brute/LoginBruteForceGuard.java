package com.zov.smart.nova.infra.security.brute;

import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.security.properties.NovaSecurityProperties;
import com.zov.smart.nova.infra.security.support.SecurityRedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/** Pure Redis login brute-force guard. */
public class LoginBruteForceGuard {
    private final StringRedisTemplate redisTemplate;
    private final NovaSecurityProperties properties;

    public LoginBruteForceGuard(StringRedisTemplate redisTemplate, NovaSecurityProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    public void checkNotLocked(String username) {
        String key = SecurityRedisKeys.loginFail(username);
        String value = redisTemplate.opsForValue().get(key);
        int failCount = value == null ? 0 : Integer.parseInt(value);
        if (failCount >= properties.getLogin().getMaxFailCount()) {
            throw new BusinessException(AuthErrorCode.AUTH_ACCOUNT_LOCKED,
                    "账号已被临时锁定，请于 " + Math.max(1L, getLockRemainingSeconds(username) / 60L) + " 分钟后重试");
        }
    }

    public void onLoginFail(String username) {
        String key = SecurityRedisKeys.loginFail(username);
        Long count = redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, properties.getLogin().getLockWindowSeconds(), TimeUnit.SECONDS);
        if (count != null && count >= properties.getLogin().getMaxFailCount()) {
            // keep locked by count + TTL; no separate lock key is needed.
        }
    }

    public void onLoginSuccess(String username) {
        redisTemplate.delete(SecurityRedisKeys.loginFail(username));
    }

    public void unlock(String username) {
        redisTemplate.delete(SecurityRedisKeys.loginFail(username));
    }

    public long getLockRemainingSeconds(String username) {
        Long ttl = redisTemplate.getExpire(SecurityRedisKeys.loginFail(username), TimeUnit.SECONDS);
        return ttl == null ? 0L : Math.max(0L, ttl);
    }
}
