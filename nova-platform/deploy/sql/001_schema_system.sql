-- nova-platform system schema, MySQL 8.x.
-- Execute after creating database nova_platform with utf8mb4.
SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL COMMENT '主键 ID',
    username VARCHAR(64) NOT NULL COMMENT '登录用户名',
    password VARCHAR(128) NOT NULL COMMENT 'BCrypt 密码摘要',
    real_name VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    nickname VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    mobile VARCHAR(32) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    avatar VARCHAR(512) DEFAULT NULL COMMENT '头像 URL',
    status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '用户状态：ENABLED/DISABLED',
    super_admin_flag TINYINT NOT NULL DEFAULT 0 COMMENT '是否超级管理员：0否 1是',
    login_lock_flag TINYINT NOT NULL DEFAULT 0 COMMENT '是否被登录锁定：0否 1是',
    last_login_at DATETIME DEFAULT NULL COMMENT '最后登录时间',
    created_by BIGINT DEFAULT 0 COMMENT '创建人 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT DEFAULT 0 COMMENT '更新人 ID',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_user_username_delete (username, delete_flag),
    KEY idx_sys_user_status (status),
    KEY idx_sys_user_mobile (mobile)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL COMMENT '主键 ID',
    role_code VARCHAR(64) NOT NULL COMMENT '角色编码',
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序值',
    status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '角色状态：ENABLED/DISABLED',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    created_by BIGINT DEFAULT 0 COMMENT '创建人 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT DEFAULT 0 COMMENT '更新人 ID',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_code_delete (role_code, delete_flag),
    KEY idx_sys_role_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT NOT NULL COMMENT '主键 ID',
    parent_id BIGINT NOT NULL DEFAULT 0 COMMENT '父级菜单 ID，根为 0',
    menu_name VARCHAR(64) NOT NULL COMMENT '菜单名称',
    menu_type VARCHAR(32) NOT NULL COMMENT '菜单类型：DIR/MENU/BUTTON',
    route_path VARCHAR(256) DEFAULT NULL COMMENT '前端路由路径',
    component_path VARCHAR(256) DEFAULT NULL COMMENT '前端组件路径',
    permission_code VARCHAR(128) DEFAULT NULL COMMENT '权限码，BUTTON 必填',
    icon VARCHAR(128) DEFAULT NULL COMMENT '图标',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序值',
    visible_flag TINYINT NOT NULL DEFAULT 1 COMMENT '是否显示：0隐藏 1显示',
    status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '状态：ENABLED/DISABLED',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    created_by BIGINT DEFAULT 0 COMMENT '创建人 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT DEFAULT 0 COMMENT '更新人 ID',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_menu_permission_delete (permission_code, delete_flag),
    KEY idx_sys_menu_parent (parent_id),
    KEY idx_sys_menu_type_status (menu_type, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单与按钮权限表';

CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    role_id BIGINT NOT NULL COMMENT '角色 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_user_role (user_id, role_id),
    KEY idx_sys_user_role_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关系表';

CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    role_id BIGINT NOT NULL COMMENT '角色 ID',
    menu_id BIGINT NOT NULL COMMENT '菜单 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_menu (role_id, menu_id),
    KEY idx_sys_role_menu_menu (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关系表';

CREATE TABLE IF NOT EXISTS sys_dict_type (
    id BIGINT NOT NULL COMMENT '主键 ID',
    dict_code VARCHAR(64) NOT NULL COMMENT '字典编码',
    dict_name VARCHAR(128) NOT NULL COMMENT '字典名称',
    status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '状态：ENABLED/DISABLED',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    created_by BIGINT DEFAULT 0 COMMENT '创建人 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT DEFAULT 0 COMMENT '更新人 ID',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_dict_type_code_delete (dict_code, delete_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统字典类型表';

CREATE TABLE IF NOT EXISTS sys_dict_item (
    id BIGINT NOT NULL COMMENT '主键 ID',
    dict_code VARCHAR(64) NOT NULL COMMENT '字典编码',
    item_label VARCHAR(128) NOT NULL COMMENT '字典项标签',
    item_value VARCHAR(128) NOT NULL COMMENT '字典项值',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序值',
    status VARCHAR(32) NOT NULL DEFAULT 'ENABLED' COMMENT '状态：ENABLED/DISABLED',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    created_by BIGINT DEFAULT 0 COMMENT '创建人 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT DEFAULT 0 COMMENT '更新人 ID',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_dict_item_value_delete (dict_code, item_value, delete_flag),
    KEY idx_sys_dict_item_code_status (dict_code, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统字典项表';

CREATE TABLE IF NOT EXISTS sys_config (
    id BIGINT NOT NULL COMMENT '主键 ID',
    config_key VARCHAR(128) NOT NULL COMMENT '参数键',
    config_value TEXT COMMENT '参数值',
    config_name VARCHAR(128) NOT NULL COMMENT '参数名称',
    config_type VARCHAR(32) NOT NULL DEFAULT 'STRING' COMMENT '参数类型：STRING/NUMBER/BOOLEAN/JSON',
    remark VARCHAR(512) DEFAULT NULL COMMENT '备注',
    created_by BIGINT DEFAULT 0 COMMENT '创建人 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT DEFAULT 0 COMMENT '更新人 ID',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_config_key_delete (config_key, delete_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统参数表';

CREATE TABLE IF NOT EXISTS sys_login_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    user_id BIGINT DEFAULT NULL COMMENT '用户 ID',
    username VARCHAR(64) DEFAULT NULL COMMENT '登录用户名',
    login_ip VARCHAR(64) DEFAULT NULL COMMENT '登录 IP',
    user_agent VARCHAR(512) DEFAULT NULL COMMENT 'User-Agent',
    login_result VARCHAR(32) NOT NULL COMMENT '登录结果：SUCCESS/FAILURE',
    failure_reason VARCHAR(64) DEFAULT NULL COMMENT '失败原因',
    login_at DATETIME NOT NULL COMMENT '登录时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_sys_login_log_username (username),
    KEY idx_sys_login_log_login_at (login_at),
    KEY idx_sys_login_log_result (login_result)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统登录日志表';
