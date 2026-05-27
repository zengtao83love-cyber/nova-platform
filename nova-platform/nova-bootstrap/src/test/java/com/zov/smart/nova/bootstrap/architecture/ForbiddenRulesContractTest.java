package com.zov.smart.nova.bootstrap.architecture;

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

/** T-137 forbidden rule checks. */
class ForbiddenRulesContractTest {

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
    void generatedCodeMustNotUseJakartaNovaBaseMapperOrWebSecurityConfigurerAdapter() throws IOException {
        Path root = findProjectRoot();
        for (Path file : javaFiles(root).stream().filter(path -> path.toString().contains("/src/main/java/")).collect(Collectors.toList())) {
            String text = read(file);
            assertFalse(text.contains("jakarta."), file + " must not use jakarta namespace");
            assertFalse(text.contains("NovaBaseMapper"), file + " must not use NovaBaseMapper");
            assertFalse(text.contains("WebSecurityConfigurerAdapter"), file + " must not use deprecated security adapter");
        }
    }

    @Test
    void applicationYamlAndMapperScanMustOnlyLiveInBootstrap() throws IOException {
        Path root = findProjectRoot();
        List<Path> ymls;
        try (Stream<Path> stream = Files.walk(root)) {
            ymls = stream.filter(path -> path.getFileName().toString().equals("application.yml"))
                    .collect(Collectors.toList());
        }
        assertTrue(ymls.size() == 1);
        assertTrue(ymls.get(0).toString().contains("nova-bootstrap"));

        List<Path> mapperScanFiles = javaFiles(root).stream()
                .filter(path -> path.toString().contains("/src/main/java/"))
                .filter(path -> {
            try { return read(path).contains("@MapperScan"); } catch (IOException ex) { throw new IllegalStateException(ex); }
        }).collect(Collectors.toList());
        assertTrue(mapperScanFiles.size() == 1);
        assertTrue(mapperScanFiles.get(0).toString().contains("nova-bootstrap"));
    }
}
