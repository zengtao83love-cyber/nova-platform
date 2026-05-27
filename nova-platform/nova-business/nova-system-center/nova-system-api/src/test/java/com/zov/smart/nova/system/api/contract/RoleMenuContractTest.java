package com.zov.smart.nova.system.api.contract;

import com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.menu.CreateMenuCommand;
import com.zov.smart.nova.system.api.query.menu.MenuTreeQuery;
import com.zov.smart.nova.system.api.vo.menu.MenuTreeVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleMenuContractTest {
    @Test
    void roleDefaultsFollowSpec() {
        CreateRoleCommand command = new CreateRoleCommand();
        assertEquals(Integer.valueOf(0), command.getSortOrder());
        assertEquals("ENABLED", command.getStatus());
        AssignRoleMenusCommand assign = new AssignRoleMenusCommand();
        assertNotNull(assign.getMenuIds());
    }

    @Test
    void menuDefaultsAndTreeChildrenAreStable() {
        CreateMenuCommand command = new CreateMenuCommand();
        assertEquals(Long.valueOf(0L), command.getParentId());
        assertEquals(Integer.valueOf(1), command.getVisibleFlag());
        assertEquals("ENABLED", command.getStatus());
        MenuTreeQuery query = new MenuTreeQuery();
        assertEquals(Boolean.FALSE, query.getIncludeButton());
        MenuTreeVO vo = new MenuTreeVO();
        assertNotNull(vo.getChildren());
    }
}
