package com.zov.smart.nova.common.mybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class JacksonJsonTypeHandlerTest {
    @Test
    void shouldSerializeNonNullParameter() throws Exception {
        JacksonJsonTypeHandler handler = new JacksonJsonTypeHandler(Map.class);
        AtomicReference<String> jsonHolder = new AtomicReference<>();
        PreparedStatement ps = preparedStatementCapturingSetString(jsonHolder);

        Map<String, Object> value = new HashMap<>();
        value.put("enabled", true);
        handler.setNonNullParameter(ps, 1, value, JdbcType.VARCHAR);

        assertTrue(jsonHolder.get().contains("enabled"));
    }

    @Test
    void shouldDeserializeJsonColumn() throws Exception {
        JacksonJsonTypeHandler handler = new JacksonJsonTypeHandler(Map.class);
        ResultSet rs = resultSetReturningJson("{\"name\":\"nova\"}");

        Object result = handler.getNullableResult(rs, "payload");

        assertTrue(result instanceof Map);
        assertEquals("nova", ((Map<?, ?>) result).get("name"));
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(IllegalArgumentException.class, new org.junit.jupiter.api.function.Executable() {
            @Override
            public void execute() {
                new JacksonJsonTypeHandler(null);
            }
        });
    }

    private static PreparedStatement preparedStatementCapturingSetString(final AtomicReference<String> jsonHolder) {
        return (PreparedStatement) Proxy.newProxyInstance(
                JacksonJsonTypeHandlerTest.class.getClassLoader(),
                new Class<?>[]{PreparedStatement.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if ("setString".equals(method.getName())) {
                            jsonHolder.set((String) args[1]);
                            return null;
                        }
                        return defaultValue(method.getReturnType());
                    }
                });
    }

    private static ResultSet resultSetReturningJson(final String json) {
        return (ResultSet) Proxy.newProxyInstance(
                JacksonJsonTypeHandlerTest.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if ("getString".equals(method.getName())) {
                            return json;
                        }
                        return defaultValue(method.getReturnType());
                    }
                });
    }

    private static Object defaultValue(Class<?> returnType) {
        if (returnType == Boolean.TYPE) {
            return false;
        }
        if (returnType == Byte.TYPE || returnType == Short.TYPE || returnType == Integer.TYPE) {
            return 0;
        }
        if (returnType == Long.TYPE) {
            return 0L;
        }
        if (returnType == Float.TYPE) {
            return 0F;
        }
        if (returnType == Double.TYPE) {
            return 0D;
        }
        return null;
    }
}
