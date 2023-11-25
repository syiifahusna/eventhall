package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {

    @Id
    @SequenceGenerator(
            name = "reservation_seq",
            sequenceName = "reservation_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_seq"
    )
    private Long id;

    private String title;
    private String purpose;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String note;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hall_id")
    private Hall hall;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public Reservation(String title, String purpose, LocalDate dateStart, LocalDate dateEnd, String note, Hall hall, User user) {
        this.title = title;
        this.purpose = purpose;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.note = note;
        this.hall = hall;
        this.user = user;
    }

    public Reservation(String title, String purpose, LocalDate dateStart, LocalDate dateEnd, String note) {
        this.title = title;
        this.purpose = purpose;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.note = note;
    }

    public Reservation(Long id, String title, String purpose, LocalDate dateStart, LocalDate dateEnd, String note, Long hallId, String hallName) {
        this.id = id;
        this.title = title;
        this.purpose = purpose;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.note = note;

        Hall hall = new Hall();
        hall.setId(hallId);
        hall.setHallName(hallName);
        this.hall = hall;
    }
}
