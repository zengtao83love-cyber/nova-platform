package com.zov.smart.nova.system.api.contract;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.facade.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FacadeContractTest {
    @Test
    void requiredFacadeTypesExist() {
        assertNotNull(UserFacade.class);
        assertNotNull(RoleFacade.class);
        assertNotNull(MenuFacade.class);
        assertNotNull(PermissionFacade.class);
        assertNotNull(DictFacade.class);
        assertNotNull(ConfigFacade.class);
        assertNotNull(LogFacade.class);
        assertNotNull(EnumFacade.class);
    }

    @Test
    void userFacadeContainsSpecRequiredMethods() throws Exception {
        Set<String> methods = new HashSet<String>();
        for (Method method : UserFacade.class.getDeclaredMethods()) {
            methods.add(method.getName());
        }
        assertTrue(methods.contains("getUserById"));
        assertTrue(methods.contains("getUserByUsername"));
        assertTrue(methods.contains("listUsersByIds"));
        assertTrue(methods.contains("existsUser"));
        assertTrue(methods.contains("isUserEnabled"));
    }

    @Test
    void pageResultCanBeFacadeReturnType() throws Exception {
        Method method = UserFacade.class.getDeclaredMethod("pageUsers", com.zov.smart.nova.system.api.query.user.UserPageQuery.class);
        assertEquals(PageResult.class, method.getReturnType());
    }
}
