package dev.omkar.usermanagement.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/auth/register").build();
    }
    @Bean
    public GroupedOpenApi customApi1() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/auth/login").build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
