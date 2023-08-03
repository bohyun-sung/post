package com.example.post.controller;

import com.example.post.config.response.Response;
import com.example.post.domain.reply.dto.request.ReplyCreateReq;
import com.example.post.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "{02. 댓글}")
@RequiredArgsConstructor
@RestController("/reply")
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/{id}")
    public Response<Void> createReply(@PathVariable Long id, @RequestBody ReplyCreateReq req) {
        replyService.createReply(id, req);
        return Response.success();
    }
}
