package com.example.eventhall.service;

import com.example.eventhall.entity.Admin;
import com.example.eventhall.entity.Hall;
import com.example.eventhall.repository.HallRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public Hall getHallDetailsWithoutManagerUsernameAndPassword(Long hallId){
        try{
            Hall hall = hallRepository.findHallWithoutManagerUsernameAndPassword(hallId);
            if(hall != null) {
                return hall;
            }else{
                return null;
            }
        }catch(EntityNotFoundException e){
            throw e;
        }
    }

    public Hall getHallById(Long hallId) {
        try {
            Optional<Hall> optinalHall = hallRepository.findById(hallId);
            if(optinalHall.isPresent()){
                return optinalHall.get();
            }else{
                return null;
            }
        }catch(EntityNotFoundException e){
            throw e;
        }
    }
}
