package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.domain.Response;
import com.example.springsecurityjwt.domain.dto.UserLoginRequest;
import com.example.springsecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Response<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.authenticate(userLoginRequest.getUsername());
        return Response.success(token);
    }

}
