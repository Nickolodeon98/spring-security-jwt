package com.example.springsecurityjwt.configuration;

import com.example.springsecurityjwt.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // 요청에서 헤더로부터 토큰을 가져오기 위해 헤더를 불러온다.ㅓ

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Authorization 이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        try {
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e) {
            log.error("토큰이 없습니다.");
            throw new RuntimeException(e);
        }

        if (!jwtTokenProvider.validateToken(token)) {
            log.error("토큰이 유효하지 않습니다.");
            filterChain.doFilter(request, response);  // 토큰을 찾았다. 이제 이 토큰이 유효한지 확인해보자.
            return;
        }


        /* 토큰 존재 여부와 유효성의 검사를 마친 뒤에 SecurityContextHolder 에 인증 정보를 저장한다. */
        SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
        filterChain.doFilter(request, response);
    }
}
