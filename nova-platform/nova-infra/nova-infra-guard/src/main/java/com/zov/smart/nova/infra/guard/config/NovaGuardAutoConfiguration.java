package com.zov.smart.nova.infra.guard.config;

import com.zov.smart.nova.infra.guard.aspect.RepeatSubmitGuardAspect;
import com.zov.smart.nova.infra.guard.aspect.ResourceLockGuardAspect;
import com.zov.smart.nova.infra.guard.key.GuardKeyGenerator;
import com.zov.smart.nova.infra.guard.properties.NovaGuardProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Auto-configuration for Redis-backed Guard infrastructure.
 */
@Configuration
@EnableConfigurationProperties(NovaGuardProperties.class)
public class NovaGuardAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GuardKeyGenerator guardKeyGenerator() {
        return new GuardKeyGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "nova.guard", name = "repeat-submit-enabled", havingValue = "true", matchIfMissing = true)
    public RepeatSubmitGuardAspect repeatSubmitGuardAspect(StringRedisTemplate stringRedisTemplate,
                                                           GuardKeyGenerator guardKeyGenerator,
                                                           NovaGuardProperties properties) {
        return new RepeatSubmitGuardAspect(stringRedisTemplate, guardKeyGenerator, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "nova.guard", name = "resource-lock-enabled", havingValue = "true", matchIfMissing = true)
    public ResourceLockGuardAspect resourceLockGuardAspect(StringRedisTemplate stringRedisTemplate,
                                                           GuardKeyGenerator guardKeyGenerator,
                                                           NovaGuardProperties properties) {
        return new ResourceLockGuardAspect(stringRedisTemplate, guardKeyGenerator, properties);
    }
}
