package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.config.enums.ConfigTypeEnum;

/**
 * 系统参数持久化对象，对应表 sys_config。
 *
 * <p>运行时安全配置仍以 application.yml / 环境变量为准；sys_config 当前只作为系统参数管理数据。</p>
 */
@TableName(value = "sys_config", autoResultMap = true)
public class SysConfigDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    @TableField("config_key")
    private String configKey;

    @TableField("config_value")
    private String configValue;

    @TableField("config_name")
    private String configName;

    @TableField(value = "config_type", typeHandler = EnumCodeTypeHandler.class)
    private ConfigTypeEnum configType;

    @TableField("remark")
    private String remark;

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }
    public String getConfigName() { return configName; }
    public void setConfigName(String configName) { this.configName = configName; }
    public ConfigTypeEnum getConfigType() { return configType; }
    public void setConfigType(ConfigTypeEnum configType) { this.configType = configType; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
