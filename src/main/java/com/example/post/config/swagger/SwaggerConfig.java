package com.example.post.config.swagger;


import static java.util.Collections.singleton;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.classmate.TypeResolver;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.OAS_30)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Pageable.class),
                        typeResolver.resolve(RequestPageableModel.class)))
                .useDefaultResponseMessages(true)
                .produces(singleton(APPLICATION_JSON_VALUE))
                .directModelSubstitute(byte[].class, String.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.post.controller"))
                .paths(PathSelectors.any())
                .build()
                .groupName("게시판 연습")
                .securitySchemes(List.of(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("POST 연습 프젝")
                .description("처음은 쉬운거 부터.")
                .version("0.1")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(HttpHeaders.AUTHORIZATION.toLowerCase(), authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION.toLowerCase(), HttpHeaders.AUTHORIZATION.toLowerCase(), "header");
    }
}
