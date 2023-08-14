package com.example.post.domain.common.dto;

import com.example.post.domain.common.entity.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressReq {

    @ApiModelProperty(value = "우편번호")
    private String zipcode;
    @ApiModelProperty(value = "주소1")
    private String address1;
    @ApiModelProperty(value = "주소2")
    private String address2;

    public Address toEntity() {
        return Address.of(
                zipcode,
                address1,
                address2
        );
    }
}
