package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.system.biz.impl.EnumBizImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumBizContractTest {
    @Test void enumBizReturnsKnownEntityAndRejectsUnknownEntity() {
        EnumBiz biz = new EnumBizImpl();
        assertFalse(biz.listAllEntityEnums().isEmpty());
        assertEquals("menu", biz.getEntityEnums("menu").getEntityName());
        assertThrows(BusinessException.class, () -> biz.getEntityEnums("unknown"));
    }
}
