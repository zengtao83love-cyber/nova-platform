package com.zov.smart.nova.common.model;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageResultTest {

    @Test
    void defaultPageResultIsEmptyAndSerializable() {
        PageResult<String> result = new PageResult<String>();

        assertEquals(1L, result.getPageNo());
        assertEquals(10L, result.getPageSize());
        assertEquals(0L, result.getTotal());
        assertTrue(result.getRecords().isEmpty());
        assertTrue(result instanceof Serializable);
    }

    @Test
    void factoryCreatesExpectedPagingResult() {
        PageResult<String> result = PageResult.of(2L, 20L, 45L, Arrays.asList("a", "b"));

        assertEquals(2L, result.getPageNo());
        assertEquals(20L, result.getPageSize());
        assertEquals(45L, result.getTotal());
        assertEquals(Arrays.asList("a", "b"), result.getRecords());
    }

    @Test
    void nullRecordsAreNormalizedToEmptyList() {
        PageResult<String> result = PageResult.of(1L, 10L, 0L, null);

        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    void recordsAreDefensivelyCopied() {
        List<String> source = new ArrayList<String>();
        source.add("before");

        PageResult<String> result = PageResult.of(1L, 10L, 1L, source);
        source.add("after");

        assertEquals(1, result.getRecords().size());
        assertEquals("before", result.getRecords().get(0));
        assertNotSame(source, result.getRecords());
    }
}
