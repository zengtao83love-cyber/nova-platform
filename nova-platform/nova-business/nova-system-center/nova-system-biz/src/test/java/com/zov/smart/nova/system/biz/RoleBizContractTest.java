package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.system.biz.impl.RoleBizImpl;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleBizContractTest {
    @Test
    void writeMethodsDeclareTransactionsInBizImpl() throws Exception {
        assertNotNull(RoleBizImpl.class.getMethod("createRole", com.zov.smart.nova.system.api.command.role.CreateRoleCommand.class).getAnnotation(Transactional.class));
        assertNotNull(RoleBizImpl.class.getMethod("updateRole", com.zov.smart.nova.system.api.command.role.UpdateRoleCommand.class).getAnnotation(Transactional.class));
        assertNotNull(RoleBizImpl.class.getMethod("deleteRole", Long.class).getAnnotation(Transactional.class));
        assertNotNull(RoleBizImpl.class.getMethod("assignMenus", com.zov.smart.nova.system.api.command.role.AssignRoleMenusCommand.class).getAnnotation(Transactional.class));
    }
}
