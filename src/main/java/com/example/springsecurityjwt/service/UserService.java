package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.configuration.JwtTokenProvider;
import com.example.springsecurityjwt.domain.entity.User;
import com.example.springsecurityjwt.domain.entity.UserRole;
import com.example.springsecurityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String authenticate(String username) {
//        UserDetails user = userRepository.findByUsername(username);
        UserRole role = UserRole.USER;

        return jwtTokenProvider.createToken(username, role);
    }
}
