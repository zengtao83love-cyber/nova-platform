package com.zov.smart.nova.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet 请求读取工具，只依赖 javax.servlet，兼容 Spring Boot 2.7.x / JDK 8。
 */
public final class ServletUtils {
    private ServletUtils() {
    }

    public static String getHeader(HttpServletRequest request, String headerName) {
        if (request == null || isBlank(headerName)) {
            return null;
        }
        String value = request.getHeader(headerName);
        return isBlank(value) ? null : value.trim();
    }

    public static String getRequestUri(HttpServletRequest request) {
        return request == null ? null : request.getRequestURI();
    }

    public static String getMethod(HttpServletRequest request) {
        return request == null ? null : request.getMethod();
    }

    public static String getClientIp(HttpServletRequest request) {
        return IpUtils.getIpAddr(request);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
