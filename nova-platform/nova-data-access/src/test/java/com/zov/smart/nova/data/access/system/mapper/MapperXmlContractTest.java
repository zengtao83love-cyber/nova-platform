package com.zov.smart.nova.data.access.system.mapper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mapper XML 文件路径、namespace、SQL 软删除过滤与禁止项保护测试。
 */
class MapperXmlContractTest {

    @Test
    void mapperXmlFilesShouldUseExpectedNamespaceAndStayUnderMapperSystem() throws IOException {
        Map<String, String> xmlNamespacePairs = new LinkedHashMap<>();
        xmlNamespacePairs.put("SysUserMapper.xml", SysUserMapper.class.getName());
        xmlNamespacePairs.put("SysRoleMapper.xml", SysRoleMapper.class.getName());
        xmlNamespacePairs.put("SysMenuMapper.xml", SysMenuMapper.class.getName());
        xmlNamespacePairs.put("SysUserRoleMapper.xml", SysUserRoleMapper.class.getName());
        xmlNamespacePairs.put("SysRoleMenuMapper.xml", SysRoleMenuMapper.class.getName());
        xmlNamespacePairs.put("SysDictTypeMapper.xml", SysDictTypeMapper.class.getName());
        xmlNamespacePairs.put("SysDictItemMapper.xml", SysDictItemMapper.class.getName());
        xmlNamespacePairs.put("SysConfigMapper.xml", SysConfigMapper.class.getName());
        xmlNamespacePairs.put("SysLoginLogMapper.xml", SysLoginLogMapper.class.getName());

        for (Map.Entry<String, String> entry : xmlNamespacePairs.entrySet()) {
            String xml = readMapperXml(entry.getKey());
            assertTrue(xml.contains("<mapper namespace=\"" + entry.getValue() + "\""));
            assertFalse(xml.contains("NovaBaseMapper"));
            assertFalse(xml.contains("@MapperScan"));
        }
    }

    @Test
    void logicalDeleteTablesShouldFilterDeleteFlagInCustomQueries() throws IOException {
        assertTrue(readMapperXml("SysUserMapper.xml").contains("delete_flag = 0"));
        assertTrue(readMapperXml("SysRoleMapper.xml").contains("delete_flag = 0"));
        assertTrue(readMapperXml("SysMenuMapper.xml").contains("delete_flag = 0"));
        assertTrue(readMapperXml("SysDictTypeMapper.xml").contains("delete_flag = 0"));
        assertTrue(readMapperXml("SysDictItemMapper.xml").contains("delete_flag = 0"));
        assertTrue(readMapperXml("SysConfigMapper.xml").contains("delete_flag = 0"));
    }

    private static String readMapperXml(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/mapper/system", fileName);
        if (!Files.exists(path)) {
            path = Paths.get("nova-data-access/src/main/resources/mapper/system", fileName);
        }
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
