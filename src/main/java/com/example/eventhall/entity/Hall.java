package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "hallsmanagers",joinColumns = @JoinColumn(name = "hall_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id",referencedColumnName = "id"))
    private List<Admin> managers;

    public Hall(String hallName, String location, String size, int capasity, Boolean status, List<Admin> managers) {
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.status = status;
        this.managers = managers;
    }

    public Hall(Long id, String hallName, String location, String size, int capasity, Boolean status) {
        this.id = id;
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.status = status;
    }

    public Hall(Long id,
                String hallName,
                String location,
                String size,
                int capasity,
                Boolean status,
                Long managerId,
                String managerName,
                String managerEmail) {

        this.id = id;
        this.hallName = hallName;
        this.location = location;
        this.size = size;
        this.capasity = capasity;
        this.status = status;

        if(managers == null){
            managers = new ArrayList<>();
        }

        Admin manager = new Admin(managerId,managerName,managerEmail);
        this.managers.add(manager);
    }

}
