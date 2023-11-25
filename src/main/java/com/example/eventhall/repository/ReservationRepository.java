package com.example.eventhall.repository;

import com.example.eventhall.entity.Hall;
import com.example.eventhall.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT " +
            "new com.example.eventhall.entity.Reservation" +
            "(r.id, r.title, r.purpose, r.dateStart, r.dateEnd, r.note, r.hall.id, r.hall.hallName) " +
            "FROM " +
            "Reservation r " +
            "WHERE r.user.id = ?1")
    List<Reservation> findByUserIdExceptUserAndHallManager(Long userId);


}
