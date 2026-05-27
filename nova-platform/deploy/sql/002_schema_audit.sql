-- nova-platform audit schema, MySQL 8.x.
SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS audit_operation_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    trace_id VARCHAR(64) DEFAULT NULL COMMENT '链路追踪 ID',
    operation_name VARCHAR(128) NOT NULL COMMENT '操作名称',
    operation_type VARCHAR(32) NOT NULL COMMENT '操作类型',
    request_method VARCHAR(16) DEFAULT NULL COMMENT 'HTTP 方法',
    request_uri VARCHAR(512) DEFAULT NULL COMMENT '请求 URI',
    request_params MEDIUMTEXT COMMENT '脱敏后的请求参数',
    response_body MEDIUMTEXT COMMENT '脱敏后的响应体',
    success_flag TINYINT NOT NULL DEFAULT 1 COMMENT '是否成功：0失败 1成功',
    error_message VARCHAR(2048) DEFAULT NULL COMMENT '错误信息',
    operator_id BIGINT DEFAULT NULL COMMENT '操作人 ID',
    operator_name VARCHAR(64) DEFAULT NULL COMMENT '操作人名称',
    client_ip VARCHAR(64) DEFAULT NULL COMMENT '客户端 IP',
    user_agent VARCHAR(512) DEFAULT NULL COMMENT 'User-Agent',
    cost_time_ms BIGINT DEFAULT NULL COMMENT '耗时毫秒',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    delete_flag TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除 1已删除',
    version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    PRIMARY KEY (id),
    KEY idx_audit_trace_id (trace_id),
    KEY idx_audit_operator_id (operator_id),
    KEY idx_audit_created_at (created_at),
    KEY idx_audit_operation_type (operation_type),
    KEY idx_audit_success_flag (success_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作审计日志表';
