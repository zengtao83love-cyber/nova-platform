package com.zov.smart.nova.common.mybatis.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DbEnumTest {
    @Test
    void shouldExposeStringCodeAndLabel() {
        assertEquals("ENABLE", DemoStatus.ENABLE.getCode());
        assertEquals("启用", DemoStatus.ENABLE.getLabel());
    }

    private enum DemoStatus implements DbEnum {
        ENABLE("ENABLE", "启用"),
        DISABLE("DISABLE", "禁用");

        private final String code;
        private final String label;

        DemoStatus(String code, String label) {
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
    }
}
