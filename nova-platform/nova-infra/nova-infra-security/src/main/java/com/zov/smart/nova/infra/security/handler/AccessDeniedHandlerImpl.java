package com.zov.smart.nova.infra.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zov.smart.nova.common.context.TraceContext;
import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.response.Result;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    public AccessDeniedHandlerImpl(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }
    @Override public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(AuthErrorCode.AUTH_FORBIDDEN.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.fail(AuthErrorCode.AUTH_FORBIDDEN).withTraceId(TraceContext.get())));
    }
}
