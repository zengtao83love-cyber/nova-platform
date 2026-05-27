package com.zov.smart.nova.system.support;

import com.zov.smart.nova.common.core.error.CommonErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.data.access.system.config.enums.ConfigTypeEnum;
import com.zov.smart.nova.data.access.system.dict.enums.DictStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;

/**
 * Defensive converters for externally supplied status and enum codes.
 */
public final class StatusValidators {

    private StatusValidators() {
    }

    public static UserStatusEnum userStatusOrDefault(String status) {
        if (isBlank(status)) {
            return UserStatusEnum.ENABLED;
        }
        UserStatusEnum value = UserStatusEnum.fromCode(status.trim());
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "用户状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static UserStatusEnum requiredUserStatus(String status) {
        UserStatusEnum value = UserStatusEnum.fromCode(trimToNull(status));
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "用户状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static RoleStatusEnum roleStatusOrDefault(String status) {
        if (isBlank(status)) {
            return RoleStatusEnum.ENABLED;
        }
        RoleStatusEnum value = RoleStatusEnum.fromCode(status.trim());
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "角色状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static RoleStatusEnum requiredRoleStatus(String status) {
        RoleStatusEnum value = RoleStatusEnum.fromCode(trimToNull(status));
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "角色状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static MenuTypeEnum requiredMenuType(String type) {
        MenuTypeEnum value = MenuTypeEnum.fromCode(trimToNull(type));
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "菜单类型只能是 DIR / MENU / BUTTON");
        }
        return value;
    }

    public static MenuStatusEnum menuStatusOrDefault(String status) {
        if (isBlank(status)) {
            return MenuStatusEnum.ENABLED;
        }
        MenuStatusEnum value = MenuStatusEnum.fromCode(status.trim());
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "菜单状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static MenuStatusEnum requiredMenuStatus(String status) {
        MenuStatusEnum value = MenuStatusEnum.fromCode(trimToNull(status));
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "菜单状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static DictStatusEnum dictStatusOrDefault(String status) {
        if (isBlank(status)) {
            return DictStatusEnum.ENABLED;
        }
        DictStatusEnum value = DictStatusEnum.fromCode(status.trim());
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "字典状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static DictStatusEnum requiredDictStatus(String status) {
        DictStatusEnum value = DictStatusEnum.fromCode(trimToNull(status));
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "字典状态只能是 ENABLED 或 DISABLED");
        }
        return value;
    }

    public static ConfigTypeEnum configTypeOrDefault(String type) {
        if (isBlank(type)) {
            return ConfigTypeEnum.STRING;
        }
        ConfigTypeEnum value = ConfigTypeEnum.fromCode(type.trim());
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "参数类型只能是 STRING / NUMBER / BOOLEAN / JSON");
        }
        return value;
    }

    public static ConfigTypeEnum requiredConfigType(String type) {
        ConfigTypeEnum value = ConfigTypeEnum.fromCode(trimToNull(type));
        if (value == null) {
            throw new BusinessException(CommonErrorCode.COMMON_PARAM_INVALID, "参数类型只能是 STRING / NUMBER / BOOLEAN / JSON");
        }
        return value;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String trimToNull(String value) {
        return isBlank(value) ? null : value.trim();
    }
}
