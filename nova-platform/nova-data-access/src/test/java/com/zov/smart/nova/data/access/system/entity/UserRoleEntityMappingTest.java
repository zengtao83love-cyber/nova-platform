package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRoleEntityMappingTest {
    @Test
    void sysUserShouldMatchTableAndStatusEnumMapping() throws Exception {
        assertTrue(BaseDO.class.isAssignableFrom(SysUserDO.class));
        assertEquals("sys_user", SysUserDO.class.getAnnotation(TableName.class).value());
        assertTrue(SysUserDO.class.getAnnotation(TableName.class).autoResultMap());

        assertTableField(SysUserDO.class, "username", "username");
        assertTableField(SysUserDO.class, "password", "password");
        assertTableField(SysUserDO.class, "realName", "real_name");
        assertTableField(SysUserDO.class, "superAdminFlag", "super_admin_flag");
        assertTableField(SysUserDO.class, "loginLockFlag", "login_lock_flag");
        assertTableField(SysUserDO.class, "lastLoginAt", "last_login_at");
        assertEnumField(SysUserDO.class, "status", UserStatusEnum.class);
    }

    @Test
    void sysRoleShouldMatchTableAndStatusEnumMapping() throws Exception {
        assertTrue(BaseDO.class.isAssignableFrom(SysRoleDO.class));
        assertEquals("sys_role", SysRoleDO.class.getAnnotation(TableName.class).value());
        assertTrue(SysRoleDO.class.getAnnotation(TableName.class).autoResultMap());

        assertTableField(SysRoleDO.class, "roleCode", "role_code");
        assertTableField(SysRoleDO.class, "roleName", "role_name");
        assertTableField(SysRoleDO.class, "sortOrder", "sort_order");
        assertTableField(SysRoleDO.class, "remark", "remark");
        assertEnumField(SysRoleDO.class, "status", RoleStatusEnum.class);
    }

    private static void assertTableField(Class<?> entityClass, String fieldName, String columnName) throws Exception {
        Field field = entityClass.getDeclaredField(fieldName);
        TableField annotation = field.getAnnotation(TableField.class);
        assertNotNull(annotation, fieldName + " must declare @TableField");
        assertEquals(columnName, annotation.value());
    }

    private static void assertEnumField(Class<?> entityClass, String fieldName, Class<?> enumClass) throws Exception {
        Field field = entityClass.getDeclaredField(fieldName);
        assertSame(enumClass, field.getType());
        TableField annotation = field.getAnnotation(TableField.class);
        assertNotNull(annotation, fieldName + " must declare @TableField");
        assertSame(EnumCodeTypeHandler.class, annotation.typeHandler());
    }
}
