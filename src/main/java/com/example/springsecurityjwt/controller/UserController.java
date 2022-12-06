package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.domain.Response;
import com.example.springsecurityjwt.domain.dto.UserLoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jwt")
public class UserController {

    @PostMapping("/login")
    public Response<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        return Response.success("");
    }

}
