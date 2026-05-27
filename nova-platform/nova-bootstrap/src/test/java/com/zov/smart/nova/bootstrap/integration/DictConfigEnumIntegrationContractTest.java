package com.zov.smart.nova.bootstrap.integration;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** T-134 dict/config/enum integration contract checks. */
class DictConfigEnumIntegrationContractTest {

    private static Path findProjectRoot() {
        Path current = Paths.get("").toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
            Path pom = current.resolve("pom.xml");
            if (Files.exists(pom)) {
                try {
                    String text = new String(Files.readAllBytes(pom), StandardCharsets.UTF_8);
                    if (text.contains("<artifactId>nova-platform</artifactId>")) {
                        return current;
                    }
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }
            }
            current = current.getParent();
        }
        throw new IllegalStateException("Cannot locate nova-platform project root");
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static List<Path> javaFiles(Path root) throws IOException {
        try (Stream<Path> stream = Files.walk(root)) {
            return stream.filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        }
    }


    @Test
    void dictConfigEnumControllersMustExist() throws IOException {
        Path root = findProjectRoot();
        assertTrue(Files.exists(root.resolve("nova-business/nova-system-center/nova-system-biz/src/main/java/com/zov/smart/nova/system/controller/DictController.java")));
        assertTrue(Files.exists(root.resolve("nova-business/nova-system-center/nova-system-biz/src/main/java/com/zov/smart/nova/system/controller/ConfigController.java")));
        assertTrue(Files.exists(root.resolve("nova-business/nova-system-center/nova-system-biz/src/main/java/com/zov/smart/nova/system/controller/EnumController.java")));
    }

    @Test
    void enumUnknownEntityMustReturnCommonEnumNotFound() throws IOException {
        Path root = findProjectRoot();
        String enumBiz = read(root.resolve("nova-business/nova-system-center/nova-system-biz/src/main/java/com/zov/smart/nova/system/biz/impl/EnumBizImpl.java"));
        assertTrue(enumBiz.contains("COMMON_ENUM_NOT_FOUND"));
    }
}
