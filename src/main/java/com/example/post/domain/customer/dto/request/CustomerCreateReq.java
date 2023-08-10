package com.example.post.domain.customer.dto.request;

import com.example.post.domain.common.dto.AddressReq;
import com.example.post.domain.customer.entity.Customer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerCreateReq {

    private String name;
    private String email;
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
