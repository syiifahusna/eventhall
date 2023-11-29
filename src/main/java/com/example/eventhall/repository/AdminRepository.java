package com.example.eventhall.repository;

import com.example.eventhall.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("SELECT new com.example.eventhall.entity.Admin(a.id, a.name, a.email) " +
            "FROM Admin a WHERE a.id = ?1")
    List<Admin> findAdminsById(Long adminId);
}
