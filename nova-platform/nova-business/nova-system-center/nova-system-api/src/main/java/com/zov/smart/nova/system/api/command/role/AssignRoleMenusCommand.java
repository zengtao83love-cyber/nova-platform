package com.zov.smart.nova.system.api.command.role;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class AssignRoleMenusCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @NotNull(message = "菜单ID集合不能为空")
    private List<Long> menuIds;




            public Long getRoleId() {
                return roleId;
            }

            public void setRoleId(Long roleId) {
                this.roleId = roleId;
            }


            public List<Long> getMenuIds() {
                return menuIds;
            }

            public void setMenuIds(List<Long> menuIds) {
                this.menuIds = menuIds;
            }

    public AssignRoleMenusCommand() { this.menuIds = new ArrayList<Long>(); }

}
