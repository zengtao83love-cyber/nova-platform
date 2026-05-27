# 本地 Docker 环境

启动 MySQL 与 Redis：

```bash
cd deploy/docker
cp .env.example .env
docker compose up -d
```

MySQL 容器会按文件名顺序自动执行 `../sql/*.sql`。
应用启动前请设置：

```bash
export NOVA_SECURITY_JWT_SECRET=please-change-this-very-long-secret-32chars-min
```
