package com.example.post.controller;

import com.example.post.config.response.Response;
import com.example.post.domain.post.dto.requset.PostCreateReq;
import com.example.post.domain.post.dto.response.PostRes;
import com.example.post.service.PostService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "{01.게시판}")
@RequiredArgsConstructor
@RestController(value = "/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public Response<Page<PostRes>> searchPost(
            @PageableDefault(size = 10, sort = "rgdt", direction = Direction.DESC) Pageable pageable) {
        return Response.success(postService.searchPost(pageable).map(PostRes::from));
    }

    @PostMapping
    public Response<Void> createPost(@RequestBody PostCreateReq req) {
        postService.createPost(req.toDto());
        return Response.success();
    }
}
