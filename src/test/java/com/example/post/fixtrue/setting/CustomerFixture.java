package com.example.post.fixtrue.setting;

import com.example.post.config.security.CustomerPrincipal;
import com.example.post.domain.common.dto.AddressDto;
import com.example.post.domain.common.entity.Address;
import com.example.post.domain.customer.dto.CustomerDto;
import com.example.post.domain.customer.entity.Customer;
import java.time.LocalDateTime;
import org.springframework.test.util.ReflectionTestUtils;

public class CustomerFixture {

    public static Customer createCustomer(String name, String email, String password, Address address) {
        Customer customer = Customer.of(name, email, password, address);
        ReflectionTestUtils.setField(customer, "id", 1L);
        return customer;
    }

    public static Customer createCustomer() {
        return createCustomer("보현", "bohyun@naver.com", "123", Address.of("우편번호", "주소1", "주소2"));
    }

    public static CustomerDto createCustomerDto() {
        return CustomerDto.of(
                1L,
                "보현",
                "bohyun@naver.com",
                "123",
                AddressDto.from(Address.of("우편번호","주소1","주소2")),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static CustomerPrincipal createCustomerPrincipal() {
        return CustomerPrincipal.from(createCustomerDto());
    }
}
