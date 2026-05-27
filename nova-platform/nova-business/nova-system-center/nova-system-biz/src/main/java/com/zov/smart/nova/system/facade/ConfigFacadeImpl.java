package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.config.CreateConfigCommand;
import com.zov.smart.nova.system.api.command.config.UpdateConfigCommand;
import com.zov.smart.nova.system.api.dto.config.ConfigDTO;
import com.zov.smart.nova.system.api.facade.ConfigFacade;
import com.zov.smart.nova.system.api.query.config.ConfigPageQuery;
import com.zov.smart.nova.system.api.vo.config.ConfigVO;
import com.zov.smart.nova.system.biz.ConfigBiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** Config facade implementation. */
@Service
public class ConfigFacadeImpl implements ConfigFacade {
    private final ConfigBiz configBiz;
    public ConfigFacadeImpl(ConfigBiz configBiz) { this.configBiz = configBiz; }
    @Override public ConfigDTO getConfigByKey(String configKey) { return configBiz.getConfigByKey(configKey); }
    @Override public PageResult<ConfigDTO> pageConfigs(ConfigPageQuery query) {
        PageResult<ConfigVO> page = configBiz.pageConfigs(query);
        List<ConfigDTO> records = new ArrayList<ConfigDTO>();
        for (ConfigVO item : page.getRecords()) { ConfigDTO dto = new ConfigDTO(); dto.setId(item.getId()); dto.setConfigKey(item.getConfigKey()); dto.setConfigValue(item.getConfigValue()); dto.setConfigName(item.getConfigName()); dto.setConfigType(item.getConfigType()); dto.setRemark(item.getRemark()); dto.setCreatedAt(item.getCreatedAt()); dto.setUpdatedAt(item.getUpdatedAt()); records.add(dto); }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }
    @Override public String getConfigValue(String configKey) { return configBiz.getConfigValue(configKey); }
    @Override public Boolean getBoolean(String configKey) { return configBiz.getBoolean(configKey); }
    @Override public Integer getInteger(String configKey) { return configBiz.getInteger(configKey); }
    @Override public Long createConfig(CreateConfigCommand command) { return configBiz.createConfig(command); }
    @Override public void updateConfig(UpdateConfigCommand command) { configBiz.updateConfig(command); }
    @Override public void deleteConfig(Long configId) { configBiz.deleteConfig(configId); }
}
