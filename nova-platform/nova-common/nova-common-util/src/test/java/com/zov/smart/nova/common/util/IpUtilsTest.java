package com.zov.smart.nova.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IpUtilsTest {
    @Test
    void shouldPickFirstValidIpFromForwardedHeader() {
        assertEquals("10.0.0.1", IpUtils.firstValidIp("unknown, 10.0.0.1, 10.0.0.2"));
        assertNull(IpUtils.firstValidIp("unknown, , UNKNOWN"));
        assertTrue(IpUtils.isUnknown("unknown"));
    }
}
