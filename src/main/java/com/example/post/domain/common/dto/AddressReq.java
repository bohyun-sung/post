package com.example.post.domain.common.dto;

import com.example.post.domain.common.entity.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressReq {

    private String zipcode;
    private String address1;
    private String address2;

    public Address toEntity() {
        return Address.of(
                zipcode,
                address1,
                address2
        );
    }
}
