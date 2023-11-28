package com.example.eventhall.service;

import com.example.eventhall.entity.Hall;
import com.example.eventhall.entity.Reservation;
import com.example.eventhall.entity.User;
import com.example.eventhall.repository.ReservationRepository;
import com.example.eventhall.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final HallService hallService;
    private final UserService userService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(HallService hallService, UserService userService, ReservationRepository reservationRepository) {
        this.hallService = hallService;
        this.userService = userService;
        this.reservationRepository = reservationRepository;
    }

    public String  attemptReservation(Reservation reservationRequest, User userRequest, Long hallId) {

        //convert
        Hall hall = hallService.getHallById(hallId);
        User user = userService.getUserById(userRequest.getId());

        Reservation reservation = new Reservation(reservationRequest.getTitle(),
                reservationRequest.getPurpose(),
                reservationRequest.getDateStart(),
                reservationRequest.getDateEnd(),
                reservationRequest.getNote(),hall,user);

        //save
        Reservation saved = reservationRepository.save(reservation);
        if(saved != null){
            return "";
        }else{
            return "There something wrong";
        }

    }

    public List<Reservation> reservationList(Long userId){
        try{
            List<Reservation> reservations  = reservationRepository.findByReservationUserIdExceptUserAndHallManager(userId);
            return reservations;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch reservations", e);
        }
    }

    public Reservation getReservationDetails(Long rId, Long userId){
        try{
            Reservation reservation = reservationRepository.findReservationByIdExceptUser(rId,userId);
            if(reservation != null){
                return reservation;
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch reservation entity", e);
        }

    }

    public Reservation getReservationById(Long rId){
        try {
            Optional<Reservation> optionalReservation = reservationRepository.findById(rId);
            if(optionalReservation.isPresent()){
                return optionalReservation.get();
            }else{
                return null;
            }
        }catch(EntityNotFoundException e){
            throw e;
        }
    }

}
