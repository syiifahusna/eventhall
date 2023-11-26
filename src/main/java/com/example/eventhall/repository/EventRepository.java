package com.example.eventhall.repository;

import com.example.eventhall.entity.Event;
import com.example.eventhall.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    @Query("SELECT new com.example.eventhall.entity.Event" +
            "(e.id, e.eventName, e.startDate, e.endDate) " +
            "FROM Event e WHERE e.reservation.id = ?1")
    List<Event> findEventByReservationId(Long rId);
}
