package com.example.springsecurityjwt.configuration;


import com.example.springsecurityjwt.domain.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final long expiryDateMs = 1000L * 60 * 60;

    public String createToken(String username, UserRole userRole) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", userRole);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiryDateMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }
}
