package com.fastfoodrestaraunt.backend.security.utils;

import com.fastfoodrestaraunt.backend.entity.UserCredential;
import com.fastfoodrestaraunt.backend.security.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig config;

    public String generateAccessToken(UserCredential user) {
        return Jwts.builder()
                .setSubject(user.getPhone())
                .claim("roles", List.of("ROLE_" + user.getRole().name()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(config.getAccessExpiration()) * 1000 * 60))
                .signWith(SignatureAlgorithm.forName(config.getAlgorithm()), config.getSecret().getBytes())
                .compact();
    }

    public String generateRefreshToken(UserCredential user) {
        return Jwts.builder()
                .setSubject(user.getPhone())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(config.getRefreshExpiration()) * 1000 * 60))
                .signWith(SignatureAlgorithm.forName(config.getAlgorithm()), config.getSecret().getBytes())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(config.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return Jwts.parser()
                .setSigningKey(config.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(config.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", List.class);
    }
}

