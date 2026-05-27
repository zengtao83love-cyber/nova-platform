package com.zov.smart.nova.infra.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zov.smart.nova.common.context.TraceContext;
import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.response.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    public AuthenticationEntryPointHandler(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }
    @Override public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(AuthErrorCode.AUTH_UNAUTHORIZED.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.fail(AuthErrorCode.AUTH_UNAUTHORIZED).withTraceId(TraceContext.get())));
    }
}
