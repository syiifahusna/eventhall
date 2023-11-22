package com.example.eventhall.security;

import com.example.eventhall.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig{

    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/error","/css/*","/js/*","/fonts/*","/images/*").permitAll()
                .requestMatchers("/",
                                        "/home",
                                        "/about",
                                        "/location",
                                        "/news",
                                        "/register",
                                        "/login",
                                        "/logoutsuccess"
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
                    .logoutSuccessUrl("/logoutsuccess")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                .csrf();


        return httpSecurity.build();

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provide = new DaoAuthenticationProvider();
        provide.setPasswordEncoder(passwordEncoder);
        provide.setUserDetailsService(userService);

        return provide;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
