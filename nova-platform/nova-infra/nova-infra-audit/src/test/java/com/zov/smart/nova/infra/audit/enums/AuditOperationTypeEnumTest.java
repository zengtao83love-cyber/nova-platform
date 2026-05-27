package com.zov.smart.nova.infra.audit.enums;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditOperationTypeEnumTest {

    @Test
    void operationTypeCodesAreStableAndUnique() {
        Set<String> codes = new HashSet<>();
        for (AuditOperationTypeEnum type : AuditOperationTypeEnum.values()) {
            assertTrue(codes.add(type.getCode()));
        }
        assertEquals("CREATE", AuditOperationTypeEnum.CREATE.getCode());
        assertEquals("OTHER", AuditOperationTypeEnum.OTHER.getCode());
    }
}
