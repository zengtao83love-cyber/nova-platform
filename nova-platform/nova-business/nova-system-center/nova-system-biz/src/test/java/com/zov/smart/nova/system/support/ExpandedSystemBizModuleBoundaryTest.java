package com.zov.smart.nova.system.support;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpandedSystemBizModuleBoundaryTest {
    @Test void systemBizStillDoesNotContainBootstrapOnlyConfigFiles() {
        File root = new File("src/main");
        List<File> files = new ArrayList<File>();
        collect(root, files);
        for (File file : files) {
            String name = file.getName();
            assertTrue(!"application.yml".equals(name) && !"bootstrap.yml".equals(name) && !"application.properties".equals(name) && !"bootstrap.properties".equals(name));
        }
    }
    private void collect(File file, List<File> files) {
        if (file == null || !file.exists()) return;
        if (file.isFile()) { files.add(file); return; }
        File[] children = file.listFiles();
        if (children != null) for (File child : children) collect(child, files);
    }
}
