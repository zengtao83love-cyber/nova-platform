package com.zov.smart.nova.infra.mybatisplus.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.model.PageParam;
import com.zov.smart.nova.common.model.PageResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageConvertorTest {

    @Test
    void shouldConvertMappedPage() {
        Page<Integer> page = new Page<Integer>(2, 3, 11);
        page.setRecords(Arrays.asList(1, 2, 3));

        PageResult<String> result = PageConvertor.convert(page, value -> "N" + value);

        assertEquals(2L, result.getPageNo());
        assertEquals(3L, result.getPageSize());
        assertEquals(11L, result.getTotal());
        assertEquals(Arrays.asList("N1", "N2", "N3"), result.getRecords());
    }

    @Test
    void shouldReturnEmptyResultWhenIPageIsNull() {
        PageResult<String> result = PageConvertor.convert(null, value -> value);

        assertEquals(1L, result.getPageNo());
        assertEquals(10L, result.getPageSize());
        assertEquals(0L, result.getTotal());
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    void shouldCreateMybatisPageFromPageParam() {
        Page<Object> page = MybatisPageHelper.toPage(new PageParam(3, 20));

        assertEquals(3L, page.getCurrent());
        assertEquals(20L, page.getSize());
    }
}
