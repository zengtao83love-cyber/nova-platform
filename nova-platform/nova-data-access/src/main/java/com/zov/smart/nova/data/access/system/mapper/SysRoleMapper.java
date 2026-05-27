package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper。
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    List<SysRoleDO> selectRolesByUserId(@Param("userId") Long userId);

    List<SysRoleDO> selectEnabledRolesByUserId(@Param("userId") Long userId);

    IPage<SysRoleDO> selectRolePage(IPage<SysRoleDO> page,
                                    @Param("roleCode") String roleCode,
                                    @Param("roleName") String roleName,
                                    @Param("status") String status);
}
