package com.zov.smart.nova.system.api.facade;

import java.util.Set;

/** Permission-code query contract for RBAC runtime checks. */
public interface PermissionFacade {
    Set<String> listPermissionCodesByUserId(Long userId);
    boolean hasPermission(Long userId, String permissionCode);
}
