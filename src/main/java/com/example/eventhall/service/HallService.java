package com.example.eventhall.service;

import com.example.eventhall.entity.Admin;
import com.example.eventhall.entity.Hall;
import com.example.eventhall.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void insertHall(){

        Hall hall = new Hall("Bunga Hall","Kuala Lumpur","6000sqft",800,new Admin("Shan","shan","123","shan@mail.com"));
        hallRepository.save(hall);

    }
}
