package com.example.post.domain.customer.dto.request;

import com.example.post.domain.common.dto.AddressReq;
import com.example.post.domain.customer.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerCreateReq {

    @NotEmpty
    @ApiModelProperty(value = "이름")
    private String name;
    @NotEmpty
    @ApiModelProperty(value = "이메일")
    private String email;
    @NotEmpty
    @ApiModelProperty(value = "비밀번호")
    private String password;

    private AddressReq address;

    public Customer toEntity(String password) {
        return Customer.of(
                name,
                email,
                password,
                address.toEntity()
        );
    }
}
