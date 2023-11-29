package com.example.eventhall.repository;

import com.example.eventhall.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

//    @Query("SELECT h.id, h.hallName, h.location, h.size, h.capasity, h.status FROM Hall h")
//    List<Object[]> findAllWithoutManager();

    @Query("SELECT new com.example.eventhall.entity.Hall" +
            "(h.id, h.hallName, h.location, h.size, h.capasity, h.status) " +
            "FROM Hall h")
    List<Hall> findAllWithoutManager();

    @Query("SELECT new com.example.eventhall.entity.Hall" +
            "(h.id, h.hallName, h.location, h.size, h.capasity, h.status, " +
            "m.id, m.name, m.email) " +
            "FROM Hall h JOIN h.managers m " +
            "WHERE h.id = ?1")
    List<Hall> findHallWithoutManagerUsernameAndPassword(Long hallId);

}
