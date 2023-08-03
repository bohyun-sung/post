package com.example.post.domain.reply.dto.request;

import com.example.post.domain.reply.entity.Reply;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyCreateReq {
    private String wirter;
    private String content;

    public Reply toEntity() {
        return Reply.of(wirter, content);
    }
}
