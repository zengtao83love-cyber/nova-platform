package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 字典、参数、登录日志 DO 与数据库字段矩阵的结构保护测试。
 */
class DictConfigLoginLogEntityMappingTest {

    @Test
    void dictTypeShouldMapToSysDictTypeAndUseEnumCodeTypeHandler() throws Exception {
        assertEquals("sys_dict_type", SysDictTypeDO.class.getAnnotation(TableName.class).value());
        assertTrue(BaseDO.class.isAssignableFrom(SysDictTypeDO.class));
        assertColumn(SysDictTypeDO.class, "dictCode", "dict_code");
        assertColumn(SysDictTypeDO.class, "dictName", "dict_name");
        assertEquals(EnumCodeTypeHandler.class, SysDictTypeDO.class.getDeclaredField("status").getAnnotation(TableField.class).typeHandler());
    }

    @Test
    void dictItemShouldMapToSysDictItemAndUseEnumCodeTypeHandler() throws Exception {
        assertEquals("sys_dict_item", SysDictItemDO.class.getAnnotation(TableName.class).value());
        assertTrue(BaseDO.class.isAssignableFrom(SysDictItemDO.class));
        assertColumn(SysDictItemDO.class, "dictCode", "dict_code");
        assertColumn(SysDictItemDO.class, "itemLabel", "item_label");
        assertColumn(SysDictItemDO.class, "itemValue", "item_value");
        assertColumn(SysDictItemDO.class, "sortOrder", "sort_order");
        assertEquals(EnumCodeTypeHandler.class, SysDictItemDO.class.getDeclaredField("status").getAnnotation(TableField.class).typeHandler());
    }

    @Test
    void configShouldMapToSysConfigAndUseEnumCodeTypeHandler() throws Exception {
        assertEquals("sys_config", SysConfigDO.class.getAnnotation(TableName.class).value());
        assertTrue(BaseDO.class.isAssignableFrom(SysConfigDO.class));
        assertColumn(SysConfigDO.class, "configKey", "config_key");
        assertColumn(SysConfigDO.class, "configValue", "config_value");
        assertColumn(SysConfigDO.class, "configName", "config_name");
        assertEquals(EnumCodeTypeHandler.class, SysConfigDO.class.getDeclaredField("configType").getAnnotation(TableField.class).typeHandler());
    }

    @Test
    void loginLogShouldMapOnlyExistingLogColumns() throws Exception {
        assertEquals("sys_login_log", SysLoginLogDO.class.getAnnotation(TableName.class).value());
        assertFalse(BaseDO.class.isAssignableFrom(SysLoginLogDO.class));
        assertColumn(SysLoginLogDO.class, "userId", "user_id");
        assertColumn(SysLoginLogDO.class, "username", "username");
        assertColumn(SysLoginLogDO.class, "loginIp", "login_ip");
        assertColumn(SysLoginLogDO.class, "userAgent", "user_agent");
        assertColumn(SysLoginLogDO.class, "loginAt", "login_at");
        assertColumn(SysLoginLogDO.class, "createdAt", "created_at");
        assertEquals(EnumCodeTypeHandler.class, SysLoginLogDO.class.getDeclaredField("loginResult").getAnnotation(TableField.class).typeHandler());
        assertEquals(EnumCodeTypeHandler.class, SysLoginLogDO.class.getDeclaredField("failureReason").getAnnotation(TableField.class).typeHandler());
    }

    private static void assertColumn(Class<?> type, String fieldName, String column) throws Exception {
        Field field = type.getDeclaredField(fieldName);
        assertEquals(column, field.getAnnotation(TableField.class).value());
    }
}
