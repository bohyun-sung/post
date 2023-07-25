package com.example.post.config.exception;

import lombok.Getter;

@Getter
public class FailResponse {

    private String code;
    private String msg;

    public FailResponse(Integer code, String msg) {
        this.code = code.toString();
        this.msg = msg;
    }
}
