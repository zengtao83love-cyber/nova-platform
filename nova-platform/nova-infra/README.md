# nova-infra

`nova-infra` is the runtime infrastructure aggregation module for `nova-platform`.

## Current SDD increment

Task `T-04` creates only the Maven aggregation skeleton and dependency boundaries. Java source files, auto-configuration classes, `spring.factories`, filters, aspects, services, and mapper XML files are intentionally deferred to later atomic implementation tasks.

## Created child modules

- `nova-infra-web`
- `nova-infra-redis`
- `nova-infra-mybatisplus`
- `nova-infra-security`
- `nova-infra-guard`
- `nova-infra-audit`

## Dependency boundary decisions

- `nova-infra-security` declares the required dependency on `nova-data-access` as specified, even though `nova-data-access` will be physically created in `T-05`.
- `nova-infra-audit` owns its future audit DO/Mapper/XML and does not depend on `nova-data-access`.
- `nova-infra-web`, `nova-infra-redis`, `nova-infra-mybatisplus`, and `nova-infra-guard` do not depend on `nova-data-access`.
- No `application.yml`, `application.properties`, or Java code is generated in this task.
