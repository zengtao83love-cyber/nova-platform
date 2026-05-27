package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;

/**
 * 系统角色持久化对象，对应表 sys_role。
 */
@TableName(value = "sys_role", autoResultMap = true)
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

    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public RoleStatusEnum getStatus() { return status; }
    public void setStatus(RoleStatusEnum status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
