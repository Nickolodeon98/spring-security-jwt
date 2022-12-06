package com.example.springsecurityjwt.configuration;


import com.example.springsecurityjwt.domain.entity.UserRole;
import com.example.springsecurityjwt.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final long expiryDateMs = 1000L * 60 * 60;
    private final UserService userService;


    public Jws<Claims> extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public String getUserName(String jwtToken) {
        return extractClaims(jwtToken).getBody().getSubject();

        // 이렇게 해도 되고 extractClaims 메서드 만들어서 클레임 추출 한 다음에 getUserName 또 따로 해도 됩니다.
    }

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

    /* 토큰이 생성된 후 AuthenticationFilter 를 통해 인증을 받은 후에 토큰의 인증정보를 SecurityContextHolder 에 저장하기 위해
     * Authentication 객체를 만들어주는 메서드입니다. */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(this.getUserName(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        return !extractClaims(token).getBody().getExpiration().before(new Date());
    }
}
