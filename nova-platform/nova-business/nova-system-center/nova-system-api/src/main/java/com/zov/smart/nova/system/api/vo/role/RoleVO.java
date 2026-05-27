package com.zov.smart.nova.system.api.vo.role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoleVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String roleCode;

    private String roleName;

    private Integer sortOrder;

    private String status;

    private String remark;

    private List<Long> menuIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


            public String getRoleCode() {
                return roleCode;
            }

            public void setRoleCode(String roleCode) {
                this.roleCode = roleCode;
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


            public List<Long> getMenuIds() {
                return menuIds;
            }

            public void setMenuIds(List<Long> menuIds) {
                this.menuIds = menuIds;
            }


            public LocalDateTime getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
            }


            public LocalDateTime getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
            }
     public RoleVO(){this.sortOrder=0;this.status="ENABLED";this.menuIds=new ArrayList<Long>();} 
}
