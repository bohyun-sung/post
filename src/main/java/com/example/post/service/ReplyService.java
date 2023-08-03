package com.example.post.service;

import com.example.post.domain.post.entity.Post;
import com.example.post.domain.post.repository.PostRepository;
import com.example.post.domain.reply.dto.request.ReplyCreateReq;
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
    public void createReply(Long replyId, ReplyCreateReq req) {
        Post post = getPostOrException(replyId);
        post.addReply(req.toEntity());
    }

    /**
     * POST Entity 조회
     * TODO 에러 핸들링은 추후 구현
     * @param id POST id
     * @return POST Entity
     */
    private Post getPostOrException(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다"));
    }
}
