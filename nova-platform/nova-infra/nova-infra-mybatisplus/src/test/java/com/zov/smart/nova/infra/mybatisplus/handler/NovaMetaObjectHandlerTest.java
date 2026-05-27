package com.zov.smart.nova.infra.mybatisplus.handler;

import com.zov.smart.nova.common.context.LoginUserContext;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NovaMetaObjectHandlerTest {

    private final NovaMetaObjectHandler handler = new NovaMetaObjectHandler();

    @AfterEach
    void tearDown() {
        LoginUserContext.clear();
    }

    @Test
    void insertFillShouldFallbackToSystemUserWhenContextMissing() {
        FillTarget target = new FillTarget();

        handler.insertFill(SystemMetaObject.forObject(target));

        assertEquals(0L, target.getCreatedBy());
        assertEquals(0L, target.getUpdatedBy());
        assertNotNull(target.getCreatedAt());
        assertNotNull(target.getUpdatedAt());
        assertEquals(0, target.getDeleteFlag());
        assertEquals(0, target.getVersion());
    }

    @Test
    void insertFillShouldUseCurrentUserContext() {
        LoginUserContext.set(new LoginUserContext.CurrentUser(1001L, "admin", "Admin", true));
        FillTarget target = new FillTarget();

        handler.insertFill(SystemMetaObject.forObject(target));

        assertEquals(1001L, target.getCreatedBy());
        assertEquals(1001L, target.getUpdatedBy());
    }

    @Test
    void updateFillShouldOnlyFillUpdateFields() {
        LoginUserContext.set(new LoginUserContext.CurrentUser(2002L, "operator", "Operator", false));
        FillTarget target = new FillTarget();

        handler.updateFill(SystemMetaObject.forObject(target));

        assertNull(target.getCreatedBy());
        assertNull(target.getCreatedAt());
        assertEquals(2002L, target.getUpdatedBy());
        assertNotNull(target.getUpdatedAt());
    }

    public static class FillTarget {
        private Long createdBy;
        private LocalDateTime createdAt;
        private Long updatedBy;
        private LocalDateTime updatedAt;
        private Integer deleteFlag;
        private Integer version;

        public Long getCreatedBy() { return createdBy; }
        public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public Long getUpdatedBy() { return updatedBy; }
        public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
        public Integer getDeleteFlag() { return deleteFlag; }
        public void setDeleteFlag(Integer deleteFlag) { this.deleteFlag = deleteFlag; }
        public Integer getVersion() { return version; }
        public void setVersion(Integer version) { this.version = version; }
    }
}
