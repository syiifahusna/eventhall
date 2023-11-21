package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

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
    private LocalDate date;

    public User(String name, String username, String password, String email, LocalDate date) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.date = date;
    }
}
