package com.zov.smart.nova.system.api.command.config;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateConfigCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "参数ID不能为空")
    private Long id;

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




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
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

}
