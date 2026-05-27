-- Initial RBAC menus and role grants.
SET NAMES utf8mb4;

INSERT INTO sys_menu (
    id, parent_id, menu_name, menu_type, route_path, component_path, permission_code, icon,
    sort_order, visible_flag, status, remark,
    created_by, created_at, updated_by, updated_at, delete_flag, version
) VALUES
    (1000, 0, '系统管理', 'DIR', '/system', NULL, NULL, 'system', 100, 1, 'ENABLED', '系统管理目录', 0, NOW(), 0, NOW(), 0, 0),
    (1100, 1000, '用户管理', 'MENU', '/system/users', 'system/user/index', NULL, 'user', 110, 1, 'ENABLED', '用户管理页面', 0, NOW(), 0, NOW(), 0, 0),
    (1101, 1100, '用户查询', 'BUTTON', NULL, NULL, 'system:user:list', NULL, 111, 0, 'ENABLED', '用户分页与详情', 0, NOW(), 0, NOW(), 0, 0),
    (1102, 1100, '用户新增', 'BUTTON', NULL, NULL, 'system:user:create', NULL, 112, 0, 'ENABLED', '新增用户', 0, NOW(), 0, NOW(), 0, 0),
    (1103, 1100, '用户修改', 'BUTTON', NULL, NULL, 'system:user:update', NULL, 113, 0, 'ENABLED', '修改用户', 0, NOW(), 0, NOW(), 0, 0),
    (1104, 1100, '用户删除', 'BUTTON', NULL, NULL, 'system:user:delete', NULL, 114, 0, 'ENABLED', '删除用户', 0, NOW(), 0, NOW(), 0, 0),
    (1105, 1100, '重置密码', 'BUTTON', NULL, NULL, 'system:user:reset-password', NULL, 115, 0, 'ENABLED', '重置用户密码', 0, NOW(), 0, NOW(), 0, 0),
    (1106, 1100, '分配角色', 'BUTTON', NULL, NULL, 'system:user:assign-role', NULL, 116, 0, 'ENABLED', '为用户分配角色', 0, NOW(), 0, NOW(), 0, 0),
    (1200, 1000, '角色管理', 'MENU', '/system/roles', 'system/role/index', NULL, 'role', 120, 1, 'ENABLED', '角色管理页面', 0, NOW(), 0, NOW(), 0, 0),
    (1201, 1200, '角色查询', 'BUTTON', NULL, NULL, 'system:role:list', NULL, 121, 0, 'ENABLED', '角色分页与详情', 0, NOW(), 0, NOW(), 0, 0),
    (1202, 1200, '角色新增', 'BUTTON', NULL, NULL, 'system:role:create', NULL, 122, 0, 'ENABLED', '新增角色', 0, NOW(), 0, NOW(), 0, 0),
    (1203, 1200, '角色修改', 'BUTTON', NULL, NULL, 'system:role:update', NULL, 123, 0, 'ENABLED', '修改角色', 0, NOW(), 0, NOW(), 0, 0),
    (1204, 1200, '角色删除', 'BUTTON', NULL, NULL, 'system:role:delete', NULL, 124, 0, 'ENABLED', '删除角色', 0, NOW(), 0, NOW(), 0, 0),
    (1205, 1200, '分配菜单', 'BUTTON', NULL, NULL, 'system:role:assign-menu', NULL, 125, 0, 'ENABLED', '为角色分配菜单', 0, NOW(), 0, NOW(), 0, 0),
    (1300, 1000, '菜单管理', 'MENU', '/system/menus', 'system/menu/index', NULL, 'menu', 130, 1, 'ENABLED', '菜单管理页面', 0, NOW(), 0, NOW(), 0, 0),
    (1301, 1300, '菜单查询', 'BUTTON', NULL, NULL, 'system:menu:list', NULL, 131, 0, 'ENABLED', '菜单树与详情', 0, NOW(), 0, NOW(), 0, 0),
    (1302, 1300, '菜单新增', 'BUTTON', NULL, NULL, 'system:menu:create', NULL, 132, 0, 'ENABLED', '新增菜单', 0, NOW(), 0, NOW(), 0, 0),
    (1303, 1300, '菜单修改', 'BUTTON', NULL, NULL, 'system:menu:update', NULL, 133, 0, 'ENABLED', '修改菜单', 0, NOW(), 0, NOW(), 0, 0),
    (1304, 1300, '菜单删除', 'BUTTON', NULL, NULL, 'system:menu:delete', NULL, 134, 0, 'ENABLED', '删除菜单', 0, NOW(), 0, NOW(), 0, 0),
    (1400, 1000, '字典管理', 'MENU', '/system/dicts', 'system/dict/index', NULL, 'dict', 140, 1, 'ENABLED', '字典管理页面', 0, NOW(), 0, NOW(), 0, 0),
    (1401, 1400, '字典查询', 'BUTTON', NULL, NULL, 'system:dict:list', NULL, 141, 0, 'ENABLED', '查询字典', 0, NOW(), 0, NOW(), 0, 0),
    (1402, 1400, '字典新增', 'BUTTON', NULL, NULL, 'system:dict:create', NULL, 142, 0, 'ENABLED', '新增字典', 0, NOW(), 0, NOW(), 0, 0),
    (1403, 1400, '字典修改', 'BUTTON', NULL, NULL, 'system:dict:update', NULL, 143, 0, 'ENABLED', '修改字典', 0, NOW(), 0, NOW(), 0, 0),
    (1404, 1400, '字典删除', 'BUTTON', NULL, NULL, 'system:dict:delete', NULL, 144, 0, 'ENABLED', '删除字典', 0, NOW(), 0, NOW(), 0, 0),
    (1500, 1000, '参数管理', 'MENU', '/system/configs', 'system/config/index', NULL, 'config', 150, 1, 'ENABLED', '参数管理页面', 0, NOW(), 0, NOW(), 0, 0),
    (1501, 1500, '参数查询', 'BUTTON', NULL, NULL, 'system:config:list', NULL, 151, 0, 'ENABLED', '查询参数', 0, NOW(), 0, NOW(), 0, 0),
    (1502, 1500, '参数新增', 'BUTTON', NULL, NULL, 'system:config:create', NULL, 152, 0, 'ENABLED', '新增参数', 0, NOW(), 0, NOW(), 0, 0),
    (1503, 1500, '参数修改', 'BUTTON', NULL, NULL, 'system:config:update', NULL, 153, 0, 'ENABLED', '修改参数', 0, NOW(), 0, NOW(), 0, 0),
    (1504, 1500, '参数删除', 'BUTTON', NULL, NULL, 'system:config:delete', NULL, 154, 0, 'ENABLED', '删除参数', 0, NOW(), 0, NOW(), 0, 0),
    (1600, 1000, '日志管理', 'MENU', '/system/logs', 'system/log/index', NULL, 'log', 160, 1, 'ENABLED', '日志查询页面', 0, NOW(), 0, NOW(), 0, 0),
    (1601, 1600, '登录日志查询', 'BUTTON', NULL, NULL, 'system:login-log:list', NULL, 161, 0, 'ENABLED', '查询登录日志', 0, NOW(), 0, NOW(), 0, 0),
    (1602, 1600, '审计日志查询', 'BUTTON', NULL, NULL, 'system:audit-log:list', NULL, 162, 0, 'ENABLED', '查询审计日志', 0, NOW(), 0, NOW(), 0, 0),
    (1700, 1000, '枚举查询', 'MENU', '/system/enums', 'system/enum/index', NULL, 'enum', 170, 1, 'ENABLED', '枚举查询页面', 0, NOW(), 0, NOW(), 0, 0),
    (1701, 1700, '枚举查询', 'BUTTON', NULL, NULL, 'system:enum:list', NULL, 171, 0, 'ENABLED', '查询枚举', 0, NOW(), 0, NOW(), 0, 0)
ON DUPLICATE KEY UPDATE
    parent_id = VALUES(parent_id),
    menu_name = VALUES(menu_name),
    menu_type = VALUES(menu_type),
    route_path = VALUES(route_path),
    component_path = VALUES(component_path),
    permission_code = VALUES(permission_code),
    icon = VALUES(icon),
    sort_order = VALUES(sort_order),
    visible_flag = VALUES(visible_flag),
    status = VALUES(status),
    remark = VALUES(remark),
    updated_at = NOW();

INSERT IGNORE INTO sys_role_menu (role_id, menu_id, created_at)
SELECT 1, id, NOW() FROM sys_menu WHERE delete_flag = 0;
