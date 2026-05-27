package com.zov.smart.nova.common.model;

import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageParamTest {

    @Test
    void defaultPageParamMatchesDtoMatrix() {
        PageParam param = new PageParam();

        assertEquals(Integer.valueOf(1), param.getPageNo());
        assertEquals(Integer.valueOf(10), param.getPageSize());
        assertEquals(0L, param.offset());
        assertTrue(param instanceof Serializable);
    }

    @Test
    void constructorNormalizesInvalidBoundaryValues() {
        PageParam param = new PageParam(0, 0);

        assertEquals(Integer.valueOf(1), param.getPageNo());
        assertEquals(Integer.valueOf(10), param.getPageSize());
    }

    @Test
    void pageSizeIsCappedAtOneHundred() {
        PageParam param = new PageParam(3, 101);

        assertEquals(Integer.valueOf(3), param.getPageNo());
        assertEquals(Integer.valueOf(100), param.getPageSize());
        assertEquals(200L, param.offset());
    }

    @Test
    void validValuesAreKept() {
        PageParam param = new PageParam();

        param.setPageNo(2);
        param.setPageSize(20);

        assertEquals(Integer.valueOf(2), param.getPageNo());
        assertEquals(Integer.valueOf(20), param.getPageSize());
        assertEquals(20L, param.offset());
    }
}
