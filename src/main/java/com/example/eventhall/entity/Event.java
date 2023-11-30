package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @SequenceGenerator(
            name = "event_seq",
            sequenceName = "event_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_seq"
    )
    private Long id;
    private String eventName;
    @Lob
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="reservation_id")
    private Reservation reservation;

    public Event(String eventName, String description, LocalDate startDate, LocalDate endDate) {
        this.eventName = eventName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String eventName, String description, LocalDate startDate, LocalDate endDate, Reservation reservation) {
        this.eventName = eventName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservation = reservation;
    }

    public Event(Long id, String eventName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(Long id,
                 String eventName,
                 String description,
                 LocalDate startDate,
                 LocalDate endDate,
                 Long rId,
                 String title,
                 String purpose,
                 LocalDate dateStart,
                 LocalDate dateEnd,
                 String note,
                 Long hallId,
                 String hallName,
                 String location,
                 String size,
                 int capacity,
                 Boolean status) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;

        Hall hall= new Hall(hallId,hallName,location,size,capacity,status);
        Reservation reservation = new Reservation(rId,title,purpose,dateStart,dateEnd,note,hall);
        this.reservation = reservation;
    }

    public Event(Long id, String eventName, String description, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
