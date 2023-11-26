package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name= "hall")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hall {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String hallName;
    private String location;
    private String size;
    private int capasity;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="manager_id")
    private Admin manager;

    public Hall(String hallName, String location, String size, int capasity, Boolean status, Admin manager) {
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.status = status;
        this.manager = manager;
    }

    public Hall(Long id, String hallName, String location, String size, int capasity, Boolean status) {
        this.id = id;
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.status = status;
    }

    public Hall(Long id, String hallName, String location, String size, int capasity, Boolean status, Long managerId, String managerName, String managerEmail) {
        this.id = id;
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.status = status;

        Admin manager = new Admin();
        manager.setId(managerId);
        manager.setName(managerName);
        manager.setEmail(managerEmail);
        this.manager = manager;
    }

}
