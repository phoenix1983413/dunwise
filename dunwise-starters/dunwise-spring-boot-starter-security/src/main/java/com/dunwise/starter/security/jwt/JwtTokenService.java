package com.dunwise.starter.security.jwt;

import com.dunwise.mica.core.constant.CommonConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 令牌服务
 */
@Component
public class JwtTokenService {

    @Value("${dunwise.security.jwt.secret:defaultSecretKeyForDevelopmentOnly12345678901234567890}")
    private String secret;

    @Value("${dunwise.security.jwt.expiration:86400000}")
    private long expiration;

    @Value("${dunwise.security.jwt.header:Authorization}")
    private String header;

    public String generateToken(String username, String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(username)
                .claim(CommonConstants.USER_ID, userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(CommonConstants.USER_ID, String.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(encodeBase64Secret(secret));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String encodeBase64Secret(String secret) {
        if (secret.length() < 32) {
            StringBuilder sb = new StringBuilder(secret);
            while (sb.length() < 32) {
                sb.append(secret);
            }
            secret = sb.toString();
        }
        return java.util.Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getHeader() {
        return header;
    }
}
