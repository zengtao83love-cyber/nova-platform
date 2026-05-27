package com.zov.smart.nova.infra.mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.zov.smart.nova.infra.mybatisplus.handler.NovaMetaObjectHandler;
import com.zov.smart.nova.infra.mybatisplus.properties.NovaMybatisPlusProperties;
import com.zov.smart.nova.infra.mybatisplus.transaction.TransactionHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * MyBatis-Plus infrastructure auto-configuration entry.
 *
 * <p>Registered through {@code META-INF/spring.factories} for Spring Boot 2.7.x.
 * This class must not declare mapper scanning; Mapper scanning belongs only to
 * {@code nova-bootstrap}.</p>
 */
@Configuration
@ConditionalOnClass(MybatisPlusInterceptor.class)
@EnableConfigurationProperties(NovaMybatisPlusProperties.class)
@Import({
        NovaMybatisPlusInterceptorConfiguration.class,
        NovaMetaObjectHandlerConfiguration.class,
        NovaTransactionConfiguration.class
})
public class NovaMybatisPlusAutoConfiguration {
}
