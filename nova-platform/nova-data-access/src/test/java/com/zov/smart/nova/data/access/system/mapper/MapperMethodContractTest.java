package com.zov.smart.nova.data.access.system.mapper;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mapper 自定义方法契约保护测试。
 */
class MapperMethodContractTest {

    @Test
    void recommendedCustomMapperMethodsShouldExist() {
        assertMethods(SysUserMapper.class, "selectByUsername", "selectUserPage");
        assertMethods(SysRoleMapper.class, "selectRolesByUserId", "selectEnabledRolesByUserId", "selectRolePage");
        assertMethods(SysMenuMapper.class, "selectMenusByUserId", "selectPermissionCodesByUserId", "selectMenusByRoleId", "countChildren", "countByPermissionCode");
        assertMethods(SysUserRoleMapper.class, "deleteByUserId", "selectRoleIdsByUserId", "batchInsert");
        assertMethods(SysRoleMenuMapper.class, "deleteByRoleId", "selectMenuIdsByRoleId", "batchInsert");
        assertMethods(SysDictTypeMapper.class, "selectByDictCode", "selectDictTypePage");
        assertMethods(SysDictItemMapper.class, "selectItemsByDictCode", "selectDictItemPage");
        assertMethods(SysConfigMapper.class, "selectByConfigKey", "selectConfigPage");
        assertMethods(SysLoginLogMapper.class, "selectLoginLogPage");
    }

    private static void assertMethods(Class<?> mapperType, String... methodNames) {
        Set<String> actual = Arrays.stream(mapperType.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toSet());
        for (String methodName : methodNames) {
            assertTrue(actual.contains(methodName), mapperType.getSimpleName() + " missing method " + methodName);
        }
    }
}
