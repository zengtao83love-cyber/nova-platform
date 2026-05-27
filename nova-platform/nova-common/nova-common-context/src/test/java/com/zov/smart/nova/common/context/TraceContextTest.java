package com.zov.smart.nova.common.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceContextTest {
    @AfterEach
    void tearDown() {
        TraceContext.clear();
    }

    @Test
    void shouldGenerateAndReuseTraceIdInSameThread() {
        String first = TraceContext.get();
        String second = TraceContext.get();

        assertNotNull(first);
        assertEquals(32, first.length());
        assertTrue(first.matches("[0-9a-f]{32}"));
        assertEquals(first, second);
    }

    @Test
    void shouldSetTrimAndClearTraceId() {
        TraceContext.set("  req-001  ");

        assertEquals("req-001", TraceContext.get());
        assertEquals("req-001", RequestIdHolder.get());

        TraceContext.clear();
        assertNull(TraceContext.peek());
    }
}
