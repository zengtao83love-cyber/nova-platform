package com.zov.smart.nova.infra.web.filter;

import com.zov.smart.nova.common.context.RequestIdHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestIdFilterTest {

    @AfterEach
    void tearDown() {
        RequestIdHolder.clear();
        MDC.clear();
    }

    @Test
    void shouldUseInboundRequestIdAndClearContextAfterRequest() throws Exception {
        RequestIdFilter filter = new RequestIdFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader(RequestIdHolder.HEADER_NAME, " request-001 ");
        AtomicBoolean invoked = new AtomicBoolean(false);

        filter.doFilter(request, response, (ServletRequest servletRequest, ServletResponse servletResponse) -> {
            assertEquals("request-001", RequestIdHolder.get());
            assertEquals("request-001", MDC.get(RequestIdFilter.MDC_TRACE_ID_KEY));
            invoked.set(true);
        });

        assertTrue(invoked.get());
        assertEquals("request-001", response.getHeader(RequestIdHolder.HEADER_NAME));
        assertNull(RequestIdHolder.peek());
        assertNull(MDC.get(RequestIdFilter.MDC_TRACE_ID_KEY));
    }

    @Test
    void shouldGenerateRequestIdWhenHeaderMissing() throws Exception {
        RequestIdFilter filter = new RequestIdFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (ServletRequest servletRequest, ServletResponse servletResponse) -> {
            assertNotNull(RequestIdHolder.get());
            assertEquals(32, RequestIdHolder.get().length());
            assertEquals(RequestIdHolder.get(), MDC.get(RequestIdFilter.MDC_TRACE_ID_KEY));
        });

        assertNotNull(response.getHeader(RequestIdHolder.HEADER_NAME));
        assertEquals(32, response.getHeader(RequestIdHolder.HEADER_NAME).length());
        assertNull(RequestIdHolder.peek());
    }
}
