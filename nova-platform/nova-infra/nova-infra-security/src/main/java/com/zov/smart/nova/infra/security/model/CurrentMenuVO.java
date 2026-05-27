package com.zov.smart.nova.infra.security.model;

import com.zov.smart.nova.data.access.system.entity.SysMenuDO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrentMenuVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuType;
    private String routePath;
    private String componentPath;
    private String icon;
    private Integer sortOrder;
    private List<CurrentMenuVO> children = new ArrayList<CurrentMenuVO>();

    public static CurrentMenuVO from(SysMenuDO menu) {
        CurrentMenuVO vo = new CurrentMenuVO();
        vo.setId(menu.getId()); vo.setParentId(menu.getParentId()); vo.setMenuName(menu.getMenuName());
        vo.setMenuType(menu.getMenuType() == null ? null : menu.getMenuType().getCode());
        vo.setRoutePath(menu.getRoutePath()); vo.setComponentPath(menu.getComponentPath()); vo.setIcon(menu.getIcon());
        vo.setSortOrder(menu.getSortOrder() == null ? 0 : menu.getSortOrder());
        return vo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public String getMenuType() { return menuType; }
    public void setMenuType(String menuType) { this.menuType = menuType; }
    public String getRoutePath() { return routePath; }
    public void setRoutePath(String routePath) { this.routePath = routePath; }
    public String getComponentPath() { return componentPath; }
    public void setComponentPath(String componentPath) { this.componentPath = componentPath; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public List<CurrentMenuVO> getChildren() { return children; }
    public void setChildren(List<CurrentMenuVO> children) { this.children = children == null ? new ArrayList<CurrentMenuVO>() : children; }
}
