# nova-system-biz

`nova-system-biz` implements system management business features.

## Responsibility

- User management
- Role management
- Menu and permission management
- Dictionary management
- System config management
- Login log query
- Operation audit log query
- Enum query

## Explicit exclusions

- No `AuthController`
- No login main flow implementation
- No finance, AI, file, notification, job, workflow, MQ, OSS, search, vector, plugin, generator, or multi-tenant module code

## Layering rule

HTTP calls must follow:

```text
Controller -> BizImpl -> ServiceImpl -> Mapper
```

Facade calls must follow:

```text
Other module -> nova-system-api Facade -> nova-system-biz FacadeImpl
```

## T-91~T-102 Increment

Implemented user and role management business layer:

- MapStruct converter contracts for User and Role.
- Atomic Service layer for user/role persistence.
- Transactional Biz layer for user/role orchestration.
- UserController and RoleController REST endpoints under `/api/system`.
- UserFacadeImpl and RoleFacadeImpl with no Mapper access.
- System business error codes from `23_ERROR_CODE_SPEC.md`.
- Post-commit cache invalidation for user-role and role-menu changes.

Boundary guarantees:

- Controller -> Biz -> Service -> Mapper.
- BizImpl does not import Mapper.
- FacadeImpl does not import Mapper.
- AuthController is not generated in nova-system-biz.
- MapperScan and application.yml are not generated here.
