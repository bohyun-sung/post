package com.example.post.service;

import com.example.post.domain.post.dto.PostDto;
import com.example.post.domain.post.dto.requset.PostCreateAndUpdateReq;
import com.example.post.domain.post.entity.Post;
import com.example.post.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public Page<PostDto> indexPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::from);
    }

    public PostDto showPost(Long id) {
        return PostDto.fromWithReply(getPostOrException(id));
    }

    @Transactional
    public void createPost(PostCreateAndUpdateReq req) {
        postRepository.save(req.toEntity());
    }

    @Transactional
    public void updatePost(Long id, PostCreateAndUpdateReq req) {
        Post post = getPostOrException(id);
        post.modifyPost(req.getTitle(), req.getContent());
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostOrException(id);
        postRepository.delete(post);
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
