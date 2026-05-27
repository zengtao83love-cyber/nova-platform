# nova-common

`nova-common` is the lowest-level common module aggregation. It contains only pure common contracts and utilities.

## SDD boundaries

- No `application.yml` or Spring Boot auto-configuration.
- No DataSource, Redis, Controller, Service, or business logic.
- No dependency on `nova-infra-*`, `nova-data-access`, `nova-business`, or `nova-bootstrap`.
- `nova-common-mybatis` contains only MyBatis compile-time contracts and does not create a custom base mapper.

## Modules

- `nova-common-core`
- `nova-common-model`
- `nova-common-context`
- `nova-common-event`
- `nova-common-util`
- `nova-common-mybatis`
- `nova-common-test`
