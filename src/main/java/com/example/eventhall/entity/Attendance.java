package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guestId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event eventId;

    private Boolean isAttending;

    public Attendance(Event eventId, Boolean isAttending) {
        this.eventId = eventId;
        this.isAttending = isAttending;
    }
}
