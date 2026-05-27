package com.zov.smart.nova.system.api.command.role;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateRoleCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "角色ID不能为空")
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 64, message = "角色名称长度不能超过64")
    private String roleName;

    @Min(value = 0, message = "排序不能小于0")
    private Integer sortOrder;

    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "角色状态只能是ENABLED或DISABLED")
    private String status;

    @Size(max = 255, message = "备注长度不能超过255")
    private String remark;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }


            public Integer getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
            }


            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

    public UpdateRoleCommand() { this.sortOrder = 0; }

}
