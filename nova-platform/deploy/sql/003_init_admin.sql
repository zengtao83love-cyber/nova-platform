-- Default account for local development.
-- username: admin
-- password: Admin@123456
-- BCrypt generated with cost 10; change immediately in production.
SET NAMES utf8mb4;

INSERT INTO sys_user (
    id, username, password, real_name, nickname, mobile, email, avatar,
    status, super_admin_flag, login_lock_flag, last_login_at,
    created_by, created_at, updated_by, updated_at, delete_flag, version
) VALUES (
    1, 'admin', '$2a$10$lyV/E8yzkyGEOShGa2hfZO81uoDEbgFIO7uBSmpMhcCunsTywoGyi', '平台管理员', 'admin', NULL, 'admin@nova.local', NULL,
    'ENABLED', 1, 0, NULL,
    0, NOW(), 0, NOW(), 0, 0
) ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    real_name = VALUES(real_name),
    status = VALUES(status),
    super_admin_flag = VALUES(super_admin_flag),
    login_lock_flag = 0,
    updated_at = NOW();

INSERT INTO sys_role (
    id, role_code, role_name, sort_order, status, remark,
    created_by, created_at, updated_by, updated_at, delete_flag, version
) VALUES (
    1, 'SUPER_ADMIN', '超级管理员', 1, 'ENABLED', '系统内置超级管理员角色',
    0, NOW(), 0, NOW(), 0, 0
) ON DUPLICATE KEY UPDATE
    role_name = VALUES(role_name),
    status = VALUES(status),
    updated_at = NOW();

INSERT IGNORE INTO sys_user_role (user_id, role_id, created_at) VALUES (1, 1, NOW());
