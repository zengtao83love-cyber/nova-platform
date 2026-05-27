package com.zov.smart.nova.infra.audit.enums;

import com.zov.smart.nova.common.mybatis.enums.DbEnum;

/**
 * Operation types persisted into audit_operation_log.operation_type.
 */
public enum AuditOperationTypeEnum implements DbEnum {

    CREATE("CREATE", "新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    QUERY("QUERY", "查询"),
    EXPORT("EXPORT", "导出"),
    LOGIN("LOGIN", "登录"),
    OTHER("OTHER", "其他");

    private final String code;
    private final String label;

    AuditOperationTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
