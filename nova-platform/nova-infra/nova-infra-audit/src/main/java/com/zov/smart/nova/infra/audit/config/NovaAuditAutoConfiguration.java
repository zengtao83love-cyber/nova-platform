package com.zov.smart.nova.infra.audit.config;

import com.zov.smart.nova.infra.audit.aspect.AuditAspect;
import com.zov.smart.nova.infra.audit.mapper.AuditOperationLogMapper;
import com.zov.smart.nova.infra.audit.service.AuditLogQueryService;
import com.zov.smart.nova.infra.audit.service.AuditLogService;
import com.zov.smart.nova.infra.audit.service.impl.AuditLogServiceImpl;
import com.zov.smart.nova.infra.audit.support.AuditPayloadMasker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Auto-configuration for audit logging infrastructure.
 *
 * <p>No MapperScan is declared here. The bootstrap module remains the only place
 * allowed to configure MapperScan.</p>
 */
@Configuration
@EnableAsync
public class NovaAuditAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditPayloadMasker auditPayloadMasker() {
        return new AuditPayloadMasker();
    }

    @Bean("auditAsyncExecutor")
    @ConditionalOnMissingBean(name = "auditAsyncExecutor")
    public Executor auditAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("nova-audit-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    @ConditionalOnMissingBean({AuditLogService.class, AuditLogQueryService.class})
    public AuditLogServiceImpl auditLogService(AuditOperationLogMapper auditOperationLogMapper) {
        return new AuditLogServiceImpl(auditOperationLogMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditAspect auditAspect(AuditLogService auditLogService, AuditPayloadMasker auditPayloadMasker) {
        return new AuditAspect(auditLogService, auditPayloadMasker);
    }
}
