package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.*;

class MenuControllerContractTest {
    @Test void menuControllerPathAndPermissionMatchSpec() throws Exception {
        RequestMapping mapping = MenuController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/api/system/menus"}, mapping.value());
        RequirePermission p = MenuController.class.getMethod("listTree", MenuTreeQuery.class).getAnnotation(RequirePermission.class);
        assertNotNull(p);
        assertEquals(SystemPermissionConstants.MENU_LIST, p.value()[0]);
    }
}
