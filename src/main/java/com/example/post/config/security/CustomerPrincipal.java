package com.example.post.config.security;

import com.example.post.domain.customer.dto.CustomerDto;
import java.util.Collection;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerPrincipal implements UserDetails {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;


    public static CustomerPrincipal of(Long id, String name, String email, String password) {
        return new CustomerPrincipal(
                id,
                name,
                email,
                password,
                getCustomerRole()
        );
    }
    public static CustomerPrincipal from(CustomerDto dto) {
        return CustomerPrincipal.of(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static Set<GrantedAuthority> getCustomerRole() {
        return Set.of(new SimpleGrantedAuthority(RoleType.CUSTOMER_ROLE));
    }

}
