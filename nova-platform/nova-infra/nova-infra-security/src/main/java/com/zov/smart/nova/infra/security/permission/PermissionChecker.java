package com.zov.smart.nova.infra.security.permission;

import com.zov.smart.nova.common.context.LoginUserContext;
import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.infra.security.properties.NovaSecurityProperties;
import com.zov.smart.nova.infra.security.service.SecurityUserService;
import com.zov.smart.nova.infra.security.support.SecurityRedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** Permission checker used by @RequirePermission AOP. */
public class PermissionChecker {
    private final StringRedisTemplate redisTemplate;
    private final SecurityUserService securityUserService;
    private final NovaSecurityProperties properties;

    public PermissionChecker(StringRedisTemplate redisTemplate, SecurityUserService securityUserService, NovaSecurityProperties properties) {
        this.redisTemplate = redisTemplate;
        this.securityUserService = securityUserService;
        this.properties = properties;
    }

    public void check(String[] required, LogicalOperator logical) {
        LoginUserContext.CurrentUser currentUser = LoginUserContext.get();
        if (currentUser == null) { throw new BusinessException(AuthErrorCode.AUTH_UNAUTHORIZED); }
        if (Boolean.TRUE.equals(currentUser.getSuperAdminFlag())) { return; }
        Set<String> requiredPermissions = normalize(required);
        if (requiredPermissions.isEmpty()) { return; }
        Set<String> owned = loadPermissions(currentUser.getUserId());
        boolean allowed = logical == LogicalOperator.OR ? matchesOr(requiredPermissions, owned) : owned.containsAll(requiredPermissions);
        if (!allowed) { throw new BusinessException(AuthErrorCode.AUTH_FORBIDDEN); }
    }

    private Set<String> loadPermissions(Long userId) {
        if (userId == null) { return Collections.emptySet(); }
        String key = SecurityRedisKeys.permissions(userId);
        Set<String> cached = redisTemplate.opsForSet().members(key);
        if (cached != null && !cached.isEmpty()) { return cached; }
        Set<String> permissions = securityUserService.loadPermissionCodes(userId);
        if (!permissions.isEmpty()) {
            redisTemplate.opsForSet().add(key, permissions.toArray(new String[0]));
            redisTemplate.expire(key, properties.getPermissionCacheTtlSeconds(), TimeUnit.SECONDS);
        }
        return permissions;
    }

    private Set<String> normalize(String[] values) {
        if (values == null || values.length == 0) { return Collections.emptySet(); }
        Set<String> result = new LinkedHashSet<String>();
        for (String value : Arrays.asList(values)) {
            if (StringUtils.hasText(value)) { result.add(value.trim()); }
        }
        return result;
    }

    private boolean matchesOr(Set<String> required, Set<String> owned) {
        for (String permission : required) {
            if (owned.contains(permission)) { return true; }
        }
        return false;
    }
}
