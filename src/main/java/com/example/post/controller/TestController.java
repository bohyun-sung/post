package com.example.post.controller;

import com.example.post.config.response.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"[01] TEST"})
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/init")
    public Response<String> init() {
        return Response.success("Hello");
    }

    @GetMapping("/init2")
    public Response<Void> init2() {
        return Response.success();
    }
}
