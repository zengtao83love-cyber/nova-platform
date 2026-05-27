package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleControllerContractTest {
    @Test
    void roleControllerPathAndPermissionMatchSpec() throws Exception {
        RequestMapping mapping = RoleController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/api/system/roles"}, mapping.value());
        RequirePermission permission = RoleController.class.getMethod("pageRoles", com.zov.smart.nova.system.api.query.role.RolePageQuery.class)
                .getAnnotation(RequirePermission.class);
        assertNotNull(permission);
        assertEquals(SystemPermissionConstants.ROLE_LIST, permission.value()[0]);
    }
}
