package com.zov.smart.nova.infra.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import java.util.Collections;
import java.util.List;

/**
 * Swagger / Knife4j documentation auto-configuration.
 *
 * <p>The runtime security layer still controls real authentication. This class
 * only exposes the {@code Authorization} header in the generated Swagger
 * documentation so that operators can paste {@code Bearer <accessToken>} once
 * in Knife4j/Swagger and invoke protected APIs from the document page.</p>
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class NovaSwaggerAutoConfiguration {



    private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String API_BASE_PACKAGE = "com.zov.smart.nova";
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            // 全局添加鉴权参数
            if(openApi.getPaths()!=null){
                openApi.getPaths().forEach((s, pathItem) -> {
                    pathItem.readOperations().forEach(operation -> {
                        operation.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
                    });
                });
            }

        };
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XXX用户系统API")
                        .version("1.0")

                        .description( "Knife4j集成springdoc-openapi示例")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com"))
                )
        .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION,new SecurityScheme()
       .name(HttpHeaders.AUTHORIZATION).type(SecurityScheme.Type.HTTP).scheme("Bearer")));
    }

}
