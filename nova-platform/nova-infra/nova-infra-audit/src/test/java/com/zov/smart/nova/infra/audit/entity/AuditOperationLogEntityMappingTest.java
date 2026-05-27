package com.zov.smart.nova.infra.audit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AuditOperationLogEntityMappingTest {

    @Test
    void auditEntityOwnsAuditTableAndDoesNotExtendBaseDO() {
        TableName tableName = AuditOperationLogDO.class.getAnnotation(TableName.class);
        assertEquals("audit_operation_log", tableName.value());
        assertFalse(BaseDO.class.isAssignableFrom(AuditOperationLogDO.class));
    }

    @Test
    void entityCanStoreOperationTypeEnum() {
        AuditOperationLogDO log = new AuditOperationLogDO();
        log.setOperationType(AuditOperationTypeEnum.CREATE);
        assertEquals(AuditOperationTypeEnum.CREATE, log.getOperationType());
    }
}
