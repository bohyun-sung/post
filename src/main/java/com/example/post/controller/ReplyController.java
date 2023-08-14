package com.example.post.controller;

import com.example.post.config.response.Response;
import com.example.post.config.security.CustomerPrincipal;
import com.example.post.domain.reply.dto.request.ReplyCreateAndUpdateReq;
import com.example.post.service.ReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "{02. 댓글}")
@RequiredArgsConstructor
@RequestMapping("/v1/reply")
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping("/{postId}")
    public Response<Void> createReply(@PathVariable Long postId, @RequestBody @Validated ReplyCreateAndUpdateReq req , @AuthenticationPrincipal CustomerPrincipal principal) {
        replyService.createReply(postId, req, principal);
        return Response.success();
    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping("/{replyId}")
    public Response<Void> updateReply(@PathVariable Long replyId, @RequestBody @Validated ReplyCreateAndUpdateReq req, @AuthenticationPrincipal CustomerPrincipal principal) {
        replyService.updateReply(replyId, principal.getName(), req.getContent());
        return Response.success();
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{replyId}")
    public Response<Void> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return Response.success();
    }
}
