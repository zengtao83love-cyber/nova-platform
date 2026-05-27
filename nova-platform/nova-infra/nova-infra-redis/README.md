# nova-infra-redis

Redis infrastructure for RedisTemplate, StringRedisTemplate, JSON serialization, CacheManager, key prefix rules, and low-level Redis helper components.

## Current SDD increment

T-04 creates only the Maven module skeleton and dependency boundary declarations. Java source files are intentionally deferred to later atomic implementation tasks.


## T-51~T-52 已完成

- `RedisAutoConfiguration`：注册 String key + Jackson JSON value 的 RedisTemplate。
- `NovaRedisJsonSerializer`：提供显式类型 JSON 序列化，避免宽泛多态反序列化。
- 通过 `META-INF/spring.factories` 注册自动装配。
- 当前模块未实现安全 Token Session 业务，后续由 nova-infra-security 使用。
