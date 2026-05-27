package com.zov.smart.nova.infra.redis.support;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisModuleBoundaryTest {

    @Test
    void shouldRegisterAutoConfigurationThroughSpringFactories() throws IOException {
        Path factories = Paths.get("src/main/resources/META-INF/spring.factories");
        String content = new String(Files.readAllBytes(factories), StandardCharsets.UTF_8);

        assertTrue(content.contains("org.springframework.boot.autoconfigure.EnableAutoConfiguration"));
        assertTrue(content.contains("com.zov.smart.nova.infra.redis.config.RedisAutoConfiguration"));
    }

    @Test
    void shouldNotUseBroadJacksonPolymorphicTypingOrForbiddenCouplings() throws IOException {
        Path root = Paths.get("src/main/java");
        StringBuilder all = new StringBuilder();
        Files.walk(root)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> append(all, path));

        assertFalse(all.toString().contains("activateDefaultTyping"));
        assertFalse(all.toString().contains("enableDefaultTyping"));
        assertFalse(all.toString().contains("jak" + "arta."));
        assertFalse(all.toString().contains("Sys" + "UserDO"));
        assertFalse(all.toString().contains("Base" + "Mapper"));
    }

    private static void append(StringBuilder builder, Path path) {
        try {
            builder.append(new String(Files.readAllBytes(path), StandardCharsets.UTF_8)).append('\n');
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
