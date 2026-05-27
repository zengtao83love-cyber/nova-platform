package com.zov.smart.nova.system.api.contract;

import com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import com.zov.smart.nova.system.api.vo.user.UserDetailVO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserContractTest {
    @Test
    void userCommandDefaultsFollowDtoMatrix() {
        CreateUserCommand command = new CreateUserCommand();
        assertEquals("ENABLED", command.getStatus());
        assertNotNull(command.getRoleIds());
        AssignUserRolesCommand assign = new AssignUserRolesCommand();
        assertNotNull(assign.getRoleIds());
    }

    @Test
    void userQueryExtendsPageParamDefaults() {
        UserPageQuery query = new UserPageQuery();
        assertEquals(1, query.getPageNo());
        assertEquals(10, query.getPageSize());
    }

    @Test
    void userDtoAndVoMustNotExposePasswordOrInternalFlags() {
        assertForbiddenFieldsAbsent(UserDTO.class, "password", "deleteFlag", "version", "loginLockFlag");
        assertForbiddenFieldsAbsent(UserDetailVO.class, "password", "deleteFlag", "version", "loginLockFlag");
    }

    private void assertForbiddenFieldsAbsent(Class<?> type, String... forbiddenFields) {
        Set<String> names = new HashSet<String>();
        for (Field field : type.getDeclaredFields()) {
            names.add(field.getName());
        }
        for (String forbiddenField : forbiddenFields) {
            assertFalse(names.contains(forbiddenField), type.getSimpleName() + " must not expose " + forbiddenField);
        }
    }
}
