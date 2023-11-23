package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String tel;
    private LocalDate birthDate;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    //private Set<SimpleGrantedAuthority> authorities;

    @Transient
    private int age;


    public User(String name, String username, String password, String email, String tel, LocalDate birthDate, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.birthDate = birthDate;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public User(String name, String username, String password, String email, String tel, LocalDate birthDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.birthDate = birthDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    public int getAge() {
        age = (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        return age;
    }
}
