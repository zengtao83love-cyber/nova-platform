package com.zov.smart.nova.common.mybatis.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BaseDOTest {
    @Test
    void shouldReadAndWriteAuditFields() {
        DemoDO demo = new DemoDO();
        LocalDateTime now = LocalDateTime.now();

        demo.setId(1L);
        demo.setCreatedBy(10L);
        demo.setCreatedAt(now);
        demo.setUpdatedBy(11L);
        demo.setUpdatedAt(now.plusSeconds(1));
        demo.setDeleteFlag(0);
        demo.setVersion(0);

        assertEquals(1L, demo.getId());
        assertEquals(10L, demo.getCreatedBy());
        assertEquals(now, demo.getCreatedAt());
        assertEquals(11L, demo.getUpdatedBy());
        assertEquals(now.plusSeconds(1), demo.getUpdatedAt());
        assertEquals(Integer.valueOf(0), demo.getDeleteFlag());
        assertEquals(Integer.valueOf(0), demo.getVersion());
    }

    private static class DemoDO extends BaseDO {
        private static final long serialVersionUID = 1L;
    }
}
