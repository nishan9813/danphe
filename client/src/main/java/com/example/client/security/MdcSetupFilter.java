package com.example.client.security;

import com.example.common.context.RequestContextHolder;
import com.example.common.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcSetupFilter extends OncePerRequestFilter {

    public static final String SESSION_COOKIE_NAME = "SESSION_TRACE";
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String REQUEST_ID_MDC_KEY = "requestId";
    private static final String SESSION_ID_MDC_KEY = "sessionId";
    private static final String IP_MDC_KEY = "ip";
    private static final int SESSION_COOKIE_MAX_AGE_SECONDS = 30 * 24 * 60 * 60;

    static String resolveClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isBlank()) {
            requestId = StringUtils.generateUUID();
        }
        MDC.put(REQUEST_ID_MDC_KEY, requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        String sessionId = extractSessionCookie(request).orElse(null);
        if (sessionId == null) {
            sessionId = StringUtils.generateUUID();
            response.addHeader(HttpHeaders.SET_COOKIE, buildSessionCookie(sessionId).toString());
        }
        MDC.put(SESSION_ID_MDC_KEY, sessionId);
        MDC.put(IP_MDC_KEY, resolveClientIp(request));

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID_MDC_KEY);
            MDC.remove(SESSION_ID_MDC_KEY);
            MDC.remove(IP_MDC_KEY);
            MDC.remove("user");
            RequestContextHolder.clear();
        }
    }

    public ResponseCookie clearSessionCookie() {
        return ResponseCookie.from(SESSION_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
    }

    private Optional<String> extractSessionCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> SESSION_COOKIE_NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private ResponseCookie buildSessionCookie(String value) {
        return ResponseCookie.from(SESSION_COOKIE_NAME, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(SESSION_COOKIE_MAX_AGE_SECONDS)
                .sameSite("None")
                .build();
    }
}
