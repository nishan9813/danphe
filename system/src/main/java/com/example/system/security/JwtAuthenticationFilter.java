package com.example.system.security;

import com.example.common.context.ApplicationRequestContext;
import com.example.common.context.RequestContextHolder;
import com.example.common.modules.ApiRequestResourceType;
import com.example.service.api.SystemUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final SystemUserService userService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, SystemUserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        resolveToken(request)
                .filter(jwtTokenProvider::isValid)
                .ifPresent(token -> {
                    String userId = jwtTokenProvider.extractUserId(token);
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        userService.findById(userId).ifPresent(systemUser -> {
                            SystemUserPrincipal principal = new SystemUserPrincipal(systemUser);
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                    principal, null, principal.getAuthorities());
                            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(auth);

                            ApplicationRequestContext ctx = new ApplicationRequestContext();
                            ctx.setId(systemUser.getId());
                            ctx.setSubject(systemUser.getEmail());
                            ctx.setIp(MdcSetupFilter.resolveClientIp(request));
                            ctx.setSessionId(MDC.get("sessionId"));
                            ctx.setAgent(request.getHeader("User-Agent"));
                            ctx.setResource(ApiRequestResourceType.WEB_SYSTEM.getValue());
                            RequestContextHolder.set(ctx);

                            MDC.put("user", systemUser.getId());
                        });
                    }
                });
        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        Optional<String> fromCookie = extractTokenFromCookie(request, JwtTokenProvider.ACCESS_TOKEN_COOKIE);
        if (fromCookie.isPresent()) {
            return fromCookie;
        }
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return Optional.of(header.substring(7));
        }
        return Optional.empty();
    }

    private Optional<String> extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> cookieName.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
