# nova-bootstrap

`nova-bootstrap` is the top-level runtime assembly module for `nova-platform`.

## Current SDD state

Created in task `T-07` as a Maven module skeleton only.

This task intentionally does not create:

- `NovaApplication.java`
- `application.yml`
- `logback-spring.xml`
- `@MapperScan`
- Java source code
- SQL or Docker configuration

Those artifacts belong to later dedicated tasks.

## Dependency boundary

This module assembles:

- `nova-system-biz`
- all current `nova-infra-*` runtime modules
- MySQL runtime driver

It must not contain business logic, data access implementation, or reusable infrastructure implementation.


## T-117~T-130 启动装配

`nova-bootstrap` 是唯一允许放置以下内容的模块：

- `@SpringBootApplication`
- `@MapperScan`
- `application.yml`
- `logback-spring.xml`

启动类：`com.zov.smart.nova.bootstrap.NovaApplication`。
