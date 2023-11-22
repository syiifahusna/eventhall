package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Guest {

    @Id
    @SequenceGenerator(
            name = "guest_seq",
            sequenceName = "guest_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guest_seq"
    )
    private Long id;
    private String name;
    private String email;
    private String gender;

    public Guest(String name, String email, String gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}
