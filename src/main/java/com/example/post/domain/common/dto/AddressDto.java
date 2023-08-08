package com.example.post.domain.common.dto;

import com.example.post.domain.common.entity.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDto {
    private final Long id;
    private final String zipcode;
    private final String address1;
    private final String address2;

    public static AddressDto from(Address entity) {
        return new AddressDto(
                entity.getId(),
                entity.getZipcode(),
                entity.getAddress1(),
                entity.getAddress2()
        );
    }

}
