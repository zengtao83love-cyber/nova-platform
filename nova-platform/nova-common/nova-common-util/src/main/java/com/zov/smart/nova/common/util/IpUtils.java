package com.zov.smart.nova.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端 IP 解析工具。
 */
public final class IpUtils {
    private static final String UNKNOWN = "unknown";
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };

    private IpUtils() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        for (String header : IP_HEADERS) {
            String value = request.getHeader(header);
            String candidate = firstValidIp(value);
            if (candidate != null) {
                return normalizeLocalhost(candidate);
            }
        }
        return normalizeLocalhost(request.getRemoteAddr());
    }

    public static String firstValidIp(String headerValue) {
        if (isBlank(headerValue)) {
            return null;
        }
        String[] parts = headerValue.split(",");
        for (String part : parts) {
            String candidate = part == null ? null : part.trim();
            if (!isUnknown(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    public static boolean isUnknown(String value) {
        return isBlank(value) || UNKNOWN.equalsIgnoreCase(value.trim());
    }

    private static String normalizeLocalhost(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
