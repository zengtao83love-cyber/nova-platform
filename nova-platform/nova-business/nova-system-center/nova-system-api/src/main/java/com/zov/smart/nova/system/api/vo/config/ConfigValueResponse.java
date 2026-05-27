package com.zov.smart.nova.system.api.vo.config;



public class ConfigValueResponse implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String configKey;

    private String configValue;

    private String configType;




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


            public String getConfigType() {
                return configType;
            }

            public void setConfigType(String configType) {
                this.configType = configType;
            }
     public ConfigValueResponse(){this.configType="STRING";} 
}
