package com.zov.smart.nova.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * nova-platform runtime bootstrap.
 *
 * <p>SDD boundary rules:</p>
 * <ul>
 *   <li>{@code @SpringBootApplication} exists only in {@code nova-bootstrap}.</li>
 *   <li>{@code @MapperScan} exists only in {@code nova-bootstrap}.</li>
 *   <li>{@code application.yml} exists only in {@code nova-bootstrap/src/main/resources}.</li>
 * </ul>
 */
@SpringBootApplication(scanBasePackages = "com.zov.smart.nova")
@MapperScan(basePackages = {
        "com.zov.smart.nova.data.access.system.mapper",
        "com.zov.smart.nova.infra.audit.mapper"
})
public class NovaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaApplication.class, args);
    }
}
