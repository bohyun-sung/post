package com.example.post.domain.post.dto.requset;

import com.example.post.domain.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateReq {

    private String title;
    private String content;

    public PostDto toDto() {
        return PostDto.of(
                title,
                content
        );
    }
}
