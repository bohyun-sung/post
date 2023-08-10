package com.example.post.controller;

import com.example.post.config.response.Response;
import com.example.post.domain.customer.dto.request.CustomerCreateReq;
import com.example.post.domain.customer.dto.request.CustomerSignInReq;
import com.example.post.domain.customer.dto.response.SignInRes;
import com.example.post.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "{03. 회원}")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public Response<Void> signUp(@RequestBody CustomerCreateReq req) {
        customerService.signup(req);
        return Response.success();
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/sign-in")
    public Response<SignInRes> signIn(@RequestBody CustomerSignInReq req) {
        return Response.success(SignInRes.from(customerService.signIn(req.getEmail(), req.getPassword())));
    }
}
