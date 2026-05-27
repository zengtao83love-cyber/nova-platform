package com.zov.smart.nova.system.api.command.menu;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateMenuCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 64, message = "菜单名称长度不能超过64")
    private String menuName;

    @NotBlank(message = "菜单类型不能为空")
    @Pattern(regexp = "DIR|MENU|BUTTON", message = "菜单类型只能是DIR/MENU/BUTTON")
    private String menuType;

    @Size(max = 255, message = "路由地址长度不能超过255")
    private String routePath;

    @Size(max = 255, message = "组件路径长度不能超过255")
    private String componentPath;

    @Size(max = 128, message = "权限码长度不能超过128")
    @Pattern(regexp = "^$|^[a-zA-Z0-9_-]+:[a-zA-Z0-9_-]+:[a-zA-Z0-9_-]+$", message = "权限码格式必须是domain:resource:action")
    private String permissionCode;

    @Size(max = 64, message = "图标长度不能超过64")
    private String icon;

    @Min(value = 0, message = "排序不能小于0")
    private Integer sortOrder;

    @Min(value = 0, message = "显示标识只能为0或1")
    @Max(value = 1, message = "显示标识只能为0或1")
    private Integer visibleFlag;

    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "菜单状态只能是ENABLED或DISABLED")
    private String status;

    @Size(max = 255, message = "备注长度不能超过255")
    private String remark;




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
     public CreateMenuCommand(){this.parentId=0L;this.sortOrder=0;this.visibleFlag=1;this.status="ENABLED";} 
}
