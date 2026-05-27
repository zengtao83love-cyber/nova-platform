package com.zov.smart.nova.system.api.command.config;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateConfigCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "参数键不能为空")
    @Size(max = 128, message = "参数键长度不能超过128")
    @Pattern(regexp = "^[a-zA-Z0-9_.:-]+$", message = "参数键格式不正确")
    private String configKey;

    @NotBlank(message = "参数值不能为空")
    @Size(max = 2000, message = "参数值长度不能超过2000")
    private String configValue;

    @NotBlank(message = "参数名称不能为空")
    @Size(max = 128, message = "参数名称长度不能超过128")
    private String configName;

    @Pattern(regexp = "^$|STRING|NUMBER|BOOLEAN|JSON", message = "参数类型只能是STRING/NUMBER/BOOLEAN/JSON")
    private String configType;

    @Size(max = 255, message = "备注长度不能超过255")
    private String remark;




            public String getConfigKey() {
                return configKey;
            }

            public void setConfigKey(String configKey) {
                this.configKey = configKey;
            }


            public String getConfigValue() {
                return configValue;
            }

            public void setConfigValue(String configValue) {
                this.configValue = configValue;
            }


            public String getConfigName() {
                return configName;
            }

            public void setConfigName(String configName) {
                this.configName = configName;
            }


            public String getConfigType() {
                return configType;
            }

            public void setConfigType(String configType) {
                this.configType = configType;
            }


            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
     public CreateConfigCommand(){this.configType="STRING";} 
}
