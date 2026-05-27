package com.zov.smart.nova.system.api.constant;

/**
 * System-center permission code constants.
 *
 * <p>Constants live in the API module so controllers, security checks, and
 * downstream business modules use exactly the same permission-code spelling.</p>
 */
public final class SystemPermissionConstants {

    private SystemPermissionConstants() {
    }

    public static final String USER_LIST = "system:user:list";
    public static final String USER_CREATE = "system:user:create";
    public static final String USER_UPDATE = "system:user:update";
    public static final String USER_DELETE = "system:user:delete";
    public static final String USER_RESET_PASSWORD = "system:user:reset-password";
    public static final String USER_ASSIGN_ROLE = "system:user:assign-role";

    public static final String ROLE_LIST = "system:role:list";
    public static final String ROLE_CREATE = "system:role:create";
    public static final String ROLE_UPDATE = "system:role:update";
    public static final String ROLE_DELETE = "system:role:delete";
    public static final String ROLE_ASSIGN_MENU = "system:role:assign-menu";

    public static final String MENU_LIST = "system:menu:list";
    public static final String MENU_CREATE = "system:menu:create";
    public static final String MENU_UPDATE = "system:menu:update";
    public static final String MENU_DELETE = "system:menu:delete";

    public static final String DICT_LIST = "system:dict:list";
    public static final String DICT_CREATE = "system:dict:create";
    public static final String DICT_UPDATE = "system:dict:update";
    public static final String DICT_DELETE = "system:dict:delete";

    public static final String CONFIG_LIST = "system:config:list";
    public static final String CONFIG_CREATE = "system:config:create";
    public static final String CONFIG_UPDATE = "system:config:update";
    public static final String CONFIG_DELETE = "system:config:delete";

    public static final String LOGIN_LOG_LIST = "system:login-log:list";
    public static final String AUDIT_LOG_LIST = "system:audit-log:list";
    public static final String ENUM_LIST = "system:enum:list";
}
