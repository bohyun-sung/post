package com.example.post.domain.customer.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerSignInReq {

    @NotEmpty
    @ApiModelProperty(value = "이메일")
    private String email;
    @NotEmpty
    @ApiModelProperty(value = "비밀번호")
    private String password;
}
