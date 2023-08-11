package com.example.post.domain.customer.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInRes {
    private String token;

    public static SignInRes from(String token) {
        return new SignInRes("Bearer " + token);
    }
}
