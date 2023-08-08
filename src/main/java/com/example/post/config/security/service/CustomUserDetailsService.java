package com.example.post.config.security.service;

import com.example.post.config.security.CustomerPrincipal;
import com.example.post.domain.customer.dto.CustomerDto;
import com.example.post.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByEmail(username)
                .map(CustomerDto::fromExceptAddress)
                .map(CustomerPrincipal::from)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    }
}
