package com.example.post.domain.reply.dto.response;

import com.example.post.domain.post.dto.response.ReplyDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyShowRes {
    private String writer;
    private String content;
    private LocalDateTime rgdt;

    public static ReplyShowRes from(ReplyDto dto) {
        return new ReplyShowRes(
                dto.getWriter(),
                dto.getContent(),
                dto.getRgdt()
        );
    }
}
