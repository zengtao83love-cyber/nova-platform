package com.zov.smart.nova.infra.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zov.smart.nova.infra.mybatisplus.properties.NovaMybatisPlusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the MyBatis-Plus runtime interceptor chain.
 *
 * <p>DbType is explicitly locked to MySQL to match the system database baseline and to
 * avoid runtime dialect guessing. Interceptor registration is driven only by generic
 * infra properties and never references business DO or Mapper classes.</p>
 */
@Configuration
public class NovaMybatisPlusInterceptorConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(NovaMybatisPlusProperties properties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        if (properties.getPagination().isEnabled()) {
            PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
            paginationInterceptor.setMaxLimit(properties.getPagination().getMaxLimit());
            paginationInterceptor.setOverflow(properties.getPagination().isOverflow());
            interceptor.addInnerInterceptor(paginationInterceptor);
        }

        if (properties.getOptimisticLocker().isEnabled()) {
            interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        }

        if (properties.getBlockAttack().isEnabled()) {
            interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        }

        return interceptor;
    }
}
