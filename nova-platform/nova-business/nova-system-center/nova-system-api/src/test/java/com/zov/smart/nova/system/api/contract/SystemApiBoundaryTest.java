package com.zov.smart.nova.system.api.contract;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SystemApiBoundaryTest {
    @Test
    void systemApiPomMustNotDependOnForbiddenModules() throws IOException {
        Path pom = Paths.get("pom.xml").toAbsolutePath();
        String xml = new String(Files.readAllBytes(pom), StandardCharsets.UTF_8);
        assertFalse(xml.contains("nova-data-access"));
        assertFalse(xml.contains("nova-infra-"));
        assertFalse(xml.contains("spring-boot-starter-web"));
        assertFalse(xml.contains("mybatis-plus"));
    }

    @Test
    void systemApiSourcesMustNotContainForbiddenImplementationTypes() throws IOException {
        Path src = Paths.get("src/main/java");
        if (!Files.exists(src)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(src)) {
            paths.filter(path -> path.toString().endsWith(".java")).forEach(path -> {
                try {
                    String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                    assertFalse(content.contains("Controller"), path + " must not declare controller content");
                    assertFalse(content.contains("ServiceImpl"), path + " must not declare service implementation content");
                    assertFalse(content.contains("Mapper"), path + " must not declare mapper content");
                    assertFalse(content.contains("com.baomidou.mybatisplus.annotation"), path + " must not use MyBatis annotations");
                    assertFalse(content.contains("jakarta."), path + " must not use jakarta namespace");
                } catch (IOException e) {
                    fail(e);
                }
            });
        }
    }
}
