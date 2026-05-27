package com.zov.smart.nova.system.api.vo.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConfigVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String configKey;

    private String configValue;

    private String configName;

    private String configType;

    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


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


            public LocalDateTime getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
            }


            public LocalDateTime getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
            }
     public ConfigVO(){this.configType="STRING";} 
}
