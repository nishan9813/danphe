package com.example.client.security;

import com.example.common.config.ExternalConfigProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {

    public static final String REFRESH_TOKEN_COOKIE = "refresh_token";
    static final String ACCESS_TOKEN_COOKIE = "access_token";
    private final ExternalConfigProvider externalConfig;

    @Value("${jwt.access-token.expiry-ms:900000}")
    private long accessTokenExpiryMs;

    @Value("${jwt.refresh-token.expiry-ms:604800000}")
    private long refreshTokenExpiryMs;

    @Value("false")
    private boolean cookieSecure;

    public JwtTokenProvider(ExternalConfigProvider externalConfig) {
        this.externalConfig = externalConfig;
    }

    public String generateAccessToken(String userId) {
        return buildToken(userId, accessTokenExpiryMs);
    }

    public String generateRefreshToken(String userId) {
        return buildToken(userId, refreshTokenExpiryMs);
    }

    public String extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public ResponseCookie accessTokenCookie(String token) {
        return cookie(ACCESS_TOKEN_COOKIE, token, Duration.ofMillis(accessTokenExpiryMs));
    }

    public ResponseCookie refreshTokenCookie(String token) {
        return cookie(REFRESH_TOKEN_COOKIE, token, Duration.ofMillis(refreshTokenExpiryMs));
    }

    public ResponseCookie clearAccessTokenCookie() {
        return cookie(ACCESS_TOKEN_COOKIE, "", Duration.ZERO);
    }

    public ResponseCookie clearRefreshCookie() {
        return cookie(REFRESH_TOKEN_COOKIE, "", Duration.ZERO);
    }

    private String buildToken(String userId, long expiryMs) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryMs))
                .signWith(signingKey())
                .compact();
    }


    private ResponseCookie cookie(String name, String value, Duration maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("None")
                .build();
    }


    private SecretKey signingKey() {
        String secret = externalConfig.get("jwt.secret");
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT secret key is not configured");
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
