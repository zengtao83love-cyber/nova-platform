package com.zov.smart.nova.system.service;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysConfigDO;
import com.zov.smart.nova.system.api.query.config.ConfigPageQuery;

/** Atomic system config persistence service. */
public interface ConfigService {
    SysConfigDO getByIdRequired(Long id);
    SysConfigDO getByKeyRequired(String configKey);
    SysConfigDO findByKey(String configKey);
    PageResult<SysConfigDO> pageConfigs(ConfigPageQuery query);
    Long createConfig(SysConfigDO config);
    void updateConfig(SysConfigDO config);
    void deleteConfig(Long id);
}
