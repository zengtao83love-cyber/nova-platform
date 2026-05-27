package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.command.menu.UpdateMenuCommand;
import com.zov.smart.nova.system.api.dto.menu.MenuDTO;
import com.zov.smart.nova.system.api.vo.menu.MenuTreeVO;
import com.zov.smart.nova.system.api.vo.menu.MenuVO;
import com.zov.smart.nova.system.support.StatusValidators;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Menu converter. It deliberately avoids exposing persistence-only fields. */
@Component
public class MenuConverter {

    public SysMenuDO toCreateDO(CreateMenuCommand command) {
        SysMenuDO entity = new SysMenuDO();
        entity.setParentId(command.getParentId() == null ? 0L : command.getParentId());
        entity.setMenuName(command.getMenuName());
        entity.setMenuType(StatusValidators.requiredMenuType(command.getMenuType()));
        entity.setRoutePath(command.getRoutePath());
        entity.setComponentPath(command.getComponentPath());
        entity.setPermissionCode(trimToNull(command.getPermissionCode()));
        entity.setIcon(command.getIcon());
        entity.setSortOrder(command.getSortOrder() == null ? 0 : command.getSortOrder());
        entity.setVisibleFlag(command.getVisibleFlag() == null ? 1 : command.getVisibleFlag());
        entity.setStatus(StatusValidators.menuStatusOrDefault(command.getStatus()));
        entity.setRemark(command.getRemark());
        return entity;
    }

    public void updateDO(UpdateMenuCommand command, SysMenuDO target) {
        target.setParentId(command.getParentId() == null ? 0L : command.getParentId());
        target.setMenuName(command.getMenuName());
        target.setMenuType(StatusValidators.requiredMenuType(command.getMenuType()));
        target.setRoutePath(command.getRoutePath());
        target.setComponentPath(command.getComponentPath());
        target.setPermissionCode(trimToNull(command.getPermissionCode()));
        target.setIcon(command.getIcon());
        target.setSortOrder(command.getSortOrder() == null ? 0 : command.getSortOrder());
        target.setVisibleFlag(command.getVisibleFlag() == null ? 1 : command.getVisibleFlag());
        if (command.getStatus() != null && !command.getStatus().trim().isEmpty()) {
            target.setStatus(StatusValidators.requiredMenuStatus(command.getStatus()));
        }
        target.setRemark(command.getRemark());
    }

    public MenuDTO toDTO(SysMenuDO source) {
        if (source == null) {
            return null;
        }
        MenuDTO dto = new MenuDTO();
        copyBase(source, dto);
        return dto;
    }

    public MenuVO toVO(SysMenuDO source) {
        if (source == null) {
            return null;
        }
        MenuVO vo = new MenuVO();
        vo.setId(source.getId());
        vo.setParentId(source.getParentId());
        vo.setMenuName(source.getMenuName());
        vo.setMenuType(toCode(source.getMenuType()));
        vo.setRoutePath(source.getRoutePath());
        vo.setComponentPath(source.getComponentPath());
        vo.setPermissionCode(source.getPermissionCode());
        vo.setIcon(source.getIcon());
        vo.setSortOrder(source.getSortOrder());
        vo.setVisibleFlag(source.getVisibleFlag());
        vo.setStatus(toCode(source.getStatus()));
        vo.setRemark(source.getRemark());
        vo.setCreatedAt(source.getCreatedAt());
        vo.setUpdatedAt(source.getUpdatedAt());
        return vo;
    }

    public MenuTreeVO toTreeVO(SysMenuDO source) {
        MenuTreeVO vo = new MenuTreeVO();
        vo.setId(source.getId());
        vo.setParentId(source.getParentId());
        vo.setMenuName(source.getMenuName());
        vo.setMenuType(toCode(source.getMenuType()));
        vo.setRoutePath(source.getRoutePath());
        vo.setComponentPath(source.getComponentPath());
        vo.setPermissionCode(source.getPermissionCode());
        vo.setIcon(source.getIcon());
        vo.setSortOrder(source.getSortOrder());
        vo.setVisibleFlag(source.getVisibleFlag());
        vo.setStatus(toCode(source.getStatus()));
        vo.setRemark(source.getRemark());
        vo.setCreatedAt(source.getCreatedAt());
        vo.setUpdatedAt(source.getUpdatedAt());
        vo.setChildren(new ArrayList<MenuTreeVO>());
        return vo;
    }

    public List<MenuDTO> toDTOList(List<SysMenuDO> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        List<MenuDTO> result = new ArrayList<MenuDTO>(source.size());
        for (SysMenuDO item : source) {
            result.add(toDTO(item));
        }
        return result;
    }

    private void copyBase(SysMenuDO source, MenuDTO dto) {
        dto.setId(source.getId());
        dto.setParentId(source.getParentId());
        dto.setMenuName(source.getMenuName());
        dto.setMenuType(toCode(source.getMenuType()));
        dto.setRoutePath(source.getRoutePath());
        dto.setComponentPath(source.getComponentPath());
        dto.setPermissionCode(source.getPermissionCode());
        dto.setIcon(source.getIcon());
        dto.setSortOrder(source.getSortOrder());
        dto.setVisibleFlag(source.getVisibleFlag());
        dto.setStatus(toCode(source.getStatus()));
        dto.setRemark(source.getRemark());
        dto.setCreatedAt(source.getCreatedAt());
        dto.setUpdatedAt(source.getUpdatedAt());
    }

    public String toCode(MenuTypeEnum value) {
        return value == null ? null : value.getCode();
    }

    public String toCode(MenuStatusEnum value) {
        return value == null ? null : value.getCode();
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
