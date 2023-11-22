package com.example.eventhall.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/",
                                        "/home",
                                        "/about",
                                        "/location",
                                        "/news",
                                        "/register",
                                        "/login"
                                ).anonymous()
                .requestMatchers("/u/*").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/u/home")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/logout")
                .and()
                .csrf().disable();


        return httpSecurity.build();

    }
}
