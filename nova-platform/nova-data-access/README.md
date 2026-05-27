# nova-data-access

`nova-data-access` is the system table persistence module for nova-platform.

## Current SDD increment

T-05 creates only the Maven module skeleton and dependency boundary declaration. Java source files, DO classes, Mapper interfaces, Mapper XML files, and persistence enums are intentionally deferred to later atomic implementation tasks.

## Scope from specification

Current allowed domain under this module:

```text
system
```

Future implementation tasks will place only the following artifact categories here:

```text
DO
Mapper
Mapper XML
persistence enums
```

## Dependency boundary

Allowed dependencies in this module:

- `nova-common-core`
- `nova-common-model`
- `nova-common-mybatis`

Forbidden dependencies in this module:

- any `nova-infra-*` module
- `nova-system-api`
- `nova-system-biz`
- `nova-bootstrap`

`nova-data-access` must remain a pure persistence module. Runtime MyBatis-Plus configuration belongs to `nova-infra-mybatisplus`, not here.

## SDD Progress - T-25~T-30

This merged batch adds persistence enums and the first five system persistence objects:

- `SysUserDO`
- `SysRoleDO`
- `SysMenuDO`
- `SysUserRoleDO`
- `SysRoleMenuDO`

Relationship entities intentionally mirror their database tables and therefore only contain `id`, relationship fields, and `createdAt`.
Mapper interfaces and XML remain deferred to later tasks.


## T-30~T-36 data-access completion

This module now contains the full system persistence entity and Mapper skeleton required by the current SDD scope:

- DO: users, roles, menus, user-role relations, role-menu relations, dictionary types, dictionary items, configs, login logs.
- Mapper: all nine system table mappers directly extend MyBatis-Plus `BaseMapper<T>`.
- XML: all Mapper XML files are placed only under `src/main/resources/mapper/system`.

No runtime configuration or `@MapperScan` is declared in this module. Mapper scanning remains reserved for `nova-bootstrap`.
