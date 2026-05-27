package com.zov.smart.nova.common.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class LoginUserContextTest {
    @AfterEach
    void tearDown() {
        LoginUserContext.clear();
    }

    @Test
    void shouldSetGetAndClearCurrentUser() {
        LoginUserContext.CurrentUser user = new LoginUserContext.CurrentUser(1L, "admin", "Administrator", true);

        LoginUserContext.set(user);

        assertTrue(LoginUserContext.hasLoginUser());
        assertSame(user, LoginUserContext.get());
        assertEquals(1L, LoginUserContext.currentUserId());
        assertTrue(LoginUserContext.get().isSuperAdmin());

        LoginUserContext.clear();

        assertFalse(LoginUserContext.hasLoginUser());
        assertNull(LoginUserContext.get());
    }

    @Test
    void shouldNotInheritCurrentUserToChildThread() throws InterruptedException {
        LoginUserContext.set(new LoginUserContext.CurrentUser(7L, "operator", "Operator", false));
        AtomicReference<LoginUserContext.CurrentUser> childThreadUser = new AtomicReference<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                childThreadUser.set(LoginUserContext.get());
            }
        });
        thread.start();
        thread.join();

        assertNull(childThreadUser.get());
        assertEquals(7L, LoginUserContext.currentUserId());
    }

    @Test
    void shouldConvertLoginUserToCurrentUser() {
        LoginUser loginUser = new LoginUser(2L, "tester", "Test User", false);

        LoginUserContext.set(loginUser);

        assertEquals(2L, LoginUserContext.get().getUserId());
        assertEquals("tester", LoginUserContext.get().getUsername());
        assertFalse(LoginUserContext.get().isSuperAdmin());
    }
}
