package com.example.post.domain.post.dto.response;

import com.example.post.domain.post.dto.PostDto;
import com.example.post.domain.reply.dto.response.ReplyShowRes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostShow {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime rgdt;
    private final LocalDateTime updt;
    private final List<ReplyShowRes> replys;

    public static PostShow from(PostDto dto) {
        return new PostShow(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getRgdt(),
                dto.getUpdt(),
                dto.getReplyDtos().stream().map(ReplyShowRes::from).collect(Collectors.toList())
        );
    }

}
