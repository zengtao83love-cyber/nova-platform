package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.system.biz.impl.UserBizImpl;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserBizContractTest {
    @Test
    void writeMethodsDeclareTransactionsInBizImpl() throws Exception {
        assertNotNull(UserBizImpl.class.getMethod("createUser", com.zov.smart.nova.system.api.command.user.CreateUserCommand.class).getAnnotation(Transactional.class));
        assertNotNull(UserBizImpl.class.getMethod("updateUser", com.zov.smart.nova.system.api.command.user.UpdateUserCommand.class).getAnnotation(Transactional.class));
        assertNotNull(UserBizImpl.class.getMethod("deleteUser", Long.class).getAnnotation(Transactional.class));
        assertNotNull(UserBizImpl.class.getMethod("assignRoles", com.zov.smart.nova.system.api.command.user.AssignUserRolesCommand.class).getAnnotation(Transactional.class));
    }
}
