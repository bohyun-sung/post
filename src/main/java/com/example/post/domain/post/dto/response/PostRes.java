package com.example.post.domain.post.dto.response;

import com.example.post.domain.post.dto.PostDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRes {

    private final String title;
    private final String content;

    public static PostRes from(PostDto dto) {
        return new PostRes(
                dto.getTitle(),
                dto.getContent()
        );
    }
}
