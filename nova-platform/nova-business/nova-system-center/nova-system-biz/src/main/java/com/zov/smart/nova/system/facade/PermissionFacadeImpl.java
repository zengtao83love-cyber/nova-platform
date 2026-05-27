package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.system.api.facade.PermissionFacade;
import com.zov.smart.nova.system.biz.MenuBiz;
import org.springframework.stereotype.Service;

import java.util.Set;

/** Permission facade implementation used by RBAC runtime consumers. */
@Service
public class PermissionFacadeImpl implements PermissionFacade {

    private final MenuBiz menuBiz;

    public PermissionFacadeImpl(MenuBiz menuBiz) {
        this.menuBiz = menuBiz;
    }

    @Override public Set<String> listPermissionCodesByUserId(Long userId) { return menuBiz.listPermissionCodesByUserId(userId); }
    @Override public boolean hasPermission(Long userId, String permissionCode) { return menuBiz.hasPermission(userId, permissionCode); }
}
