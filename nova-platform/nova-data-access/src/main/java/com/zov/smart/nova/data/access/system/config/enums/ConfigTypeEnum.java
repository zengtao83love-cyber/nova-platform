package com.zov.smart.nova.data.access.system.config.enums;

    import com.zov.smart.nova.common.mybatis.enums.DbEnum;

    /**
     * 系统参数值类型枚举。
     *
     * <p>业务枚举持久化 code 必须使用 String，与数据库 varchar 字段保持一致。</p>
     */
    public enum ConfigTypeEnum implements DbEnum {
        STRING("STRING", "字符串"),
NUMBER("NUMBER", "数字"),
BOOLEAN("BOOLEAN", "布尔"),
JSON("JSON", "JSON");

        private final String code;
        private final String label;

        ConfigTypeEnum(String code, String label) {
            this.code = code;
            this.label = label;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getLabel() {
            return label;
        }

        /**
         * 根据数据库 code 转换为枚举；未知 code 返回 null，由调用方决定是否抛出业务异常。
         */
        public static ConfigTypeEnum fromCode(String code) {
            if (code == null || code.trim().isEmpty()) {
                return null;
            }
            for (ConfigTypeEnum item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }
