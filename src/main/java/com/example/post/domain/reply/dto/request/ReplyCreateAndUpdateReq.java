package com.example.post.domain.reply.dto.request;

import com.example.post.domain.post.entity.Post;
import com.example.post.domain.reply.entity.Reply;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyCreateAndUpdateReq {
    private String writer;
    private String content;

    public Reply toEntity(Post post) {
        return Reply.of(writer, content, post);
    }
}
