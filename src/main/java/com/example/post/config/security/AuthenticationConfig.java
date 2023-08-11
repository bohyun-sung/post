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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                                // GET 접근 허용
                                .mvcMatchers(GET).permitAll()
                                // POST 접근 불가
                                .mvcMatchers(POST, SecurityWhiteList.post).permitAll()
                                .mvcMatchers("/v1/**").hasRole(CUSTOMER_ROLE)
                                // 스웨거 페이지 접근 허용
                                .antMatchers("/v2/api-docs", "/v3/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger*/**").permitAll() // 스웨거 페이지 접근 허용
                                // 그외의 페이지 접근 불가
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
