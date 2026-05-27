package com.zov.smart.nova.system.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DictConfigLogEnumControllerContractTest {
    @Test void controllerPathsMatchSpec() {
        assertArrayEquals(new String[]{"/api/system"}, DictController.class.getAnnotation(RequestMapping.class).value());
        assertArrayEquals(new String[]{"/api/system/configs"}, ConfigController.class.getAnnotation(RequestMapping.class).value());
        assertArrayEquals(new String[]{"/api/system"}, LogController.class.getAnnotation(RequestMapping.class).value());
        assertArrayEquals(new String[]{"/api/system/enums"}, EnumController.class.getAnnotation(RequestMapping.class).value());
    }
}
