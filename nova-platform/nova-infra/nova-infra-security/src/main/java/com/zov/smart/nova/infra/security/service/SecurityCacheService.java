package com.zov.smart.nova.infra.security.service;

import java.util.List;

public interface SecurityCacheService {
    void clearUserPermissions(Long userId);
    void clearUserMenus(Long userId);
    void clearUserAccessTokens(Long userId);
    void clearRoleRelatedPermissions(Long roleId, List<Long> affectedUserIds);
    void clearAllPermissionsCache();
}
