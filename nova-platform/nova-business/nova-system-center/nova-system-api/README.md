# nova-system-api

`nova-system-api` is the stable system-management contract module.

## Allowed content

- Command objects
- Query objects
- DTO objects
- VO objects defined by the API schema matrix
- Facade interfaces
- System constants

## Dependency boundary

This module must not depend on:

- `nova-data-access`
- any `nova-infra-*` module
- `nova-system-biz`
- `spring-boot-starter-web`
- MyBatis / MyBatis-Plus starters

Validation annotations must use `javax.validation.*`, never `jakarta validation namespace`.

## T-79~T-90 Contract Layer Additions

This module now contains stable system-center API contracts only:

- command objects for user/role/menu/dict/config write operations
- query objects for user/role/menu/dict/config/log read operations
- DTO objects for cross-module data transfer
- stable VO objects required by the task blueprint and DTO matrix
- Facade interfaces for user, role, menu, permission, dictionary, configuration, log, and enum contracts
- constants and pure event payloads

Boundary rules remain unchanged: no Controller, ServiceImpl, Mapper, DO, MyBatis-Plus annotation,
Redis operation, Spring Web implementation, `nova-data-access`, or `nova-infra-*` dependency is allowed here.
