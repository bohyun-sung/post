package com.example.post.controller;

import com.example.post.config.response.Response;
import com.example.post.domain.post.dto.requset.PostCreateAndUpdateReq;
import com.example.post.domain.post.dto.response.PostRes;
import com.example.post.domain.post.dto.response.PostShow;
import com.example.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "{01.게시판}")
@RequiredArgsConstructor
@RestController(value = "/post")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시판 목록 조회", notes = ""
            + "게시물 조회 Pagination 기능으로 10개 까지만 조회,"
            + "최근 등록일 순으로 조회")
    @GetMapping
    public Response<Page<PostRes>> indexPost(
            @PageableDefault(size = 10, sort = "rgdt", direction = Direction.DESC) Pageable pageable) {
        return Response.success(postService.indexPost(pageable).map(PostRes::from));
    }

    @ApiOperation(value = "게시물 조회")
    @GetMapping("/{id}")
    public Response<PostShow> showPost(@PathVariable Long id) {
        return Response.success(PostShow.from(postService.showPost(id)));
    }

    @ApiOperation(value = "게시물 등록")
    @PostMapping
    public Response<Void> createPost(@RequestBody PostCreateAndUpdateReq req) {
        postService.createPost(req);
        return Response.success();
    }

    @ApiOperation(value = "게시물 수정")
    @PatchMapping("/{id}")
    public Response<Void> updatePost(@PathVariable Long id, @RequestBody PostCreateAndUpdateReq req) {
        postService.updatePost(id, req);
        return Response.success();
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping("/{id}")
    public Response<Void> deltePost(@PathVariable Long id) {
        postService.deletePost(id);
        return Response.success();
    }
}
