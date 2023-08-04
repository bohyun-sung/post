package com.example.post.service;

import com.example.post.config.response.Response;
import com.example.post.domain.post.entity.Post;
import com.example.post.domain.post.repository.PostRepository;
import com.example.post.domain.reply.dto.request.ReplyCreateAndUpdateReq;
import com.example.post.domain.reply.entity.Reply;
import com.example.post.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void createReply(Long replyId, ReplyCreateAndUpdateReq req) {
        Post post = getPostOrException(replyId);
        Reply reply = req.toEntity(post);
        replyRepository.save(reply);
    }

    @Transactional
    public void updateReply(Long replyId, String writer, String content) {
        Reply reply = getReplyOrException(replyId);
        reply.modifyReply(writer, content);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        Reply reply = getReplyOrException(replyId);
        replyRepository.delete(reply);
    }

    /**
     * POST Entity 조회
     * TODO 에러 핸들링은 추후 구현
     * @param postId POST postId
     * @return POST Entity
     */
    private Post getPostOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다"));
    }

    /**
     * Reply Entity 조회
     * @param replyId Reply replyId
     * @return Reply Entity
     */
    private Reply getReplyOrException(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
    }

}
