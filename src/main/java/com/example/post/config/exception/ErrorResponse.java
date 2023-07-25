package com.example.post.config.exception;

import java.util.Map;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final boolean result = false;
    private final FailResponse fail;
    private Map<String, Object> data;


    public static ErrorResponse getFailResult(Integer code, String msg) {
        return new ErrorResponse(code, msg);
    }

    public static ErrorResponse getFailResult(Integer code, String msg, Map<String, Object> data) {
        return new ErrorResponse(code, msg, data);
    }

    private ErrorResponse(Integer code, String msg) {
        this.fail = new FailResponse(code, msg);
    }

    private ErrorResponse(Integer code, String msg, Map<String, Object> data) {
        this.fail = new FailResponse(code, msg);
        this.data = data;
    }
}
