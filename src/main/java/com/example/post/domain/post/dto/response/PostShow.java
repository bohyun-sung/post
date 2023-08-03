package com.example.post.domain.post.dto.response;

import com.example.post.domain.post.dto.PostDto;
import java.time.LocalDateTime;
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

    public static PostShow from(PostDto dto) {
        return new PostShow(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getRgdt(),
                dto.getUpdt()
        );
    }

}
