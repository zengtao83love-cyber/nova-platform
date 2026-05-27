package com.zov.smart.nova.infra.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditMapperContractTest {

    @Test
    void mapperDirectlyExtendsBaseMapper() {
        Type type = AuditOperationLogMapper.class.getGenericInterfaces()[0];
        ParameterizedType parameterizedType = (ParameterizedType) type;
        assertEquals(BaseMapper.class, parameterizedType.getRawType());
        assertEquals(AuditOperationLogDO.class, parameterizedType.getActualTypeArguments()[0]);
    }

    @Test
    void mapperXmlUsesCorrectNamespaceAndNoMapperScan() throws Exception {
        String xml = new String(Files.readAllBytes(Paths.get("src/main/resources/mapper/audit/AuditOperationLogMapper.xml")), StandardCharsets.UTF_8);
        assertTrue(xml.contains("com.zov.smart.nova.infra.audit.mapper.AuditOperationLogMapper"));
        assertTrue(xml.contains("audit_operation_log"));
        assertTrue(xml.contains("delete_flag = 0"));
        assertTrue(!xml.contains("MapperScan"));
    }
}
