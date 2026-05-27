-- Initial dictionaries and management parameters.
SET NAMES utf8mb4;

INSERT INTO sys_dict_type (id, dict_code, dict_name, status, remark, created_by, created_at, updated_by, updated_at, delete_flag, version) VALUES
    (2001, 'common_status', '通用启停状态', 'ENABLED', '系统内置状态字典', 0, NOW(), 0, NOW(), 0, 0),
    (2002, 'menu_type', '菜单类型', 'ENABLED', '系统菜单类型字典', 0, NOW(), 0, NOW(), 0, 0),
    (2003, 'login_result', '登录结果', 'ENABLED', '登录日志结果字典', 0, NOW(), 0, NOW(), 0, 0),
    (2004, 'config_type', '参数类型', 'ENABLED', '系统参数值类型字典', 0, NOW(), 0, NOW(), 0, 0)
ON DUPLICATE KEY UPDATE dict_name = VALUES(dict_name), status = VALUES(status), updated_at = NOW();

INSERT INTO sys_dict_item (id, dict_code, item_label, item_value, sort_order, status, remark, created_by, created_at, updated_by, updated_at, delete_flag, version) VALUES
    (2101, 'common_status', '启用', 'ENABLED', 1, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2102, 'common_status', '禁用', 'DISABLED', 2, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2201, 'menu_type', '目录', 'DIR', 1, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2202, 'menu_type', '菜单', 'MENU', 2, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2203, 'menu_type', '按钮', 'BUTTON', 3, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2301, 'login_result', '成功', 'SUCCESS', 1, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2302, 'login_result', '失败', 'FAILURE', 2, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2401, 'config_type', '字符串', 'STRING', 1, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2402, 'config_type', '数字', 'NUMBER', 2, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2403, 'config_type', '布尔', 'BOOLEAN', 3, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0),
    (2404, 'config_type', 'JSON', 'JSON', 4, 'ENABLED', NULL, 0, NOW(), 0, NOW(), 0, 0)
ON DUPLICATE KEY UPDATE item_label = VALUES(item_label), status = VALUES(status), updated_at = NOW();

INSERT INTO sys_config (id, config_key, config_value, config_name, config_type, remark, created_by, created_at, updated_by, updated_at, delete_flag, version) VALUES
    (3001, 'system.name', 'nova-platform', '系统名称', 'STRING', '展示参数，不驱动运行时配置', 0, NOW(), 0, NOW(), 0, 0),
    (3002, 'system.admin-default-password', 'Admin@123456', '本地初始化管理员默认密码', 'STRING', '仅用于本地初始化说明，生产环境必须修改', 0, NOW(), 0, NOW(), 0, 0),
    (3003, 'security.token.expire-seconds', '7200', '访问令牌有效期展示值', 'NUMBER', '运行时以 application.yml / 环境变量为准', 0, NOW(), 0, NOW(), 0, 0),
    (3004, 'security.login.max-fail-count', '5', '登录失败锁定阈值展示值', 'NUMBER', '运行时以 application.yml / 环境变量为准', 0, NOW(), 0, NOW(), 0, 0),
    (3005, 'security.login.lock-window-seconds', '1800', '登录锁定窗口展示值', 'NUMBER', '运行时以 application.yml / 环境变量为准', 0, NOW(), 0, NOW(), 0, 0)
ON DUPLICATE KEY UPDATE config_value = VALUES(config_value), config_name = VALUES(config_name), updated_at = NOW();
