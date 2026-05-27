package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerContractTest {
    @Test
    void userControllerPathAndPermissionMatchSpec() throws Exception {
        RequestMapping mapping = UserController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/api/system/users"}, mapping.value());
        RequirePermission permission = UserController.class.getMethod("pageUsers", com.zov.smart.nova.system.api.query.user.UserPageQuery.class)
                .getAnnotation(RequirePermission.class);
        assertNotNull(permission);
        assertEquals(SystemPermissionConstants.USER_LIST, permission.value()[0]);
    }
}
