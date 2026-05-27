package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户 Mapper。所有基础 CRUD 来自 MyBatis-Plus BaseMapper。
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    SysUserDO selectByUsername(@Param("username") String username);

    IPage<SysUserDO> selectUserPage(IPage<SysUserDO> page,
                                    @Param("username") String username,
                                    @Param("realName") String realName,
                                    @Param("mobile") String mobile,
                                    @Param("status") String status);
}
