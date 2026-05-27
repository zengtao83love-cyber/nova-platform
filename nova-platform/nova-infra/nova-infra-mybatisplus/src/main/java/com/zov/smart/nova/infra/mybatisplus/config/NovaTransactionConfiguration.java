package com.zov.smart.nova.infra.mybatisplus.config;

import com.zov.smart.nova.infra.mybatisplus.transaction.TransactionHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Programmatic transaction helper configuration.
 */
@Configuration
public class NovaTransactionConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(PlatformTransactionManager.class)
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(TransactionTemplate.class)
    public TransactionHelper transactionHelper(TransactionTemplate transactionTemplate) {
        return new TransactionHelper(transactionTemplate);
    }
}
