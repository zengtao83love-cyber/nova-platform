package com.zov.smart.nova.infra.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.zov.smart.nova.infra.mybatisplus.properties.NovaMybatisPlusProperties;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NovaMybatisPlusInterceptorConfigurationTest {

    @Test
    void shouldRegisterThreeDefaultInterceptors() throws Exception {
        NovaMybatisPlusProperties properties = new NovaMybatisPlusProperties();
        MybatisPlusInterceptor interceptor = new NovaMybatisPlusInterceptorConfiguration().mybatisPlusInterceptor(properties);

        List<InnerInterceptor> innerInterceptors = innerInterceptors(interceptor);

        assertEquals(3, innerInterceptors.size());
        assertTrue(innerInterceptors.get(0).getClass().getSimpleName().contains("Pagination"));
        assertTrue(innerInterceptors.get(1).getClass().getSimpleName().contains("OptimisticLocker"));
        assertTrue(innerInterceptors.get(2).getClass().getSimpleName().contains("BlockAttack"));
    }

    @Test
    void shouldRespectDisabledPlugins() throws Exception {
        NovaMybatisPlusProperties properties = new NovaMybatisPlusProperties();
        properties.getPagination().setEnabled(false);
        properties.getOptimisticLocker().setEnabled(false);
        properties.getBlockAttack().setEnabled(false);

        MybatisPlusInterceptor interceptor = new NovaMybatisPlusInterceptorConfiguration().mybatisPlusInterceptor(properties);

        assertEquals(0, innerInterceptors(interceptor).size());
    }

    @SuppressWarnings("unchecked")
    private static List<InnerInterceptor> innerInterceptors(MybatisPlusInterceptor interceptor) throws Exception {
        Field field = MybatisPlusInterceptor.class.getDeclaredField("interceptors");
        field.setAccessible(true);
        return (List<InnerInterceptor>) field.get(interceptor);
    }
}
