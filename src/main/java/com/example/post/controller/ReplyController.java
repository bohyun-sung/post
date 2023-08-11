package com.example.post.controller;

import com.example.post.config.response.Response;
import com.example.post.domain.reply.dto.request.ReplyCreateAndUpdateReq;
import com.example.post.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "{02. 댓글}")
@RequiredArgsConstructor
@RestController("/v1/reply")
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/{postId}")
    public Response<Void> createReply(@PathVariable Long postId, @RequestBody ReplyCreateAndUpdateReq req) {
        replyService.createReply(postId, req);
        return Response.success();
    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping("/{replyId}")
    public Response<Void> updateReply(@PathVariable Long replyId, @RequestBody ReplyCreateAndUpdateReq req) {
        replyService.updateReply(replyId, req.getWriter(), req.getContent());
        return Response.success();
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{replyId}")
    public Response<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return Response.success();
    }
}
