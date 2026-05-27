package com.zov.smart.nova.common.util;

/**
 * 敏感数据脱敏工具。
 */
public final class MaskUtils {
    private MaskUtils() {
    }

    public static String maskMobile(String mobile) {
        if (isBlank(mobile) || mobile.length() < 7) {
            return mobile;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
    }

    public static String maskEmail(String email) {
        if (isBlank(email)) {
            return email;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "****" + (atIndex >= 0 ? email.substring(atIndex) : "");
        }
        return email.substring(0, 1) + "****" + email.substring(atIndex);
    }

    public static String maskName(String name) {
        if (isBlank(name) || name.length() <= 1) {
            return name;
        }
        if (name.length() == 2) {
            return name.substring(0, 1) + "*";
        }
        return name.substring(0, 1) + repeat('*', name.length() - 2) + name.substring(name.length() - 1);
    }

    public static String maskIdCard(String idCard) {
        if (isBlank(idCard) || idCard.length() < 8) {
            return idCard;
        }
        return idCard.substring(0, 4) + repeat('*', idCard.length() - 8) + idCard.substring(idCard.length() - 4);
    }

    public static String maskToken(String token) {
        if (isBlank(token) || token.length() <= 12) {
            return "******";
        }
        return token.substring(0, 6) + "******" + token.substring(token.length() - 6);
    }

    private static String repeat(char ch, int count) {
        if (count <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            builder.append(ch);
        }
        return builder.toString();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
