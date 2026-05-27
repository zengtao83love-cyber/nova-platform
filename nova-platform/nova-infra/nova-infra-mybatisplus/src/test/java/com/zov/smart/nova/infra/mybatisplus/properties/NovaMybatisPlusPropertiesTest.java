package com.zov.smart.nova.infra.mybatisplus.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NovaMybatisPlusPropertiesTest {

    @Test
    void shouldUseSpecDefaults() {
        NovaMybatisPlusProperties properties = new NovaMybatisPlusProperties();

        assertTrue(properties.getPagination().isEnabled());
        assertEquals(500L, properties.getPagination().getMaxLimit());
        assertFalse(properties.getPagination().isOverflow());
        assertTrue(properties.getOptimisticLocker().isEnabled());
        assertTrue(properties.getBlockAttack().isEnabled());
        assertTrue(properties.getAuditFill().isEnabled());
    }

    @Test
    void shouldNormalizeInvalidPaginationLimit() {
        NovaMybatisPlusProperties properties = new NovaMybatisPlusProperties();
        properties.getPagination().setMaxLimit(0L);

        assertEquals(500L, properties.getPagination().getMaxLimit());
    }
}
