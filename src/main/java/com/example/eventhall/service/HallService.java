package com.example.eventhall.service;

import com.example.eventhall.entity.Admin;
import com.example.eventhall.entity.Hall;
import com.example.eventhall.repository.HallRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HallService {
    private final HallRepository hallRepository;

    @Autowired
    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<Hall> getHallsWithoutManager(){
        try {
            List<Hall> halls = hallRepository.findAllWithoutManager();
            return halls;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch halls", e);
        }
    }

    public Hall getHallDetails(Long hallId){
        try{
            Optional<Hall> hall = hallRepository.findById(hallId);
            if(hall.isPresent()) {
                return hall.get();
            }else{
                return null;
            }
        }catch(EntityNotFoundException e){
            throw e;
        }
    }
}
