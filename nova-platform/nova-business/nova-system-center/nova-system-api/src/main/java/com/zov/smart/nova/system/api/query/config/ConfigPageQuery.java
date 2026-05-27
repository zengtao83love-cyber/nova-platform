package com.zov.smart.nova.system.api.query.config;

import com.zov.smart.nova.common.model.PageParam;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ConfigPageQuery extends PageParam {
    private static final long serialVersionUID = 1L;
    @Size(max = 128) private String configKey;
    @Size(max = 128) private String configName;
    @Pattern(regexp = "^$|STRING|NUMBER|BOOLEAN|JSON") private String configType;
    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigName() { return configName; }
    public void setConfigName(String configName) { this.configName = configName; }
    public String getConfigType() { return configType; }
    public void setConfigType(String configType) { this.configType = configType; }
}
