package com.example.post.domain.post.dto;

import com.example.post.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostDto {
    private final String title;
    private final String content;

    private PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostDto of(String title, String content){
        return new PostDto(title, content);
    }

    public Post toEntity(){
        return Post.of(
                title,
                content
        );
    }

    public static PostDto from(Post entity) {
        return PostDto.of(
                entity.getTitle(),
                entity.getContent()
        );
    }
}
