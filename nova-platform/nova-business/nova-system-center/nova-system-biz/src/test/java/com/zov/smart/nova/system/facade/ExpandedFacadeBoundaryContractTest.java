package com.zov.smart.nova.system.facade;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ExpandedFacadeBoundaryContractTest {
    @Test void facadeImplsDoNotInjectMappersDirectly() {
        assertNoMapper(MenuFacadeImpl.class);
        assertNoMapper(PermissionFacadeImpl.class);
        assertNoMapper(DictFacadeImpl.class);
        assertNoMapper(ConfigFacadeImpl.class);
        assertNoMapper(LogFacadeImpl.class);
        assertNoMapper(EnumFacadeImpl.class);
    }
    private void assertNoMapper(Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            assertFalse(field.getType().getName().contains("Mapper"), type.getSimpleName() + " must not inject Mapper directly");
        }
    }
}
