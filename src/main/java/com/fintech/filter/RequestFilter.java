package com.fintech.filter;

import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestFilter implements Filter {
    private static final String SESSION_ID_HEADER = "X-Session-Id";
    private static final String MDC_SESSION_ID = "sessionId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String sessionId = httpRequest.getHeader(SESSION_ID_HEADER);
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_SESSION_ID, sessionId);
        chain.doFilter(request, response);
        MDC.remove(MDC_SESSION_ID);
    }
}
