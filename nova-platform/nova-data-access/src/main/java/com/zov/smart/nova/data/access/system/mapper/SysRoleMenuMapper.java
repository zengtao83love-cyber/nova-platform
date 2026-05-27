package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zov.smart.nova.data.access.system.entity.SysRoleMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 角色-菜单关系 Mapper。
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuDO> {

    int deleteByRoleId(@Param("roleId") Long roleId);

    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    int batchInsert(@Param("roleId") Long roleId, @Param("menuIds") Collection<Long> menuIds);
}
