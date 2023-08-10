package com.example.post.service;

import com.example.post.config.security.jwt.JwtTokenProperty;
import com.example.post.config.security.jwt.JwtTokenUtils;
import com.example.post.domain.customer.dto.request.CustomerCreateReq;
import com.example.post.domain.customer.entity.Customer;
import com.example.post.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProperty jwtTokenProperty;


    public void signup(CustomerCreateReq req) {
        customerRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    public String signIn(String email, String password) {
        Customer customer = getCustomerOrException(email);
        validatePassword(password, customer.getPassword());
        return getToken(customer);
    }

    private Customer getCustomerOrException(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다"));
    }

    private void validatePassword(String password, String queryPassword) {
        if (encoder.matches(password, queryPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }

    private String getToken(Customer customer) {
        return JwtTokenUtils.generateToken(customer.getEmail(), jwtTokenProperty.getCustomerAccessTokenExpiration());
    }
}
