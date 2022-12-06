package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.configuration.JwtTokenProvider;
import com.example.springsecurityjwt.domain.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;

    public String authenticate(String username, String password) {

        UserRole role = UserRole.USER;

        String token = jwtTokenProvider.createToken(username, role);
        return token;
    }
}
