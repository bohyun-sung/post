package com.example.post.domain.post.dto.requset;

import com.example.post.domain.post.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "게시물 등록, 수정 req")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateAndUpdateReq {

    @NotEmpty
    @ApiModelProperty(value = "제목")
    private String title;
    @NotEmpty
    @ApiModelProperty(value = "본문")
    private String content;

    public Post toEntity() {
        return Post.of(
                title,
                content
        );
    }
}
