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

    @Query("SELECT new com.example.eventhall.entity.Hall(h.id, h.hallName, h.location, h.size, h.capasity, h.status) FROM Hall h")
    List<Hall> findAllWithoutManager();

    @Query("SELECT new com.example.eventhall.entity.Hall" +
            "(h.id, h.hallName, h.location, h.size, h.capasity, h.status," +
            "h.manager.id, h.manager.name, h.manager.email)" +
            "FROM Hall h " +
            "WHERE h.id = ?1")
    Hall findHallWithoutManagerUsernameAndPassword(Long hallId);

}
