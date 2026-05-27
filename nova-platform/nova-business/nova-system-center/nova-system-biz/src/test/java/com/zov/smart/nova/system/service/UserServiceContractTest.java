package com.zov.smart.nova.system.service;

import com.zov.smart.nova.system.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceContractTest {
    @Test
    void userServiceImplIsAtomicPersistenceLayerWithoutTransactionalAnnotation() {
        for (Method method : UserServiceImpl.class.getDeclaredMethods()) {
            assertEquals(null, method.getAnnotation(org.springframework.transaction.annotation.Transactional.class));
        }
    }
}
