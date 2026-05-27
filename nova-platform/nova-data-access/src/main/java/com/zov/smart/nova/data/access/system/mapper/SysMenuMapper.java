package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单 Mapper。
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {

    List<SysMenuDO> selectMenusByUserId(@Param("userId") Long userId);

    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);

    List<SysMenuDO> selectMenusByRoleId(@Param("roleId") Long roleId);

    int countChildren(@Param("parentId") Long parentId);

    int countByPermissionCode(@Param("permissionCode") String permissionCode,
                              @Param("excludeId") Long excludeId);
}
