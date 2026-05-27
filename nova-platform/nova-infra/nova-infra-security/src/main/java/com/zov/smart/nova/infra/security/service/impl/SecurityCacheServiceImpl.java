package com.zov.smart.nova.infra.security.service.impl;

import com.zov.smart.nova.infra.security.service.SecurityCacheService;
import com.zov.smart.nova.infra.security.service.TokenService;
import com.zov.smart.nova.infra.security.support.SecurityRedisKeys;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

public class SecurityCacheServiceImpl implements SecurityCacheService {
    private final StringRedisTemplate redisTemplate;
    private final TokenService tokenService;

    public SecurityCacheServiceImpl(StringRedisTemplate redisTemplate, TokenService tokenService) {
        this.redisTemplate = redisTemplate;
        this.tokenService = tokenService;
    }

    @Override public void clearUserPermissions(Long userId) { if (userId != null) { redisTemplate.delete(SecurityRedisKeys.permissions(userId)); } }
    @Override public void clearUserMenus(Long userId) { if (userId != null) { redisTemplate.delete(SecurityRedisKeys.menus(userId)); } }
    @Override public void clearUserAccessTokens(Long userId) { tokenService.revokeAllUserTokens(userId); }
    @Override public void clearRoleRelatedPermissions(Long roleId, List<Long> affectedUserIds) {
        if (affectedUserIds != null) { for (Long userId : affectedUserIds) { clearUserPermissions(userId); clearUserMenus(userId); } }
    }
    @Override public void clearAllPermissionsCache() {
        Set<String> keys = redisTemplate.keys(SecurityRedisKeys.PERMISSIONS + "*");
        if (keys != null && !keys.isEmpty()) { redisTemplate.delete(keys); }
    }
}
