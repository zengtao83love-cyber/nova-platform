package com.zov.smart.nova.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaskUtilsTest {
    @Test
    void shouldMaskCommonSensitiveValues() {
        assertEquals("138****5678", MaskUtils.maskMobile("13812345678"));
        assertEquals("z****@example.com", MaskUtils.maskEmail("zeng@example.com"));
        assertEquals("张*", MaskUtils.maskName("张三"));
        assertEquals("1101**********5678", MaskUtils.maskIdCard("110101199001015678"));
        assertEquals("abcdef******uvwxyz", MaskUtils.maskToken("abcdefghijklmnopqrstuvwxyz"));
    }
}
