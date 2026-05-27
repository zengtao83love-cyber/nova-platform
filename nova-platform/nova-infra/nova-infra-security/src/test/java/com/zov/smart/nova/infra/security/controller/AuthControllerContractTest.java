package com.zov.smart.nova.infra.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerContractTest {
    @Test void authControllerLivesInSecurityAndExposesExpectedEndpoints() {
        assertEquals("com.zov.smart.nova.infra.security.controller", AuthController.class.getPackage().getName());
        assertEquals("/api/auth", AuthController.class.getAnnotation(RequestMapping.class).value()[0]);
        Set<String> methods = new HashSet<String>();
        for (Method method : AuthController.class.getDeclaredMethods()) { methods.add(method.getName()); }
        assertTrue(methods.contains("login")); assertTrue(methods.contains("logout")); assertTrue(methods.contains("refresh"));
        assertTrue(methods.contains("me")); assertTrue(methods.contains("updateProfile")); assertTrue(methods.contains("changePassword"));
        assertTrue(methods.contains("menus")); assertTrue(methods.contains("permissions"));
    }
}
