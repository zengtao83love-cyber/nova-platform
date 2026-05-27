package com.zov.smart.nova.infra.audit.support;

import com.zov.smart.nova.common.util.JsonUtils;
import com.zov.smart.nova.common.util.MaskUtils;

import java.util.regex.Pattern;

/**
 * Masks sensitive data before audit payloads are persisted.
 */
public class AuditPayloadMasker {

    private static final int DEFAULT_MAX_LENGTH = 4000;
    private static final Pattern PASSWORD_FIELD = Pattern.compile("(?i)(\\\"?(password|oldPassword|newPassword|confirmPassword)\\\"?\\s*[:=]\\s*)\\\"?[^,}\\s\\\"]+\\\"?");
    private static final Pattern TOKEN_FIELD = Pattern.compile("(?i)(\\\"?(accessToken|refreshToken|authorization|token)\\\"?\\s*[:=]\\s*)\\\"?[^,}\\s\\\"]+\\\"?");

    public String maskObject(Object value) {
        if (value == null) {
            return null;
        }
        return maskText(JsonUtils.toJson(value));
    }

    public String maskText(String text) {
        if (isBlank(text)) {
            return text;
        }
        String masked = PASSWORD_FIELD.matcher(text).replaceAll("$1\"******\"");
        masked = TOKEN_FIELD.matcher(masked).replaceAll("$1\"" + MaskUtils.maskToken("placeholder-token-value") + "\"");
        masked = masked.replaceAll("(?i)Bearer\\s+[A-Za-z0-9._~+/=-]+", "Bearer ******");
        return truncate(masked, DEFAULT_MAX_LENGTH);
    }

    public String truncate(String text, int maxLength) {
        if (text == null || maxLength <= 0 || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
