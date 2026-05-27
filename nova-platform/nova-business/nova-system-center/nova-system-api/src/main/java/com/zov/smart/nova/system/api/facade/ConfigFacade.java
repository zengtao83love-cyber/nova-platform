package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.config.CreateConfigCommand;
import com.zov.smart.nova.system.api.command.config.UpdateConfigCommand;
import com.zov.smart.nova.system.api.dto.config.ConfigDTO;
import com.zov.smart.nova.system.api.query.config.ConfigPageQuery;

/** System configuration contract exposed by system center. */
public interface ConfigFacade {
    ConfigDTO getConfigByKey(String configKey);
    PageResult<ConfigDTO> pageConfigs(ConfigPageQuery query);
    String getConfigValue(String configKey);
    Boolean getBoolean(String configKey);
    Integer getInteger(String configKey);
    Long createConfig(CreateConfigCommand command);
    void updateConfig(UpdateConfigCommand command);
    void deleteConfig(Long configId);
}
