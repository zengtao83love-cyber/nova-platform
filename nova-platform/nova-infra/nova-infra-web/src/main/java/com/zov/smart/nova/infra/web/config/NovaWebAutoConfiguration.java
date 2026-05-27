package com.zov.smart.nova.infra.web.config;

import com.zov.smart.nova.infra.web.filter.RequestIdFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web infrastructure auto-configuration for Spring Boot 2.7.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class NovaWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestIdFilter requestIdFilter() {
        return new RequestIdFilter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "novaRequestIdFilterRegistration")
    public FilterRegistrationBean<RequestIdFilter> novaRequestIdFilterRegistration(RequestIdFilter requestIdFilter) {
        FilterRegistrationBean<RequestIdFilter> registrationBean = new FilterRegistrationBean<RequestIdFilter>();
        registrationBean.setFilter(requestIdFilter);
        registrationBean.setName("novaRequestIdFilter");
        registrationBean.setOrder(requestIdFilter.getOrder());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
