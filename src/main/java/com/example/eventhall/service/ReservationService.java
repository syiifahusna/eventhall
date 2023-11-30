package com.example.eventhall.service;

import com.example.eventhall.entity.*;
import com.example.eventhall.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            List<Reservation> findReservations = reservationRepository.findReservationByIdExceptUser(rId,userId);

            if(findReservations.isEmpty()){
                return null;
            }

            List<Admin> managers = new ArrayList<>();
            for (Reservation r: findReservations) {
                Admin manager = r.getHall().getManagers().get(0);
                managers.add(manager);
            }

            Hall hall = new Hall(findReservations.get(0).getHall().getId(),
                    findReservations.get(0).getHall().getHallName(),
                    findReservations.get(0).getHall().getLocation(),
                    findReservations.get(0).getHall().getSize(),
                    findReservations.get(0).getHall().getCapasity(),
                    findReservations.get(0).getHall().getStatus(),
                    managers);

            User user = null;

            Reservation reservation = new Reservation(findReservations.get(0).getId(),
                    findReservations.get(0).getTitle(),
                    findReservations.get(0).getPurpose(),
                    findReservations.get(0).getDateStart(),
                    findReservations.get(0).getDateEnd(),
                    findReservations.get(0).getNote(),
                    hall,
                    user);

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

    public Hall getHallDetailsWithoutManagerUsernameAndPassword(Long hallId){
        return hallService.getHallDetailsWithoutManagerUsernameAndPassword(hallId);
    }


}
