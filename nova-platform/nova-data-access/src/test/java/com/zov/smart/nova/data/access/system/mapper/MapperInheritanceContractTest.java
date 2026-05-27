package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zov.smart.nova.data.access.system.entity.SysConfigDO;
import com.zov.smart.nova.data.access.system.entity.SysDictItemDO;
import com.zov.smart.nova.data.access.system.entity.SysDictTypeDO;
import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import com.zov.smart.nova.data.access.system.entity.SysRoleMenuDO;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.entity.SysUserRoleDO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mapper 继承边界保护测试：所有 Mapper 必须直接继承 MyBatis-Plus BaseMapper。
 */
class MapperInheritanceContractTest {

    @Test
    void allSystemMappersShouldDirectlyExtendMybatisPlusBaseMapper() {
        Map<Class<?>, Class<?>> mapperEntityPairs = new LinkedHashMap<>();
        mapperEntityPairs.put(SysUserMapper.class, SysUserDO.class);
        mapperEntityPairs.put(SysRoleMapper.class, SysRoleDO.class);
        mapperEntityPairs.put(SysMenuMapper.class, SysMenuDO.class);
        mapperEntityPairs.put(SysUserRoleMapper.class, SysUserRoleDO.class);
        mapperEntityPairs.put(SysRoleMenuMapper.class, SysRoleMenuDO.class);
        mapperEntityPairs.put(SysDictTypeMapper.class, SysDictTypeDO.class);
        mapperEntityPairs.put(SysDictItemMapper.class, SysDictItemDO.class);
        mapperEntityPairs.put(SysConfigMapper.class, SysConfigDO.class);
        mapperEntityPairs.put(SysLoginLogMapper.class, SysLoginLogDO.class);

        for (Map.Entry<Class<?>, Class<?>> entry : mapperEntityPairs.entrySet()) {
            assertDirectBaseMapper(entry.getKey(), entry.getValue());
            assertTrue(Arrays.stream(entry.getKey().getGenericInterfaces())
                    .map(Type::getTypeName)
                    .noneMatch(name -> name.contains("NovaBaseMapper")));
        }
    }

    private static void assertDirectBaseMapper(Class<?> mapperType, Class<?> entityType) {
        Type[] interfaces = mapperType.getGenericInterfaces();
        assertEquals(1, interfaces.length, mapperType.getName() + " must only directly extend BaseMapper<T>");
        assertTrue(interfaces[0] instanceof ParameterizedType);
        ParameterizedType parameterizedType = (ParameterizedType) interfaces[0];
        assertEquals(BaseMapper.class, parameterizedType.getRawType());
        assertEquals(entityType, parameterizedType.getActualTypeArguments()[0]);
    }
}
