package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 登录日志 Mapper。
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLogDO> {

    IPage<SysLoginLogDO> selectLoginLogPage(IPage<SysLoginLogDO> page,
                                            @Param("username") String username,
                                            @Param("loginResult") String loginResult,
                                            @Param("beginTime") LocalDateTime beginTime,
                                            @Param("endTime") LocalDateTime endTime);
}
