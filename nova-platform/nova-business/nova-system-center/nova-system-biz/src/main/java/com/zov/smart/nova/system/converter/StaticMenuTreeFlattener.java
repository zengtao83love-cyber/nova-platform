package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.system.api.dto.menu.MenuDTO;
import com.zov.smart.nova.system.api.vo.menu.MenuTreeVO;

import java.util.ArrayList;
import java.util.List;

/** Internal helper for converting tree VOs into facade DTO lists without Mapper access. */
public final class StaticMenuTreeFlattener {
    private StaticMenuTreeFlattener() { }
    public static List<MenuDTO> flatten(List<MenuTreeVO> tree) {
        List<MenuDTO> result = new ArrayList<MenuDTO>();
        if (tree != null) {
            for (MenuTreeVO node : tree) {
                flattenNode(node, result);
            }
        }
        return result;
    }
    private static void flattenNode(MenuTreeVO node, List<MenuDTO> result) {
        MenuDTO dto = new MenuDTO();
        dto.setId(node.getId()); dto.setParentId(node.getParentId()); dto.setMenuName(node.getMenuName());
        dto.setMenuType(node.getMenuType()); dto.setRoutePath(node.getRoutePath()); dto.setComponentPath(node.getComponentPath());
        dto.setPermissionCode(node.getPermissionCode()); dto.setIcon(node.getIcon()); dto.setSortOrder(node.getSortOrder());
        dto.setVisibleFlag(node.getVisibleFlag()); dto.setStatus(node.getStatus()); dto.setRemark(node.getRemark());
        dto.setCreatedAt(node.getCreatedAt()); dto.setUpdatedAt(node.getUpdatedAt());
        result.add(dto);
        if (node.getChildren() != null) {
            for (MenuTreeVO child : node.getChildren()) {
                flattenNode(child, result);
            }
        }
    }
}
