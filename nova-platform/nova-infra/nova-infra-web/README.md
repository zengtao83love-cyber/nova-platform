# nova-infra-web

Web infrastructure for unified exception handling, response wrapping, Jackson/WebMvc/OpenAPI/CORS configuration, RequestId filtering, and request support.

## Current SDD increment

T-04 creates only the Maven module skeleton and dependency boundary declarations. Java source files are intentionally deferred to later atomic implementation tasks.


## T-47~T-50 已完成

- `RequestIdFilter`：统一 `X-Request-Id` 与 MDC `traceId`。
- `GlobalExceptionHandler`：按 ErrorCode 矩阵返回 HTTP 状态与 Result.code。
- 通过 `META-INF/spring.factories` 注册 `NovaWebAutoConfiguration`。
- 当前模块仍不依赖 `nova-data-access`、不包含 Controller/Service/Biz。
