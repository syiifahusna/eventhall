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


    @Query("SELECT new com.example.eventhall.entity.Event" +
            "(e.id," +
            "e.eventName, " +
            "e.description, " +
            "e.startDate, " +
            "e.endDate, " +
            "e.reservation.id, " +
            "e.reservation.title, " +
            "e.reservation.purpose, " +
            "e.reservation.dateStart, " +
            "e.reservation.dateEnd, " +
            "e.reservation.note, " +
            "e.reservation.hall.id, " +
            "e.reservation.hall.hallName, " +
            "e.reservation.hall.location," +
            "e.reservation.hall.size," +
            "e.reservation.hall.capasity," +
            "e.reservation.hall.status) " +
            "FROM Event e WHERE  e.id = ?1 AND e.reservation.user.id = ?2")
    Event findEventByEventIdExceptUserAndHallManager(Long eId,Long userId);
}
