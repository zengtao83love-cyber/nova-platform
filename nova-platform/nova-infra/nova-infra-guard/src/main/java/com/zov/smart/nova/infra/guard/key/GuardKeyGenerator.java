package com.zov.smart.nova.infra.guard.key;

import com.zov.smart.nova.common.context.LoginUserContext;
import org.aspectj.lang.ProceedingJoinPoint;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Builds deterministic Redis keys for guard aspects.
 *
 * <p>Arguments are represented only by a SHA-256 digest. This prevents raw request
 * parameters, passwords, tokens, or uploaded content from being written into Redis
 * keys or logs.</p>
 */
public class GuardKeyGenerator {

    private static final String ANONYMOUS = "anonymous";

    public String repeatSubmitKey(String prefix, ProceedingJoinPoint point, String customKey) {
        return build(prefix, currentUserIdentity(), point, customKey);
    }

    public String resourceLockKey(String prefix, ProceedingJoinPoint point, String customKey) {
        return build(prefix, "resource", point, customKey);
    }

    private String build(String prefix, String owner, ProceedingJoinPoint point, String customKey) {
        String method = point == null || point.getSignature() == null ? "unknown" : point.getSignature().toShortString();
        String businessKey = isBlank(customKey) ? method + ":" + digest(point == null ? null : point.getArgs()) : customKey.trim();
        return prefix + owner + ":" + businessKey;
    }

    private String currentUserIdentity() {
        LoginUserContext.CurrentUser user = LoginUserContext.get();
        return user == null || user.getUserId() == null ? ANONYMOUS : String.valueOf(user.getUserId());
    }

    private String digest(Object[] args) {
        String raw = Arrays.deepToString(args == null ? new Object[0] : args);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder(32);
            for (int i = 0; i < Math.min(16, bytes.length); i++) {
                String hex = Integer.toHexString(bytes[i] & 0xff);
                if (hex.length() == 1) {
                    builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 algorithm is unavailable", ex);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
