package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;

/**
 * 系统菜单/按钮持久化对象，对应表 sys_menu。
 */
@TableName(value = "sys_menu", autoResultMap = true)
public class SysMenuDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    @TableField("parent_id")
    private Long parentId;

    @TableField("menu_name")
    private String menuName;

    @TableField(value = "menu_type", typeHandler = EnumCodeTypeHandler.class)
    private MenuTypeEnum menuType;

    @TableField("route_path")
    private String routePath;

    @TableField("component_path")
    private String componentPath;

    @TableField("permission_code")
    private String permissionCode;

    @TableField("icon")
    private String icon;

    @TableField("sort_order")
    private Integer sortOrder;

    @TableField("visible_flag")
    private Integer visibleFlag;

    @TableField(value = "status", typeHandler = EnumCodeTypeHandler.class)
    private MenuStatusEnum status;

    @TableField("remark")
    private String remark;

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public MenuTypeEnum getMenuType() { return menuType; }
    public void setMenuType(MenuTypeEnum menuType) { this.menuType = menuType; }
    public String getRoutePath() { return routePath; }
    public void setRoutePath(String routePath) { this.routePath = routePath; }
    public String getComponentPath() { return componentPath; }
    public void setComponentPath(String componentPath) { this.componentPath = componentPath; }
    public String getPermissionCode() { return permissionCode; }
    public void setPermissionCode(String permissionCode) { this.permissionCode = permissionCode; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public Integer getVisibleFlag() { return visibleFlag; }
    public void setVisibleFlag(Integer visibleFlag) { this.visibleFlag = visibleFlag; }
    public MenuStatusEnum getStatus() { return status; }
    public void setStatus(MenuStatusEnum status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    /**
     * BUTTON 类型菜单必须配置 permissionCode。该方法仅提供结构级自检，业务异常由 Biz 层抛出。
     */
    public boolean isButtonPermissionCodeValid() {
        if (menuType != MenuTypeEnum.BUTTON) {
            return true;
        }
        return permissionCode != null && !permissionCode.trim().isEmpty();
    }
}
