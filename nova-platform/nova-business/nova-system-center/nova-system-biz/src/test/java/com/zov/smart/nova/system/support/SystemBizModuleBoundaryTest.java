package com.zov.smart.nova.system.support;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemBizModuleBoundaryTest {
    @Test
    void controllersDoNotImportMapperOrServiceLayer() throws IOException {
        Path base = Paths.get("src/main/java/com/zov/smart/nova/system/controller");
        for (Path path : Files.newDirectoryStream(base, "*.java")) {
            String source = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            assertFalse(source.contains(".mapper."));
            assertFalse(source.contains(".service."));
            assertTrue(source.contains(".biz."));
        }
    }

    @Test
    void bizImplDoesNotImportMapperDirectly() throws IOException {
        Path base = Paths.get("src/main/java/com/zov/smart/nova/system/biz/impl");
        for (Path path : Files.newDirectoryStream(base, "*.java")) {
            String source = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            assertFalse(source.contains(".mapper."));
        }
    }
}
