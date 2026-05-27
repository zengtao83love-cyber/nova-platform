package com.zov.smart.nova.system.error;

import com.zov.smart.nova.common.core.error.ErrorCode;

/**
 * System-center business error codes from 23_ERROR_CODE_SPEC.md.
 */
public enum SystemErrorCode implements ErrorCode {

    USER_USERNAME_EXISTS("USER_USERNAME_EXISTS", "用户名已存在", 200),
    USER_NOT_FOUND("USER_NOT_FOUND", "用户不存在", 404),
    USER_SUPER_ADMIN_PROTECTED("USER_SUPER_ADMIN_PROTECTED", "超级管理员不允许执行该操作", 200),
    USER_LAST_ADMIN_FORBIDDEN("USER_LAST_ADMIN_FORBIDDEN", "至少保留一个启用状态的超级管理员", 200),

    ROLE_CODE_EXISTS("ROLE_CODE_EXISTS", "角色编码已存在", 200),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "角色不存在", 404),
    ROLE_IN_USE("ROLE_IN_USE", "角色已被用户使用，不能删除", 200),

    MENU_NOT_FOUND("MENU_NOT_FOUND", "菜单不存在", 404),
    MENU_PARENT_NOT_FOUND("MENU_PARENT_NOT_FOUND", "父级菜单不存在", 404),
    MENU_PERMISSION_CODE_EXISTS("MENU_PERMISSION_CODE_EXISTS", "权限码已存在", 200),
    MENU_HAS_CHILDREN("MENU_HAS_CHILDREN", "当前菜单存在子节点，不能删除", 200),
    MENU_BUTTON_PERMISSION_REQUIRED("MENU_BUTTON_PERMISSION_REQUIRED", "按钮菜单必须配置权限码", 200),
    MENU_TYPE_INVALID("MENU_TYPE_INVALID", "菜单类型不合法", 400),

    DICT_TYPE_CODE_EXISTS("DICT_TYPE_CODE_EXISTS", "字典编码已存在", 200),
    DICT_TYPE_NOT_FOUND("DICT_TYPE_NOT_FOUND", "字典类型不存在", 404),
    DICT_ITEM_NOT_FOUND("DICT_ITEM_NOT_FOUND", "字典项不存在", 404),
    DICT_ITEM_VALUE_EXISTS("DICT_ITEM_VALUE_EXISTS", "字典项值已存在", 200),

    CONFIG_KEY_EXISTS("CONFIG_KEY_EXISTS", "参数键已存在", 200),
    CONFIG_NOT_FOUND("CONFIG_NOT_FOUND", "系统参数不存在", 404),
    CONFIG_VALUE_TYPE_INVALID("CONFIG_VALUE_TYPE_INVALID", "参数值类型不合法", 400);

    private final String code;
    private final String message;
    private final int httpStatus;

    SystemErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
