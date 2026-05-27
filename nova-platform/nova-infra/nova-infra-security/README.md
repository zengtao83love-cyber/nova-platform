# nova-infra-security

Security infrastructure for login/logout/current-user APIs, JWT and Redis session management, authentication filter, permission checks, security cache, and login log writing.

## Current SDD increment

T-04 creates only the Maven module skeleton and dependency boundary declarations. Java source files are intentionally deferred to later atomic implementation tasks.
This module intentionally declares `nova-data-access` because authentication and permission loading must read system tables. `nova-data-access` is created in `T-05`, so full reactor validation is deferred to `T-08`.



## T-61~T-78

本批次实现 nova-infra-security 认证鉴权运行时：NovaSecurityProperties、TokenService、LoginBruteForceGuard、TokenAuthenticationFilter、PermissionChecker、AuthService、AuthController 以及结构测试。
