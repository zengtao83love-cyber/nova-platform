package com.zov.smart.nova.system.api.dto.menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String menuName;

    private String menuType;

    private String routePath;

    private String componentPath;

    private String permissionCode;

    private String icon;

    private Integer sortOrder;

    private Integer visibleFlag;

    private String status;

    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


            public Long getParentId() {
                return parentId;
            }

            public void setParentId(Long parentId) {
                this.parentId = parentId;
            }


            public String getMenuName() {
                return menuName;
            }

            public void setMenuName(String menuName) {
                this.menuName = menuName;
            }


            public String getMenuType() {
                return menuType;
            }

            public void setMenuType(String menuType) {
                this.menuType = menuType;
            }


            public String getRoutePath() {
                return routePath;
            }

            public void setRoutePath(String routePath) {
                this.routePath = routePath;
            }


            public String getComponentPath() {
                return componentPath;
            }

            public void setComponentPath(String componentPath) {
                this.componentPath = componentPath;
            }


            public String getPermissionCode() {
                return permissionCode;
            }

            public void setPermissionCode(String permissionCode) {
                this.permissionCode = permissionCode;
            }


            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }


            public Integer getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
            }


            public Integer getVisibleFlag() {
                return visibleFlag;
            }

            public void setVisibleFlag(Integer visibleFlag) {
                this.visibleFlag = visibleFlag;
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
     public MenuDTO(){this.parentId=0L;this.sortOrder=0;this.visibleFlag=1;this.status="ENABLED";} 
}
