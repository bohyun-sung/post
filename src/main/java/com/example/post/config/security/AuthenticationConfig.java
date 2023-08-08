package com.example.post.config.security;

import static com.example.post.config.security.RoleType.CUSTOMER_ROLE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.example.post.config.security.jwt.JwtTokenFilter;
import com.example.post.config.security.jwt.JwtTokenProperty;
import com.example.post.config.security.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class AuthenticationConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource,
            CustomUserDetailsService customUserDetailsService) throws Exception {
        return http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                // login
                .formLogin(AbstractHttpConfigurer::disable)
                // http
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeRequests(authirize ->
                        authirize.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .mvcMatchers(GET).permitAll()
                                .mvcMatchers(POST, SecurityWhiteList.post)
//                                .mvcMatchers("/customer/**").hasRole(CUSTOMER_ROLE)
//                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
