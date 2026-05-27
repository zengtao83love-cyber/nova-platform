package com.zov.smart.nova.infra.guard.key;

import com.zov.smart.nova.common.context.LoginUserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GuardKeyGeneratorTest {

    @AfterEach
    void tearDown() {
        LoginUserContext.clear();
    }

    @Test
    void repeatKeyUsesUserIdAndDoesNotExposeRawArguments() {
        LoginUserContext.set(new LoginUserContext.CurrentUser(9L, "admin", "系统管理员", true));
        ProceedingJoinPoint point = mockPoint("Demo.save(..)", new Object[]{"password=secret"});
        String key = new GuardKeyGenerator().repeatSubmitKey("nova:guard:repeat-submit:", point, "");
        assertTrue(key.startsWith("nova:guard:repeat-submit:9:"));
        assertFalse(key.contains("secret"));
    }

    private ProceedingJoinPoint mockPoint(String signature, Object[] args) {
        ProceedingJoinPoint point = mock(ProceedingJoinPoint.class);
        Signature sign = mock(Signature.class);
        when(sign.toShortString()).thenReturn(signature);
        when(point.getSignature()).thenReturn(sign);
        when(point.getArgs()).thenReturn(args);
        return point;
    }
}
