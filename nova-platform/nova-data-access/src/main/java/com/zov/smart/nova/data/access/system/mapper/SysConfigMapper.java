package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.data.access.system.entity.SysConfigDO;
import org.apache.ibatis.annotations.Param;

/**
 * 系统参数 Mapper。
 */
public interface SysConfigMapper extends BaseMapper<SysConfigDO> {

    SysConfigDO selectByConfigKey(@Param("configKey") String configKey);

    IPage<SysConfigDO> selectConfigPage(IPage<SysConfigDO> page,
                                        @Param("configKey") String configKey,
                                        @Param("configName") String configName,
                                        @Param("configType") String configType);
}
