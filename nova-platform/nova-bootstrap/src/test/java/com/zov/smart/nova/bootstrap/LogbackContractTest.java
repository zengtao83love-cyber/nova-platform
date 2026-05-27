package com.zov.smart.nova.bootstrap;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/** Contract test ensuring logback includes traceId in every log line pattern. */
class LogbackContractTest {

    @Test
    void logbackPatternContainsTraceId() throws Exception {
        InputStream input = getClass().getClassLoader().getResourceAsStream("logback-spring.xml");
        assertNotNull(input);
        String xml = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        assertTrue(xml.contains("%X{traceId"));
        assertTrue(xml.contains("RollingFileAppender"));
    }
}
