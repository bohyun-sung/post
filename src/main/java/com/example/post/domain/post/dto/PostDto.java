package com.example.post.domain.post.dto;

import com.example.post.domain.post.dto.response.ReplyDto;
import com.example.post.domain.post.entity.Post;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostDto {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime rgdt;
    private final LocalDateTime updt;
    private Set<ReplyDto> replyDtos;

    private PostDto(Long id, String title, String content, LocalDateTime rgdt, LocalDateTime updt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rgdt = rgdt;
        this.updt = updt;
    }

    public PostDto(Long id, String title, String content, LocalDateTime rgdt,
            LocalDateTime updt, Set<ReplyDto> replyDtos) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rgdt = rgdt;
        this.updt = updt;
        this.replyDtos = replyDtos;
    }

    public static PostDto of(Long id, String title, String content, LocalDateTime rgdt,
            LocalDateTime updt) {
        return new PostDto(id, title, content, rgdt, updt);
    }

    public static PostDto of(Long id, String title, String content, LocalDateTime rgdt,
            LocalDateTime updt, Set<ReplyDto> replyDtos) {
        return new PostDto(id, title, content, rgdt, updt, replyDtos);
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

    public static PostDto fromWithReply(Post entity) {
        return PostDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getRgdt(),
                entity.getUpdt(),
                entity.getReplys().stream().map(ReplyDto::from).collect(Collectors.toSet())
        );
    }

}
