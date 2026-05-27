package com.zov.smart.nova.infra.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zov.smart.nova.data.access.system.mapper.SysLoginLogMapper;
import com.zov.smart.nova.data.access.system.mapper.SysMenuMapper;
import com.zov.smart.nova.data.access.system.mapper.SysRoleMapper;
import com.zov.smart.nova.data.access.system.mapper.SysUserMapper;
import com.zov.smart.nova.infra.security.brute.LoginBruteForceGuard;
import com.zov.smart.nova.infra.security.filter.TokenAuthenticationFilter;
import com.zov.smart.nova.infra.security.handler.AccessDeniedHandlerImpl;
import com.zov.smart.nova.infra.security.handler.AuthenticationEntryPointHandler;
import com.zov.smart.nova.infra.security.permission.PermissionAspect;
import com.zov.smart.nova.infra.security.permission.PermissionChecker;
import com.zov.smart.nova.infra.security.properties.NovaSecurityProperties;
import com.zov.smart.nova.infra.security.service.*;
import com.zov.smart.nova.infra.security.service.impl.SecurityCacheServiceImpl;
import com.zov.smart.nova.infra.security.service.impl.TokenServiceImpl;
import com.zov.smart.nova.infra.security.token.AccessTokenCacheStore;
import com.zov.smart.nova.infra.security.token.RefreshTokenCacheStore;
import com.zov.smart.nova.infra.security.token.TokenSessionSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Spring Boot 2.7 security auto-configuration. */
@Configuration
@EnableConfigurationProperties(NovaSecurityProperties.class)
public class NovaSecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    @ConditionalOnMissingBean
    public TokenSessionSerializer tokenSessionSerializer(ObjectMapper objectMapper) { return new TokenSessionSerializer(objectMapper); }

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenCacheStore accessTokenCacheStore(StringRedisTemplate redisTemplate, TokenSessionSerializer serializer) { return new AccessTokenCacheStore(redisTemplate, serializer); }

    @Bean
    @ConditionalOnMissingBean
    public RefreshTokenCacheStore refreshTokenCacheStore(StringRedisTemplate redisTemplate, TokenSessionSerializer serializer) { return new RefreshTokenCacheStore(redisTemplate, serializer); }

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService(StringRedisTemplate redisTemplate, AccessTokenCacheStore accessStore, RefreshTokenCacheStore refreshStore, NovaSecurityProperties properties) {
        return new TokenServiceImpl(redisTemplate, accessStore, refreshStore, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginBruteForceGuard loginBruteForceGuard(StringRedisTemplate redisTemplate, NovaSecurityProperties properties) { return new LoginBruteForceGuard(redisTemplate, properties); }

    @Bean
    @ConditionalOnMissingBean
    public SecurityUserService securityUserService(SysUserMapper userMapper, SysRoleMapper roleMapper, SysMenuMapper menuMapper) { return new SecurityUserService(userMapper, roleMapper, menuMapper); }

    @Bean
    @ConditionalOnMissingBean
    public SecurityLoginLogService securityLoginLogService(SysLoginLogMapper mapper) { return new SecurityLoginLogService(mapper); }

    @Bean
    @ConditionalOnMissingBean
    public AuthService authService(SecurityUserService securityUserService, TokenService tokenService, LoginBruteForceGuard bruteForceGuard,
                                   SecurityLoginLogService loginLogService, SysUserMapper sysUserMapper, PasswordEncoder passwordEncoder,
                                   NovaSecurityProperties properties) {
        return new AuthService(securityUserService, tokenService, bruteForceGuard, loginLogService, sysUserMapper, passwordEncoder, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityCacheService securityCacheService(StringRedisTemplate redisTemplate, TokenService tokenService) { return new SecurityCacheServiceImpl(redisTemplate, tokenService); }

    @Bean
    @ConditionalOnMissingBean
    public PermissionChecker permissionChecker(StringRedisTemplate redisTemplate, SecurityUserService securityUserService, NovaSecurityProperties properties) {
        return new PermissionChecker(redisTemplate, securityUserService, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionAspect permissionAspect(PermissionChecker permissionChecker) { return new PermissionAspect(permissionChecker); }

    @Bean
    @ConditionalOnMissingBean
    public TokenAuthenticationFilter tokenAuthenticationFilter(TokenService tokenService, NovaSecurityProperties properties) { return new TokenAuthenticationFilter(tokenService, properties); }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPointHandler authenticationEntryPointHandler(ObjectMapper objectMapper) { return new AuthenticationEntryPointHandler(objectMapper); }

    @Bean
    @ConditionalOnMissingBean
    public AccessDeniedHandlerImpl accessDeniedHandler(ObjectMapper objectMapper) { return new AccessDeniedHandlerImpl(objectMapper); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenAuthenticationFilter,
                                                   AuthenticationEntryPointHandler authenticationEntryPointHandler,
                                                   AccessDeniedHandlerImpl accessDeniedHandler,
                                                   NovaSecurityProperties properties) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(properties.getIgnoreUrlsArray()).permitAll()
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPointHandler)
                    .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
