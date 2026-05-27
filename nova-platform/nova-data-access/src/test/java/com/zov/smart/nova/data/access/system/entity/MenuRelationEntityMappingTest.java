package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuRelationEntityMappingTest {
    @Test
    void sysMenuShouldMatchTableAndEnumMappings() throws Exception {
        assertTrue(BaseDO.class.isAssignableFrom(SysMenuDO.class));
        assertEquals("sys_menu", SysMenuDO.class.getAnnotation(TableName.class).value());
        assertTrue(SysMenuDO.class.getAnnotation(TableName.class).autoResultMap());

        assertTableField(SysMenuDO.class, "parentId", "parent_id");
        assertTableField(SysMenuDO.class, "menuName", "menu_name");
        assertTableField(SysMenuDO.class, "routePath", "route_path");
        assertTableField(SysMenuDO.class, "componentPath", "component_path");
        assertTableField(SysMenuDO.class, "permissionCode", "permission_code");
        assertTableField(SysMenuDO.class, "visibleFlag", "visible_flag");
        assertEnumField(SysMenuDO.class, "menuType", MenuTypeEnum.class);
        assertEnumField(SysMenuDO.class, "status", MenuStatusEnum.class);
    }

    @Test
    void buttonMenuShouldRequirePermissionCode() {
        SysMenuDO button = new SysMenuDO();
        button.setMenuType(MenuTypeEnum.BUTTON);
        button.setPermissionCode(null);
        assertFalse(button.isButtonPermissionCodeValid());

        button.setPermissionCode("system:user:create");
        assertTrue(button.isButtonPermissionCodeValid());

        SysMenuDO directory = new SysMenuDO();
        directory.setMenuType(MenuTypeEnum.DIR);
        assertTrue(directory.isButtonPermissionCodeValid());
    }

    @Test
    void relationEntitiesShouldNotInheritBaseDOBecauseTablesDoNotContainFullAuditFields() throws Exception {
        assertFalse(BaseDO.class.isAssignableFrom(SysUserRoleDO.class));
        assertFalse(BaseDO.class.isAssignableFrom(SysRoleMenuDO.class));
        assertEquals("sys_user_role", SysUserRoleDO.class.getAnnotation(TableName.class).value());
        assertEquals("sys_role_menu", SysRoleMenuDO.class.getAnnotation(TableName.class).value());
        assertTableField(SysUserRoleDO.class, "userId", "user_id");
        assertTableField(SysUserRoleDO.class, "roleId", "role_id");
        assertTableField(SysUserRoleDO.class, "createdAt", "created_at");
        assertTableField(SysRoleMenuDO.class, "roleId", "role_id");
        assertTableField(SysRoleMenuDO.class, "menuId", "menu_id");
        assertTableField(SysRoleMenuDO.class, "createdAt", "created_at");
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
