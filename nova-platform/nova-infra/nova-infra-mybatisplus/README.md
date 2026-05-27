# nova-infra-mybatisplus

MyBatis-Plus runtime infrastructure for pagination, optimistic lock, block attack protection, meta object fill, transaction helpers, and page conversion.

## Current SDD increment

T-04 creates only the Maven module skeleton and dependency boundary declarations. Java source files are intentionally deferred to later atomic implementation tasks.


## T-37~T-46 已实现范围

- `NovaMybatisPlusProperties`：绑定 `nova.mybatis-plus.*`。
- `NovaMybatisPlusAutoConfiguration`：Spring Boot 2.7 `spring.factories` 自动装配入口。
- `NovaMybatisPlusInterceptorConfiguration`：分页、乐观锁、防全表更新/删除插件链。
- `NovaMetaObjectHandler`：审计字段自动填充，缺少登录上下文时使用系统用户 `0L`。
- `MybatisPageHelper` / `PageConvertor`：统一分页入参和出参转换。
- `TransactionHelper`：编程式事务和 afterCommit 辅助。

本模块仍严格禁止：`BaseDO`、`NovaBaseMapper`、`Sys*DO`、`Sys*Mapper`、业务 XML、`@MapperScan`、`application.yml`。
