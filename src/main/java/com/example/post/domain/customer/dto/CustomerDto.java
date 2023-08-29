package com.example.post.domain.customer.dto;

import static java.util.Objects.isNull;

import com.example.post.domain.common.dto.AddressDto;
import com.example.post.domain.customer.entity.Customer;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDto {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final AddressDto addressDto;
    private final LocalDateTime rgdt;
    private final LocalDateTime updt;

    public static CustomerDto from(Customer entity) {
        return new CustomerDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                isNull(entity.getAddress()) ? null : AddressDto.from(entity.getAddress()),
                entity.getRgdt(),
                entity.getUpdt()
        );
    }

    public static CustomerDto fromExceptAddress(Customer entity) {
        return new CustomerDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                null,
                entity.getRgdt(),
                entity.getUpdt()
        );
    }

    public static CustomerDto of(Long id, String name, String email, String password,
            AddressDto addressDto, LocalDateTime rgdt, LocalDateTime updt) {
        return new CustomerDto(id,name,email,password, addressDto, rgdt, updt);
    }
}
