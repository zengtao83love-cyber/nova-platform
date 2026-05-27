package com.zov.smart.nova.system.converter;

import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserConverterContractTest {
    @Test
    void userConverterUsesMapStructSpringComponentModel() {
        Mapper mapper = UserConverter.class.getAnnotation(Mapper.class);
        assertNotNull(mapper);
        assertEquals("spring", mapper.componentModel());
    }
}
