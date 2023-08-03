package com.example.post.domain.post.dto;

import com.example.post.domain.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime rgdt;
    private final LocalDateTime updt;

    private PostDto(Long id, String title, String content, LocalDateTime rgdt, LocalDateTime updt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rgdt = rgdt;
        this.updt = updt;
    }

    public static PostDto of(Long id, String title, String content, LocalDateTime rgdt, LocalDateTime updt){
        return new PostDto(id, title, content, rgdt, updt);
    }

    public Post toEntity(){
        return Post.of(
                title,
                content
        );
    }

    public static PostDto from(Post entity) {
        return PostDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getRgdt(),
                entity.getUpdt()
        );
    }
}
