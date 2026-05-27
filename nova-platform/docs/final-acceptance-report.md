# Nova Platform Final SDD Acceptance Report

## Scope

This report belongs to task batch **T-131~T-140** and records the final integration, boundary, forbidden-rule, packaging, and delivery status of the generated `nova-platform` project.

## Covered Acceptance Items

| Task | Acceptance Item | Status |
|---|---|---|
| T-131 | Auth integration contracts | Implemented as static JUnit contract tests |
| T-132 | RBAC integration contracts | Implemented as static JUnit contract tests |
| T-133 | User / Role / Menu integration contracts | Implemented as static JUnit contract tests |
| T-134 | Dict / Config / Enum integration contracts | Implemented as static JUnit contract tests |
| T-135 | Login log / Audit log integration contracts | Implemented as static JUnit contract tests |
| T-136 | Module dependency boundary checks | Implemented as JUnit contract tests and shell validation |
| T-137 | Forbidden rules checks | Implemented as JUnit contract tests and shell validation |
| T-138 | Maven full build acceptance | Maven command documented; not executed in this sandbox because `mvn` is unavailable |
| T-139 | Bootstrap startup acceptance | Startup command documented; not executed in this sandbox because external MySQL/Redis are unavailable |
| T-140 | Final delivery package | Completed |

## Manual Verification Commands

```bash
cd nova-platform
bash deploy/check/validate-deploy-files.sh
bash deploy/check/validate-final-acceptance.sh
mvn clean package
java -jar nova-bootstrap/target/nova-bootstrap.jar
```

## Important Notes

- `application.yml` exists only under `nova-bootstrap`.
- `@MapperScan` exists only in `NovaApplication`.
- `AuthController` exists only under `nova-infra-security`.
- `nova-data-access` does not depend on `nova-infra-*`.
- `nova-system-api` remains a pure contract module.
- `NovaBaseMapper`, `jakarta.*`, and `WebSecurityConfigurerAdapter` are forbidden and checked.
- `NOVA_SECURITY_JWT_SECRET` is the unified JWT secret environment variable.
