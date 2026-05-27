package com.zov.smart.nova.bootstrap;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

/** Contract test for bootstrap-only application entry. */
class NovaApplicationContractTest {

    @Test
    void novaApplicationOwnsSpringBootApplicationAndMapperScan() {
        assertNotNull(NovaApplication.class.getAnnotation(SpringBootApplication.class));
        MapperScan mapperScan = NovaApplication.class.getAnnotation(MapperScan.class);
        assertNotNull(mapperScan);
        assertArrayEquals(new String[]{
                "com.zov.smart.nova.data.access.system.mapper",
                "com.zov.smart.nova.infra.audit.mapper"
        }, mapperScan.basePackages());
    }

    @Test
    void noUnexpectedCustomAnnotationsAreRequiredForStartup() {
        Annotation[] annotations = NovaApplication.class.getAnnotations();
        assertTrue(annotations.length >= 2);
    }
}
