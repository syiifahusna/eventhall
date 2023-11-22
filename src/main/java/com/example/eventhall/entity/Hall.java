package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int id;
    private String hallName;
    private String location;
    private String size;
    private int capasity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="manager_id")
    private Admin manager;

    public Hall(String hallName, String location, String size, int capasity, Admin manager) {
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.manager = manager;
    }
}
