package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.config.enums.ConfigTypeEnum;
import com.zov.smart.nova.data.access.system.entity.SysConfigDO;
import com.zov.smart.nova.system.api.command.config.CreateConfigCommand;
import com.zov.smart.nova.system.api.command.config.UpdateConfigCommand;
import com.zov.smart.nova.system.api.dto.config.ConfigDTO;
import com.zov.smart.nova.system.api.query.config.ConfigPageQuery;
import com.zov.smart.nova.system.api.vo.config.ConfigVO;
import com.zov.smart.nova.system.biz.ConfigBiz;
import com.zov.smart.nova.system.converter.ConfigConverter;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/** System config business implementation. */
@Service
public class ConfigBizImpl implements ConfigBiz {

    private final ConfigService configService;
    private final ConfigConverter configConverter;

    public ConfigBizImpl(ConfigService configService, ConfigConverter configConverter) {
        this.configService = configService;
        this.configConverter = configConverter;
    }

    @Override
    public ConfigDTO getConfigByKey(String configKey) {
        return configConverter.toDTO(configService.getByKeyRequired(configKey));
    }

    @Override
    public PageResult<ConfigVO> pageConfigs(ConfigPageQuery query) {
        PageResult<SysConfigDO> page = configService.pageConfigs(query);
        List<ConfigVO> records = new ArrayList<ConfigVO>();
        for (SysConfigDO item : page.getRecords()) {
            records.add(configConverter.toVO(item));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public String getConfigValue(String configKey) {
        return configService.getByKeyRequired(configKey).getConfigValue();
    }

    @Override
    public Boolean getBoolean(String configKey) {
        SysConfigDO config = configService.getByKeyRequired(configKey);
        assertType(config, ConfigTypeEnum.BOOLEAN);
        return Boolean.valueOf(config.getConfigValue());
    }

    @Override
    public Integer getInteger(String configKey) {
        SysConfigDO config = configService.getByKeyRequired(configKey);
        assertType(config, ConfigTypeEnum.NUMBER);
        try {
            return Integer.valueOf(config.getConfigValue());
        } catch (NumberFormatException ex) {
            throw new BusinessException(SystemErrorCode.CONFIG_VALUE_TYPE_INVALID, "参数值不是有效整数", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createConfig(CreateConfigCommand command) {
        if (configService.findByKey(command.getConfigKey()) != null) {
            throw new BusinessException(SystemErrorCode.CONFIG_KEY_EXISTS);
        }
        return configService.createConfig(configConverter.toCreateDO(command));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(UpdateConfigCommand command) {
        SysConfigDO config = configService.getByIdRequired(command.getId());
        configConverter.updateDO(command, config);
        configService.updateConfig(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long configId) {
        configService.getByIdRequired(configId);
        configService.deleteConfig(configId);
    }

    private void assertType(SysConfigDO config, ConfigTypeEnum expected) {
        if (!expected.equals(config.getConfigType())) {
            throw new BusinessException(SystemErrorCode.CONFIG_VALUE_TYPE_INVALID);
        }
    }
}
