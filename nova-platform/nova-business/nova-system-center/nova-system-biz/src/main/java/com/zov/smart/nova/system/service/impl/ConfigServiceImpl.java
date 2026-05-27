package com.zov.smart.nova.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysConfigDO;
import com.zov.smart.nova.data.access.system.mapper.SysConfigMapper;
import com.zov.smart.nova.system.api.query.config.ConfigPageQuery;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.ConfigService;
import org.springframework.stereotype.Service;

/** Atomic system config persistence implementation. */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final SysConfigMapper configMapper;

    public ConfigServiceImpl(SysConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    public SysConfigDO getByIdRequired(Long id) {
        SysConfigDO config = id == null ? null : configMapper.selectById(id);
        if (config == null) {
            throw new BusinessException(SystemErrorCode.CONFIG_NOT_FOUND);
        }
        return config;
    }

    @Override
    public SysConfigDO getByKeyRequired(String configKey) {
        SysConfigDO config = findByKey(configKey);
        if (config == null) {
            throw new BusinessException(SystemErrorCode.CONFIG_NOT_FOUND);
        }
        return config;
    }

    @Override
    public SysConfigDO findByKey(String configKey) {
        if (configKey == null || configKey.trim().isEmpty()) {
            return null;
        }
        return configMapper.selectByConfigKey(configKey.trim());
    }

    @Override
    public PageResult<SysConfigDO> pageConfigs(ConfigPageQuery query) {
        ConfigPageQuery safe = query == null ? new ConfigPageQuery() : query;
        IPage<SysConfigDO> page = new Page<SysConfigDO>(safe.getPageNo(), safe.getPageSize());
        IPage<SysConfigDO> result = configMapper.selectConfigPage(page, safe.getConfigKey(), safe.getConfigName(), safe.getConfigType());
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }

    @Override
    public Long createConfig(SysConfigDO config) {
        configMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateConfig(SysConfigDO config) {
        configMapper.updateById(config);
    }

    @Override
    public void deleteConfig(Long id) {
        configMapper.deleteById(id);
    }
}
