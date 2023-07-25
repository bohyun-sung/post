package com.example.post.controller;

import com.example.post.config.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"[01] TEST"})
@RequestMapping("/test")
@RestController
public class TestController {

    @ApiOperation(value = "response test")
    @GetMapping("/init")
    public Response<String> init() {
        return Response.success("Hello");
    }

    @ApiOperation(value = "response test")
    @GetMapping("/init2")
    public Response<Void> init2() {
        return Response.success();
    }

    @ApiOperation(value = "error test")
    @GetMapping("/init3")
    public Response<Void> init3() {
        throw new IllegalArgumentException();
    }
}
