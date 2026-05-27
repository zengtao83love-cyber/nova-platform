package com.zov.smart.nova.infra.web.filter;

import com.zov.smart.nova.common.context.RequestIdHolder;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RequestId / TraceId entrance filter.
 *
 * <p>The platform standardizes {@code X-Request-Id} as the inbound and outbound
 * request identifier. The same value is stored in {@link RequestIdHolder} and
 * in MDC key {@code traceId}; both are cleared in {@code finally} to prevent
 * thread-pool context leakage.</p>
 */
public class RequestIdFilter extends OncePerRequestFilter implements Ordered {

    public static final String MDC_TRACE_ID_KEY = "traceId";
    private static final int MAX_REQUEST_ID_LENGTH = 128;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestId = normalize(request.getHeader(RequestIdHolder.HEADER_NAME));
        RequestIdHolder.set(requestId);
        String traceId = RequestIdHolder.get();
        MDC.put(MDC_TRACE_ID_KEY, traceId);
        response.setHeader(RequestIdHolder.HEADER_NAME, traceId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_TRACE_ID_KEY);
            RequestIdHolder.clear();
        }
    }

    private static String normalize(String candidate) {
        if (!StringUtils.hasText(candidate)) {
            return null;
        }
        String trimmed = candidate.trim();
        if (trimmed.length() > MAX_REQUEST_ID_LENGTH) {
            return trimmed.substring(0, MAX_REQUEST_ID_LENGTH);
        }
        return trimmed;
    }
}
