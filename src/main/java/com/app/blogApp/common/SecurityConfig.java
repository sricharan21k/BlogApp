package com.app.blogApp.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.GET, "/*")
//                        .permitAll()
//                        .requestMatchers(HttpMethod.POST, "/users")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated());
        http.authorizeHttpRequests(requests -> requests
                .anyRequest()
                .permitAll());

        return http.build();
    }

}
