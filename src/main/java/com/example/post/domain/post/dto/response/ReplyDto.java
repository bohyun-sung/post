package com.example.post.domain.post.dto.response;

import com.example.post.domain.reply.entity.Reply;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyDto {

    private Long id;
    private String writer;
    private String content;
    private LocalDateTime rgdt;
    private LocalDateTime updt;

    private ReplyDto(Long id, String writer, String content, LocalDateTime rgdt,
            LocalDateTime updt) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.rgdt = rgdt;
        this.updt = updt;
    }

    public static ReplyDto of(Long id, String writer, String content, LocalDateTime rgdt,
            LocalDateTime updt) {
        return new ReplyDto(id, writer, content, rgdt, updt);
    }

    public static ReplyDto from(Reply entity) {
        return ReplyDto.of(
                entity.getId(),
                entity.getWriter(),
                entity.getContent(),
                entity.getRgdt(),
                entity.getUpdt()
        );
    }
}
