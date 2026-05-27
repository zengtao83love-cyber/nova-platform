package com.zov.smart.nova.data.access.system.menu.enums;

    import com.zov.smart.nova.common.mybatis.enums.DbEnum;

    /**
     * 系统菜单类型枚举。
     *
     * <p>业务枚举持久化 code 必须使用 String，与数据库 varchar 字段保持一致。</p>
     */
    public enum MenuTypeEnum implements DbEnum {
        DIR("DIR", "目录"),
MENU("MENU", "菜单"),
BUTTON("BUTTON", "按钮");

        private final String code;
        private final String label;

        MenuTypeEnum(String code, String label) {
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
        public static MenuTypeEnum fromCode(String code) {
            if (code == null || code.trim().isEmpty()) {
                return null;
            }
            for (MenuTypeEnum item : values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
            return null;
        }
    }
