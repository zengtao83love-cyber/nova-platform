package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zov.smart.nova.data.access.system.entity.SysUserRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 用户-角色关系 Mapper。
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleDO> {

    int deleteByUserId(@Param("userId") Long userId);

    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    int batchInsert(@Param("userId") Long userId, @Param("roleIds") Collection<Long> roleIds);
}
