# nova-platform SQL 执行顺序

请按以下顺序执行：

1. `001_schema_system.sql`
2. `002_schema_audit.sql`
3. `003_init_admin.sql`
4. `004_init_rbac.sql`
5. `005_init_dict_config.sql`

本地默认账号：

- 用户名：`admin`
- 密码：`Admin@123456`

生产环境必须立即修改默认密码，并通过环境变量注入 `NOVA_SECURITY_JWT_SECRET`。
