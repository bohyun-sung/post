package com.example.post.service;

import com.example.post.domain.post.dto.PostDto;
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

    public Page<PostDto> searchPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::from);
    }

    @Transactional
    public void createPost(PostDto dto) {
        postRepository.save(dto.toEntity());
    }


}
