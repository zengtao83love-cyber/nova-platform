# nova-platform

`nova-platform` is a Spring Boot 2.7.x + JDK 8 monolithic multi-module backend management framework generated under strict SDD rules.

## Current generation state

This repository is currently at task `T-07`: root project skeleton plus `nova-bom`, `nova-common`, `nova-infra`, `nova-data-access`, `nova-business/nova-system-center`, and `nova-bootstrap` Maven module skeletons.

Created so far:

```text
nova-platform/
├─ pom.xml
├─ file_manifest.json
├─ README.md
├─ nova-bom/
├─ nova-common/
├─ nova-infra/
├─ nova-data-access/
├─ nova-business/
│  └─ nova-system-center/
│     ├─ nova-system-api/
│     └─ nova-system-biz/
├─ nova-bootstrap/
├─ docs/
└─ deploy/
```

All currently created top-level Maven modules are declared in the root `<modules>` list. `docs` and `deploy` remain non-Maven directories.

Created child Maven modules through T-07:

```text
T-03 nova-common ✅
T-04 nova-infra ✅
T-05 nova-data-access ✅
T-06 nova-business / nova-system-center ✅
T-07 nova-bootstrap ✅
```

## Hard scope

The current system scope is a backend management framework containing authentication, RBAC, users, roles, menus, dictionaries, system configs, login logs, audit logs, Redis cache, MyBatis-Plus data access, unified response/error handling, local Docker environment, and Logback configuration.

The current stage must not generate finance, AI, workflow, MQ, OSS, search, vector, plugin, generator, or multi-tenant modules.

## Current SDD state after T-03

Created `nova-common` aggregation and its seven common child modules. No Java source code has been generated in this task.

Created modules:

- `nova-common/nova-common-core`
- `nova-common/nova-common-model`
- `nova-common/nova-common-context`
- `nova-common/nova-common-event`
- `nova-common/nova-common-util`
- `nova-common/nova-common-mybatis`
- `nova-common/nova-common-test`



## Current SDD state after T-04

Created `nova-infra` aggregation and its six infrastructure child modules. No Java source code, resource auto-configuration files, or YAML/property configuration files have been generated in this task.

Created modules:

- `nova-infra/nova-infra-web`
- `nova-infra/nova-infra-redis`
- `nova-infra/nova-infra-mybatisplus`
- `nova-infra/nova-infra-security`
- `nova-infra/nova-infra-guard`
- `nova-infra/nova-infra-audit`


## Current SDD state after T-05

Created `nova-data-access` as the system persistence Maven module skeleton. No Java source code, Mapper XML, entity classes, runtime configuration, SQL, or YAML/property configuration files have been generated in this task.

Created module:

- `nova-data-access`


## Current SDD state after T-06

Created `nova-business` aggregation and the only allowed current business center `nova-system-center`. No Java source code, Controller, Service, Mapper, runtime configuration, SQL, or YAML/property configuration files have been generated in this task.

Created modules:

- `nova-business`
- `nova-business/nova-system-center`
- `nova-business/nova-system-center/nova-system-api`
- `nova-business/nova-system-center/nova-system-biz`


## Current SDD state after T-07

Created `nova-bootstrap` as the top-level runtime assembly Maven module skeleton. No Java source code, startup class, `application.yml`, `logback-spring.xml`, `@MapperScan`, SQL, Docker configuration, or runtime implementation has been generated in this task.

Created module:

- `nova-bootstrap`


## T-09~T-16 合并批次状态

本批次按用户指令跳过 T-08，并合并执行 T-09 至 T-16：

- `nova-common-core` 新增统一响应模型、错误码契约、通用错误码、认证错误码、业务异常。
- `nova-common-model` 新增分页入参与分页结果模型。
- 对应新增 JUnit 5 单元测试。
- 本批次未引入 Spring IoC 注解、Controller、Service、DataSource、Redis、YAML 或业务模块代码。


## T-17~T-24 合并批次变更摘要

本批次在 nova-common 范围内新增：

- `LoginUserContext` / `CurrentUser` / `LoginUser`：当前登录用户线程上下文与会话快照。
- `TraceContext` / `RequestIdHolder`：TraceId 与 `X-Request-Id` 统一入口。
- `BaseDO` / `DbEnum`：MyBatis 编译期契约，保持 DO 继承基类与业务枚举字符串 code 规则。
- `JacksonJsonTypeHandler` / `EnumCodeTypeHandler`：JSON 字段与 DbEnum 字段持久化处理器。
- `JsonUtils` / `MaskUtils` / `IpUtils` / `ServletUtils`：纯工具能力，不包含 Spring Bean 与业务逻辑。

本批次未创建业务模块代码、未创建配置文件、未创建自定义 BaseMapper。

## SDD Progress - T-25~T-30

Completed merged batch T-25 through T-30 by explicit user instruction.

Scope completed:

- System persistence enums in `nova-data-access`.
- `SysUserDO`, `SysRoleDO`, `SysMenuDO`.
- `SysUserRoleDO`, `SysRoleMenuDO` relationship entities.
- Reflection-based contract tests for enum codes, table names, column mappings, enum type handlers, and BUTTON permission-code structure.

Important boundary decision:

- Full system entities inherit `BaseDO`.
- Relationship entities do not inherit `BaseDO` because `16_DATABASE_SCHEMA_SPEC.md` defines `sys_user_role` and `sys_role_menu` without full audit columns.
- Mapper interfaces and XML are intentionally deferred to later tasks.


## SDD Progress - T-30~T-36

Completed merged batch requested as T-30 through T-36. Since T-30 had already been completed in the previous batch, this batch added the T-31 through T-36 deliverables:

- `SysDictTypeDO`, `SysDictItemDO`, `SysConfigDO`, `SysLoginLogDO`.
- All system Mapper interfaces under `nova-data-access/.../system/mapper`.
- Mapper XML files under `src/main/resources/mapper/system`.
- Contract tests for entity mappings, direct `BaseMapper<T>` inheritance, custom Mapper method presence, and XML namespace/path constraints.

Important boundary decision:

- `SysLoginLogDO` does not inherit `BaseDO` because `16_DATABASE_SCHEMA_SPEC.md` defines `sys_login_log` without `updated_at`, `delete_flag`, and `version`. This follows the same database-schema truth-source policy used for relation entities.
- No `MapperScan`, configuration class, Controller, Service, Biz, Facade, Repository, Redis operation, security logic, or business transaction was generated.


## SDD 执行进度：T-37~T-46

已合并完成 nova-infra-mybatisplus 运行时基础设施批次：配置属性、自动装配、MyBatis-Plus 拦截器链、MetaObjectHandler 自动填充、TransactionHelper、分页转换器及对应测试。当前批次未创建任何业务 Controller/Service/Mapper/DO，未创建 application.yml，未依赖 nova-data-access。


## SDD 增量记录：T-47~T-52

已合并完成 nova-infra-web 与 nova-infra-redis 的基础设施批次：

- RequestIdFilter：统一 `X-Request-Id`，写入 TraceContext 与 MDC，响应头透传，finally 清理。
- GlobalExceptionHandler：统一处理 BusinessException、参数校验、404、未知异常。
- RedisAutoConfiguration：String key + JSON value，禁止 Jackson 宽泛多态默认类型。
- NovaRedisJsonSerializer：提供显式类型 JSON 序列化，供后续 TokenSession 等对象可靠反序列化。

本批次未实现认证、鉴权、Redis 会话、Controller、Service、Biz、Mapper 或配置文件。


## SDD 增量记录：T-53~T-60

已合并完成 nova-infra-guard 与 nova-infra-audit 批次：

- Guard：新增 `RepeatSubmitGuard`、`ResourceLockGuard`、Redis `SET NX EX` AOP、Guard 错误码、Key 生成器与自动装配。
- Audit：新增 `OperationLog`、`AuditOperationTypeEnum`、`AuditOperationLogDO/Mapper/XML`、审计脱敏器、`AuditAspect`、异步独立事务落库服务、审计查询服务与自动装配。
- 审计异步策略：主线程采集用户、Trace、HTTP、参数/响应摘要；异步线程只接收完整 `AuditOperationLogDO` 快照，不读取 `LoginUserContext`。
- 本批次未创建 `MapperScan`、业务 Controller/Service/Biz、系统 API DTO、application.yml 或 bootstrap 配置。


## T-61~T-78 Security Runtime Batch

已完成安全模块配置、双 Token、Redis 会话、登录防暴破、认证过滤器、权限注解/AOP、AuthService 与 AuthController。


## T-79~T-90

Added nova-system-api contract objects, Facade interfaces, constants, pure event payload, and contract/boundary tests. No system-biz implementation was generated in this batch.


## T-91~T-102

User/Role management business layer completed: converters, services, biz orchestration, controllers, facade implementations, and contract tests.


## T-103~T-116

已完成 Menu / Dict / Config / Log / Enum 的 system-biz 闭环实现，并补齐 MenuFacade、PermissionFacade、DictFacade、ConfigFacade、LogFacade、EnumFacade 的实现。


## SDD T-117~T-130 增量

本批次补齐启动与部署层：

- `NovaApplication` 与唯一 `@MapperScan`
- `nova-bootstrap/src/main/resources/application.yml`
- `nova-bootstrap/src/main/resources/logback-spring.xml`
- 系统表与审计表 DDL
- admin / RBAC / 字典 / 参数初始化 SQL
- Docker Compose 本地 MySQL + Redis 环境
- 部署文件校验脚本 `deploy/check/validate-deploy-files.sh`

本地默认账号：`admin / Admin@123456`。生产环境必须修改默认密码并注入 `NOVA_SECURITY_JWT_SECRET`。


## T-131~T-140 Final Acceptance

Final integration and delivery artifacts have been added under:

- `nova-bootstrap/src/test/java/com/zov/smart/nova/bootstrap/integration`
- `nova-bootstrap/src/test/java/com/zov/smart/nova/bootstrap/architecture`
- `deploy/check/validate-final-acceptance.sh`
- `docs/final-acceptance-report.md`

Run:

```bash
bash deploy/check/validate-deploy-files.sh
bash deploy/check/validate-final-acceptance.sh
mvn clean package
```
