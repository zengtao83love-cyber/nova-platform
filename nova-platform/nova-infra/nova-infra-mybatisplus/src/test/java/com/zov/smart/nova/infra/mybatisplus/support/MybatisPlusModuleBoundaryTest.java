package com.zov.smart.nova.infra.mybatisplus.support;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MybatisPlusModuleBoundaryTest {

    @Test
    void moduleSourceMustNotContainForbiddenPersistenceContracts() throws IOException {
        Path moduleRoot = Paths.get("src/main");
        if (!Files.exists(moduleRoot)) {
            return;
        }

        try (Stream<Path> stream = Files.walk(moduleRoot)) {
            List<Path> files = stream
                    .filter(path -> Files.isRegularFile(path))
                    .collect(Collectors.toList());
            for (Path file : files) {
                String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
                assertFalse(content.contains("NovaBaseMapper"), file.toString());
                assertFalse(content.contains("SysUserDO"), file.toString());
                assertFalse(content.contains("SysUserMapper"), file.toString());
                assertFalse(content.contains("jak" + "arta."), file.toString());
                assertFalse(content.contains("@MapperScan"), file.toString());
            }
        }
    }

    @Test
    void shouldRegisterAutoConfigurationBySpringFactories() throws IOException {
        Path springFactories = Paths.get("src/main/resources/META-INF/spring.factories");
        if (!Files.exists(springFactories)) {
            return;
        }
        String content = new String(Files.readAllBytes(springFactories), StandardCharsets.UTF_8);
        assertTrue(content.contains("org.springframework.boot.autoconfigure.EnableAutoConfiguration"));
        assertTrue(content.contains("NovaMybatisPlusAutoConfiguration"));
    }
}
