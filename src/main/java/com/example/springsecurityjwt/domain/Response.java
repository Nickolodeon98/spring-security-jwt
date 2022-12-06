package com.example.springsecurityjwt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Response<T> {

    private T resultBody;
    public static <T> Response<T> success(T body) {

        return (Response<T>) Response.builder().resultBody(body).build();
    }
}
