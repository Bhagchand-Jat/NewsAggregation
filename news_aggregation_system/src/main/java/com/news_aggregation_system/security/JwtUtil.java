package com.news_aggregation_system.security;

import com.news_aggregation_system.service.common.Constant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {

    private static final String SECRET = Optional.ofNullable(System.getenv("JWT_SECRET"))
            .orElseThrow(() -> new IllegalStateException(Constant.JWT_SECRET_NOT_SET));
    private static final long ACCESS_EXPIRY = 24 * 60 * 60 * 1000;
    private static final long REFRESH_EXPIRY = 7 * 24 * 60 * 60 * 1000;
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public JwtUtil() {
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(Map.of("userId", ((CustomUserDetails) userDetails).getUserId()), userDetails.getUsername(), ACCESS_EXPIRY);
    }
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(Map.of(), userDetails.getUsername(), REFRESH_EXPIRY);
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiry) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails ud) {
        return extractUsername(token).equals(ud.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return parse(token).getBody().getExpiration().before(Date.from(Instant.now()));
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
