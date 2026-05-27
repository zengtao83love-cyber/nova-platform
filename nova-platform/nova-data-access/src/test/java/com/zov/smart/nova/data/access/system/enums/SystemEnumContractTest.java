package com.zov.smart.nova.data.access.system.enums;

import com.zov.smart.nova.common.mybatis.enums.DbEnum;
import com.zov.smart.nova.data.access.system.config.enums.ConfigTypeEnum;
import com.zov.smart.nova.data.access.system.dict.enums.DictStatusEnum;
import com.zov.smart.nova.data.access.system.log.enums.LoginFailureReasonEnum;
import com.zov.smart.nova.data.access.system.log.enums.LoginResultEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemEnumContractTest {
    @Test
    void shouldExposeExactlySpecifiedCodes() {
        assertCodes(UserStatusEnum.class, "ENABLED", "DISABLED");
        assertCodes(RoleStatusEnum.class, "ENABLED", "DISABLED");
        assertCodes(MenuTypeEnum.class, "DIR", "MENU", "BUTTON");
        assertCodes(MenuStatusEnum.class, "ENABLED", "DISABLED");
        assertCodes(DictStatusEnum.class, "ENABLED", "DISABLED");
        assertCodes(ConfigTypeEnum.class, "STRING", "NUMBER", "BOOLEAN", "JSON");
        assertCodes(LoginResultEnum.class, "SUCCESS", "FAIL");
        assertCodes(LoginFailureReasonEnum.class,
                "AUTH_USER_NOT_FOUND",
                "AUTH_PASSWORD_ERROR",
                "AUTH_USER_DISABLED",
                "AUTH_ACCOUNT_LOCKED");
    }

    @Test
    void shouldLookupEnumByCode() {
        assertEquals(UserStatusEnum.ENABLED, UserStatusEnum.fromCode("ENABLED"));
        assertEquals(RoleStatusEnum.DISABLED, RoleStatusEnum.fromCode("DISABLED"));
        assertEquals(MenuTypeEnum.BUTTON, MenuTypeEnum.fromCode("BUTTON"));
        assertEquals(MenuStatusEnum.ENABLED, MenuStatusEnum.fromCode("ENABLED"));
        assertEquals(ConfigTypeEnum.JSON, ConfigTypeEnum.fromCode("JSON"));
        assertEquals(LoginFailureReasonEnum.AUTH_ACCOUNT_LOCKED,
                LoginFailureReasonEnum.fromCode("AUTH_ACCOUNT_LOCKED"));
    }

    @SafeVarargs
    private static <E extends Enum<E> & DbEnum> void assertCodes(Class<E> enumClass, String... expectedCodes) {
        E[] constants = enumClass.getEnumConstants();
        assertEquals(expectedCodes.length, constants.length, enumClass.getSimpleName() + " enum item size");
        Set<String> actual = new HashSet<>();
        for (E constant : constants) {
            assertNotNull(constant.getCode());
            assertNotNull(constant.getLabel());
            assertTrue(actual.add(constant.getCode()), "duplicate enum code: " + constant.getCode());
        }
        for (String expectedCode : expectedCodes) {
            assertTrue(actual.contains(expectedCode), "missing enum code: " + expectedCode);
        }
    }
}
