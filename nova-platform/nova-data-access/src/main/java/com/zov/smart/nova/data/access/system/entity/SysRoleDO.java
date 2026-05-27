package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色持久化对象，对应表 sys_role。
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role", autoResultMap = true)
@Data
public class SysRoleDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    @TableField("role_code")
    private String roleCode;

    @TableField("role_name")
    private String roleName;

    @TableField("sort_order")
    private Integer sortOrder;

    @TableField(value = "status", typeHandler = EnumCodeTypeHandler.class)
    private RoleStatusEnum status;

    @TableField("remark")
    private String remark;

}
