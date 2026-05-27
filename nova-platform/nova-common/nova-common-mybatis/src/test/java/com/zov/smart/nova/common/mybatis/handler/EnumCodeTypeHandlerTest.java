package com.zov.smart.nova.common.mybatis.handler;

import com.zov.smart.nova.common.mybatis.enums.DbEnum;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EnumCodeTypeHandlerTest {
    @Test
    void shouldPersistEnumCode() throws Exception {
        EnumCodeTypeHandler<DemoStatus> handler = new EnumCodeTypeHandler<>(DemoStatus.class);
        AtomicReference<String> codeHolder = new AtomicReference<>();
        PreparedStatement ps = preparedStatementCapturingSetString(codeHolder);

        handler.setNonNullParameter(ps, 1, DemoStatus.ENABLE, JdbcType.VARCHAR);

        assertEquals("ENABLE", codeHolder.get());
    }

    @Test
    void shouldConvertCodeToEnum() throws Exception {
        EnumCodeTypeHandler<DemoStatus> handler = new EnumCodeTypeHandler<>(DemoStatus.class);
        ResultSet rs = resultSetReturningString("DISABLE");

        assertEquals(DemoStatus.DISABLE, handler.getNullableResult(rs, "status"));
    }

    @Test
    void shouldReturnNullWhenCodeUnknown() throws Exception {
        EnumCodeTypeHandler<DemoStatus> handler = new EnumCodeTypeHandler<>(DemoStatus.class);
        ResultSet rs = resultSetReturningString("UNKNOWN");

        assertNull(handler.getNullableResult(rs, "status"));
    }

    private enum DemoStatus implements DbEnum {
        ENABLE("ENABLE", "启用"),
        DISABLE("DISABLE", "禁用");

        private final String code;
        private final String label;

        DemoStatus(String code, String label) {
            this.code = code;
            this.label = label;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getLabel() {
            return label;
        }
    }

    private static PreparedStatement preparedStatementCapturingSetString(final AtomicReference<String> holder) {
        return (PreparedStatement) Proxy.newProxyInstance(
                EnumCodeTypeHandlerTest.class.getClassLoader(),
                new Class<?>[]{PreparedStatement.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if ("setString".equals(method.getName())) {
                            holder.set((String) args[1]);
                        }
                        return null;
                    }
                });
    }

    private static ResultSet resultSetReturningString(final String value) {
        return (ResultSet) Proxy.newProxyInstance(
                EnumCodeTypeHandlerTest.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if ("getString".equals(method.getName())) {
                            return value;
                        }
                        return null;
                    }
                });
    }
}
