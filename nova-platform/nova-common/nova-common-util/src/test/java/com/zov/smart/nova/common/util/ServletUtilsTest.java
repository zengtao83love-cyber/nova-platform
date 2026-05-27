package com.zov.smart.nova.common.util;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ServletUtilsTest {
    @Test
    void shouldReadHeaderAndRequestMetadata() {
        HttpServletRequest request = requestProxy();

        assertEquals("trace-001", ServletUtils.getHeader(request, "X-Request-Id"));
        assertEquals("/api/auth/me", ServletUtils.getRequestUri(request));
        assertEquals("GET", ServletUtils.getMethod(request));
        assertEquals("127.0.0.1", ServletUtils.getClientIp(request));
        assertNull(ServletUtils.getHeader(request, "Missing"));
    }

    private static HttpServletRequest requestProxy() {
        return (HttpServletRequest) Proxy.newProxyInstance(
                ServletUtilsTest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if ("getHeader".equals(method.getName())) {
                            if ("X-Request-Id".equals(args[0])) {
                                return " trace-001 ";
                            }
                            return null;
                        }
                        if ("getRequestURI".equals(method.getName())) {
                            return "/api/auth/me";
                        }
                        if ("getMethod".equals(method.getName())) {
                            return "GET";
                        }
                        if ("getRemoteAddr".equals(method.getName())) {
                            return "0:0:0:0:0:0:0:1";
                        }
                        return null;
                    }
                });
    }
}
