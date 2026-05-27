#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"

required_files=(
  "nova-bootstrap/src/main/java/com/zov/smart/nova/bootstrap/NovaApplication.java"
  "nova-bootstrap/src/main/resources/application.yml"
  "nova-bootstrap/src/main/resources/logback-spring.xml"
  "deploy/sql/001_schema_system.sql"
  "deploy/sql/002_schema_audit.sql"
  "deploy/sql/003_init_admin.sql"
  "deploy/sql/004_init_rbac.sql"
  "deploy/sql/005_init_dict_config.sql"
  "deploy/docker/docker-compose.yml"
)

for file in "${required_files[@]}"; do
  test -f "$ROOT_DIR/$file" || { echo "missing required file: $file" >&2; exit 1; }
done

if find "$ROOT_DIR" -path "$ROOT_DIR/nova-bootstrap/src/main/resources/application.yml" -prune -o \
  \( -name 'application.yml' -o -name 'bootstrap.yml' -o -name 'application.properties' -o -name 'bootstrap.properties' \) -print | grep -q .; then
  echo "configuration file found outside nova-bootstrap" >&2
  exit 1
fi

mapper_scan_count=$(grep -R "^@MapperScan" "$ROOT_DIR" --include='*.java' | wc -l | tr -d ' ')
if [ "$mapper_scan_count" != "1" ]; then
  echo "@MapperScan must appear exactly once in nova-bootstrap main source; actual=$mapper_scan_count" >&2
  exit 1
fi

grep -q "NOVA_SECURITY_JWT_SECRET" "$ROOT_DIR/nova-bootstrap/src/main/resources/application.yml"
grep -q "%X{traceId" "$ROOT_DIR/nova-bootstrap/src/main/resources/logback-spring.xml"
grep -q "mysql:8.0" "$ROOT_DIR/deploy/docker/docker-compose.yml"
grep -q "redis:6" "$ROOT_DIR/deploy/docker/docker-compose.yml"

echo "deploy files validation passed"
