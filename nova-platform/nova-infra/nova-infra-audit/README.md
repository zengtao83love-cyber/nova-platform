# nova-infra-audit

Audit infrastructure for operation log annotation/AOP, audit DO/Mapper/XML ownership, independent persistence, masking, and audit query support.

## Current SDD increment: T-57~T-60

Implemented:

- `@OperationLog`
- `AuditOperationTypeEnum`
- `AuditOperationLogDO`
- `AuditOperationLogMapper`
- `AuditOperationLogMapper.xml`
- `AuditPayloadMasker`
- `AuditAspect`
- `AuditLogService` / `AuditLogQueryService`
- `AuditLogServiceImpl`
- `NovaAuditAutoConfiguration`

Rules:

- `nova-infra-audit` owns audit DO/Mapper/XML and does not depend on `nova-data-access`.
- `asyncSaveLog` uses `@Async("auditAsyncExecutor")` and `@Transactional(REQUIRES_NEW)`.
- Audit failures are logged and never block the main business operation.
- Sensitive payload fields such as password, token, accessToken, refreshToken, and Authorization are masked before persistence.
- This module does not declare `@MapperScan`; bootstrap remains the only allowed MapperScan owner.
