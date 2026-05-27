package com.zov.smart.nova.system.api.query.role;

import com.zov.smart.nova.common.model.PageParam;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Role management paging query contract. */
public class RolePageQuery extends PageParam {
    private static final long serialVersionUID = 1L;
    @Size(max = 64, message = "角色编码长度不能超过64")
    private String roleCode;
    @Size(max = 64, message = "角色名称长度不能超过64")
    private String roleName;
    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "角色状态只能是ENABLED或DISABLED")
    private String status;
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
