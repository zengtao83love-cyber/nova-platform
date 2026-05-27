# nova-system-center

`nova-system-center` is the only business center generated in the current SDD scope.

It contains:

- `nova-system-api`: clean external contract module.
- `nova-system-biz`: system management implementation module.

Authentication APIs remain in `nova-infra-security`; this center does not contain `AuthController`.
