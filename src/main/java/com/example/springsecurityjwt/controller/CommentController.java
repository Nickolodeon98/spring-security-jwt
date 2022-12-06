package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.domain.dto.CommentRequestDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @PostMapping("/new")
    public String createComment(@RequestBody CommentRequestDto commentRequestDto, Authentication authentication) {
        return authentication.getName() + " 리뷰 등록이 완료되었습니다.";
    }
}
