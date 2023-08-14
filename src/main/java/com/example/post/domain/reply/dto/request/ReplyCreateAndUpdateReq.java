package com.example.post.domain.reply.dto.request;

import com.example.post.domain.post.entity.Post;
import com.example.post.domain.reply.entity.Reply;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyCreateAndUpdateReq {

    @NotEmpty
    @ApiModelProperty(value = "내용")
    private String content;

    public Reply toEntity(Post post, String writer) {
        return Reply.of(writer, content, post);
    }
}
