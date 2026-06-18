package com.example.system.controller.auth;


import com.example.common.exception.AppException;
import com.example.common.exception.UnauthorizedException;
import com.example.common.response.SystemUserResponse;
import com.example.system.security.JwtTokenProvider;
import com.example.system.security.MdcSetupFilter;
import com.example.system.security.SystemUserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;


@Tag(name = "01 System Authentication")
@RestController
@Slf4j
@RequestMapping("/api/system/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtprovider;
    private final MdcSetupFilter mdcSetupFilter;

    public AuthController(AuthenticationManager authManager, JwtTokenProvider jwtprovider, MdcSetupFilter mdcSetupFilter) {
        this.authManager = authManager;
        this.jwtprovider = jwtprovider;
        this.mdcSetupFilter = mdcSetupFilter;
    }

    @PostMapping("/login")
    ResponseEntity<SystemUserResponse> login(@Validated @RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            SystemUserPrincipal principal = (SystemUserPrincipal) auth.getPrincipal();
            String userId = principal.getUser().getId();
            response.addHeader(HttpHeaders.SET_COOKIE,
                    jwtprovider.accessTokenCookie(jwtprovider.generateAccessToken(userId)).toString());
            response.addHeader(HttpHeaders.SET_COOKIE,
                    jwtprovider.refreshTokenCookie(jwtprovider.generateRefreshToken(userId)).toString());
            log.info("Login Successful: {}", principal.getUser().getEmail());
            return ResponseEntity.ok(SystemUserResponse.from(principal.getUser()));
        } catch (AuthenticationException e) {
            log.warn(
                    "Login Failed → email={} type={} msg={}",
                    request.email(),
                    e.getClass().getSimpleName(),
                    e.getMessage(),
                    e
            );

            throw new AppException("Invalid email or password");
        } catch (Exception e) {
            log.error("Auth service error during login", e);

            throw new AppException("Authentication server error");
        }
    }

    @PostMapping("/refresh")
    ResponseEntity<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractCookie(request, JwtTokenProvider.REFRESH_TOKEN_COOKIE)
                .orElseThrow(() -> new UnauthorizedException("Refresh token missing"));
        if (!jwtprovider.isValid(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        } try {
            String userId = jwtprovider.extractUserId(refreshToken);
            response.addHeader(HttpHeaders.SET_COOKIE,
                    jwtprovider.accessTokenCookie(jwtprovider.generateRefreshToken(userId)).toString());
        } catch (Exception e) {
            throw new UnauthorizedException("Token process failed");
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/logout")
    ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, jwtprovider.clearRefreshCookie().toString());
        response.addHeader(HttpHeaders.SET_COOKIE, jwtprovider.clearRefreshCookie().toString());
        response.addHeader(HttpHeaders.SET_COOKIE, mdcSetupFilter.clearSessionCookie().toString());
        return ResponseEntity.noContent().build();
    }


    private Optional<String> extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}