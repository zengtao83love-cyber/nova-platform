你现在这个返回：

```json
{
  "code": "AUTH_UNAUTHORIZED",
  "message": "未登录或登录已过期"
}
```

说明：**你正在调用受保护接口，但请求头里没有带 `accessToken`，或者 Token 已失效。**

正确调用顺序是：

```text
1. 先调用登录接口 /api/auth/login
2. 拿到 accessToken
3. 后续所有受保护接口都在 Header 里带 Authorization: Bearer accessToken
```

---

## 1. 默认管理员账号

系统初始化 SQL 里默认账号是：

```text
用户名：admin
密码：Admin@123456
```

---

## 2. 登录接口调用方式

### 请求地址

```http
POST http://localhost:8080/api/auth/login
```

### Header

```http
Content-Type: application/json
```

### Body

```json
{
  "username": "admin",
  "password": "Admin@123456"
}
```

### curl 示例

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin@123456"
  }'
```

### 登录成功后返回类似这样

```json
{
  "code": "0",
  "message": "success",
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.xxx.xxx",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.xxx.xxx",
    "expiresIn": 7200,
    "tokenType": "Bearer"
  }
}
```

你需要复制：

```text
data.accessToken
```

---

## 3. 调用受保护接口

例如调用当前用户信息：

```http
GET http://localhost:8080/api/auth/me
```

### Header 必须这样设置

```http
Authorization: Bearer 你的accessToken
```

注意：`Bearer` 后面有一个空格。

### curl 示例

```bash
curl -X GET "http://localhost:8080/api/auth/me" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.xxx.xxx"
```

---

## 4. 调用系统管理接口示例

### 查询用户列表

```http
GET http://localhost:8080/api/system/users?pageNo=1&pageSize=10
```

### Header

```http
Authorization: Bearer 你的accessToken
```

### curl 示例

```bash
curl -X GET "http://localhost:8080/api/system/users?pageNo=1&pageSize=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.xxx.xxx"
```

---

## 5. Token 过期后如何刷新

### 请求地址

```http
POST http://localhost:8080/api/auth/refresh
```

### Header

```http
Content-Type: application/json
```

### Body

```json
{
  "refreshToken": "你的refreshToken"
}
```

### curl 示例

```bash
curl -X POST "http://localhost:8080/api/auth/refresh" \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.xxx.xxx"
  }'
```

刷新成功后会返回新的：

```text
data.accessToken
```

然后后续请求继续使用新的 accessToken。

---

## 6. Postman / Apifox 设置方式

登录接口：

```text
Method: POST
URL: http://localhost:8080/api/auth/login
Headers:
  Content-Type: application/json
Body:
  raw / JSON
```

Body：

```json
{
  "username": "admin",
  "password": "Admin@123456"
}
```

调用其他接口时：

```text
Headers:
  Authorization: Bearer 登录返回的accessToken
```

或者在 Authorization 选项卡中选择：

```text
Type: Bearer Token
Token: 登录返回的 accessToken
```

---

## 7. Swagger / Knife4j 如何调用

先访问：

```text
http://localhost:8080/doc.html
```

如果只是访问文档地址，修复后这些地址不应该被拦截：

```text
http://localhost:8080/v3/api-docs/swagger-config
http://localhost:8080/v3/api-docs
http://localhost:8080/doc.html
```

但在 Swagger / Knife4j 页面里调用业务接口时，仍然需要授权。

操作方式：

```text
1. 先调用 /api/auth/login
2. 复制返回的 accessToken
3. 点击页面上的 Authorize / 授权
4. 输入：
   Bearer 你的accessToken
5. 再调用 /api/system/users 等接口
```

---

## 8. 如果登录失败，重点检查这几个点

### 1. 初始化 SQL 是否执行

必须执行过：

```text
deploy/sql/001_schema_system.sql
deploy/sql/002_schema_audit.sql
deploy/sql/003_init_admin.sql
deploy/sql/004_init_rbac.sql
deploy/sql/005_init_dict_config.sql
```

### 2. Redis 是否启动

Token 会写入 Redis。Redis 没启动，登录或后续鉴权会异常。

### 3. MySQL 是否连接正确

确认 `application.yml` 里的数据库地址、账号、密码正确。

### 4. 密码连续输错超过 5 次

系统会锁定 30 分钟。可以重启 Redis 或清理 Redis key：

```text
nova:security:login-fail:admin
```

---

## 9. 最小完整调用流程

```bash
# 1. 登录
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin@123456"}'

# 2. 复制返回的 data.accessToken

# 3. 调用当前用户
curl -X GET "http://localhost:8080/api/auth/me" \
  -H "Authorization: Bearer 这里替换成accessToken"

# 4. 查询用户列表
curl -X GET "http://localhost:8080/api/system/users?pageNo=1&pageSize=10" \
  -H "Authorization: Bearer 这里替换成accessToken"
```

关键点只有一个：**除了登录、刷新 Token、Swagger 文档、健康检查这些白名单接口，其他接口都必须带 `Authorization: Bearer accessToken`。**
