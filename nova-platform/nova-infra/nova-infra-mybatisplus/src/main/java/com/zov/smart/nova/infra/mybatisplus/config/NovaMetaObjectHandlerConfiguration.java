package com.zov.smart.nova.infra.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zov.smart.nova.infra.mybatisplus.handler.NovaMetaObjectHandler;
import com.zov.smart.nova.infra.mybatisplus.properties.NovaMybatisPlusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the Nova audit-field auto fill handler.
 */
@Configuration
public class NovaMetaObjectHandlerConfiguration {

    @Bean
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    @ConditionalOnProperty(prefix = "nova.mybatis-plus.audit-fill", name = "enabled", havingValue = "true", matchIfMissing = true)
    public NovaMetaObjectHandler novaMetaObjectHandler(NovaMybatisPlusProperties properties) {
        return new NovaMetaObjectHandler();
    }
}
