package com.zov.smart.nova.system.service;

import com.zov.smart.nova.system.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleServiceContractTest {
    @Test
    void roleServiceImplIsAtomicPersistenceLayerWithoutTransactionalAnnotation() {
        for (Method method : RoleServiceImpl.class.getDeclaredMethods()) {
            assertEquals(null, method.getAnnotation(org.springframework.transaction.annotation.Transactional.class));
        }
    }
}
