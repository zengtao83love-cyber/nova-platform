package com.zov.smart.nova.system.api.command.user;

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

public class AssignUserRolesCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "角色ID集合不能为空")
    private List<Long> roleIds;




            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }


            public List<Long> getRoleIds() {
                return roleIds;
            }

            public void setRoleIds(List<Long> roleIds) {
                this.roleIds = roleIds;
            }

    public AssignUserRolesCommand() {
        this.roleIds = new ArrayList<Long>();
    }

}
