package com.zov.smart.nova.system.facade;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FacadeBoundaryContractTest {
    @Test
    void facadeImplDoesNotImportMapperOrDataAccessDo() throws IOException {
        Path base = Paths.get("src/main/java/com/zov/smart/nova/system/facade");
        for (Path path : Files.newDirectoryStream(base, "*.java")) {
            String source = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            assertFalse(source.contains(".mapper."));
            assertFalse(source.contains("SysUserDO"));
            assertFalse(source.contains("SysRoleDO"));
        }
    }
}
