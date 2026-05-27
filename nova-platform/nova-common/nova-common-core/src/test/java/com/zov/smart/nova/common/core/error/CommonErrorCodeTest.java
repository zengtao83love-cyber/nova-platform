package com.zov.smart.nova.common.core.error;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonErrorCodeTest {

    @Test
    void commonErrorCodeHttpStatusMappingMatchesSpec() {
        assertEquals(400, CommonErrorCode.COMMON_PARAM_INVALID.getHttpStatus());
        assertEquals(400, CommonErrorCode.COMMON_PARAM_MISSING.getHttpStatus());
        assertEquals(404, CommonErrorCode.COMMON_NOT_FOUND.getHttpStatus());
        assertEquals(409, CommonErrorCode.COMMON_CONCURRENT_CONFLICT.getHttpStatus());
        assertEquals(200, CommonErrorCode.COMMON_ENUM_NOT_FOUND.getHttpStatus());
        assertEquals(500, CommonErrorCode.COMMON_SYSTEM_ERROR.getHttpStatus());
    }

    @Test
    void authErrorCodeHttpStatusMappingProvides401And403ForInfraWebTests() {
        assertEquals(401, AuthErrorCode.AUTH_UNAUTHORIZED.getHttpStatus());
        assertEquals(401, AuthErrorCode.AUTH_TOKEN_EXPIRED.getHttpStatus());
        assertEquals(401, AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED.getHttpStatus());
        assertEquals(403, AuthErrorCode.AUTH_FORBIDDEN.getHttpStatus());
        assertEquals(200, AuthErrorCode.AUTH_ACCOUNT_LOCKED.getHttpStatus());
    }

    @Test
    void allErrorCodesAreStableAndUniqueWithinEachEnum() {
        assertUnique(CommonErrorCode.values());
        assertUnique(AuthErrorCode.values());
    }

    private static void assertUnique(ErrorCode[] values) {
        Set<String> codes = new HashSet<String>();
        for (ErrorCode value : values) {
            assertTrue(codes.add(value.getCode()), "duplicated code: " + value.getCode());
        }
    }
}
