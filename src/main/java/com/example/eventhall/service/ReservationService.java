package com.example.eventhall.service;

import com.example.eventhall.entity.Hall;
import com.example.eventhall.entity.Reservation;
import com.example.eventhall.entity.User;
import com.example.eventhall.repository.HallRepository;
import com.example.eventhall.repository.ReservationRepository;
import com.example.eventhall.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final HallRepository hallRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(HallRepository hallRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.hallRepository = hallRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }


    public String  attemptReservation(Reservation reservationRequest, User userRequest, Long hallId) {

        //validation


        //convert
        Hall hall = hallRepository.findById(hallId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userRequest.getId()).orElseThrow(EntityNotFoundException::new);

        Reservation reservation = new Reservation(reservationRequest.getTitle(),
                reservationRequest.getPurpose(),
                reservationRequest.getDateStart(),
                reservationRequest.getDateEnd(),
                reservationRequest.getNote(),hall,user);

        //save
        Reservation saved = reservationRepository.save(reservation);
        if(!saved.getTitle().isEmpty()){
            return "";
        }else{
            return "There something wrong";
        }

    }
}
