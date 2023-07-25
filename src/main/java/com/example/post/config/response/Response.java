package com.example.post.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response<T> {

    private final boolean result = true;
    private T data;

    public Response(T data) {
        this.data = data;
    }

    public static Response<Void> success() {
        return new Response<>(null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public String toStream() {
        if(data == null){
            return "{" +
                    "\"result\":" + "\"" + result + "\"," +
                    "\"data\":" +  null +  "}";
        } else {
            return "{" +
                    "\"result\":" + "\"" + result + "\"," +
                    "\"data\":" + "\"" + data + "\"" + "}";
        }
    }
}
