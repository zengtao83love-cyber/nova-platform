#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT_DIR"

fail() { echo "[FAIL] $1" >&2; exit 1; }
ok() { echo "[OK] $1"; }

[ -f pom.xml ] || fail "root pom.xml missing"
[ -f nova-bootstrap/src/main/java/com/zov/smart/nova/bootstrap/NovaApplication.java ] || fail "NovaApplication missing"
[ -f nova-bootstrap/src/main/resources/application.yml ] || fail "application.yml missing in bootstrap"
[ "$(find . -name application.yml | wc -l | tr -d ' ')" = "1" ] || fail "application.yml must exist only once"
[ "$(find . -path '*/src/main/java/*' -name '*.java' -print0 | xargs -0 grep -l "@MapperScan" | wc -l | tr -d ' ')" = "1" ] || fail "@MapperScan must exist in exactly one main Java file"
! find . -path '*/src/main/java/*' -name '*.java' -print0 | xargs -0 grep -n "jakarta\." >/tmp/nova_jakarta_hits.txt || fail "jakarta namespace is forbidden"
! find . -path '*/src/main/java/*' -name '*.java' -print0 | xargs -0 grep -n "NovaBaseMapper" >/tmp/nova_base_mapper_hits.txt || fail "NovaBaseMapper is forbidden"
! find . -path '*/src/main/java/*' -name '*.java' -print0 | xargs -0 grep -n "WebSecurityConfigurerAdapter" >/tmp/nova_security_adapter_hits.txt || fail "WebSecurityConfigurerAdapter is forbidden"
[ -f deploy/sql/001_schema_system.sql ] || fail "system schema missing"
[ -f deploy/sql/002_schema_audit.sql ] || fail "audit schema missing"
[ -f deploy/sql/003_init_admin.sql ] || fail "admin init sql missing"
[ -f deploy/docker/docker-compose.yml ] || fail "docker-compose missing"
grep -R "NOVA_SECURITY_JWT_SECRET" -n nova-bootstrap/src/main/resources/application.yml deploy/docker/.env.example >/dev/null || fail "JWT env var not unified"
grep -R "%X{traceId" -n nova-bootstrap/src/main/resources/logback-spring.xml >/dev/null || fail "logback traceId missing"

echo "All final SDD acceptance checks passed."
