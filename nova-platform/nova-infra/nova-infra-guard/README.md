# nova-infra-guard

Guard infrastructure for Redis-backed repeat-submit and resource-lock protection.

## Current SDD increment: T-53~T-56

Implemented:

- `@RepeatSubmitGuard`
- `@ResourceLockGuard`
- `RepeatSubmitGuardAspect`
- `ResourceLockGuardAspect`
- `GuardKeyGenerator`
- `GuardErrorCode`
- `NovaGuardAutoConfiguration`

Rules:

- Uses Redis `SET NX EX` semantics.
- Does not depend on `nova-data-access`.
- Does not replace database unique constraints.
- Does not write raw arguments into Redis keys; argument data is hashed.
