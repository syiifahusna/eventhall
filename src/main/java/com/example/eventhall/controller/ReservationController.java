package com.example.eventhall.controller;

import com.example.eventhall.entity.Event;
import com.example.eventhall.entity.Hall;
import com.example.eventhall.entity.Reservation;
import com.example.eventhall.entity.User;
import com.example.eventhall.service.EventService;
import com.example.eventhall.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping({"/u/reservation","/u/reservation/"})
public class ReservationController {

    @Value("${app.user.dir}")
    private String userDir;
    private final ReservationService reservationService;
    private final EventService eventService;

    @Autowired
    public ReservationController(ReservationService reservationService, EventService eventService) {
        this.reservationService = reservationService;
        this.eventService = eventService;
    }

    @GetMapping("/form/{hallId}")
    public String reservation(@PathVariable("hallId") Long hallId, Model model){

        Hall hall = reservationService.getHallDetailsWithoutManagerUsernameAndPassword(hallId);
        if(hall != null){
            model.addAttribute("hall",hall);
        }else{
            model.addAttribute("message","Hall does not exist");
        }

        return userDir + "/reservationform";
    }

    @PostMapping("/attemptreserve/{hallId}")
    public String attemptReserve(@RequestParam("title") String title,
                                 @RequestParam("purpose") String purpose,
                                 @RequestParam("dateStart") String dateStart,
                                 @RequestParam("dateEnd") String dateEnd,
                                 @RequestParam("note") String note,
                                 @PathVariable("hallId") Long hallId,
                                 Authentication authentication,
                                 Model model){

        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);

        User userRequest = (User) authentication.getPrincipal();
        Reservation reservationRequest = new Reservation(title,purpose,startDate,endDate,note);
        String message = reservationService.attemptReservation(reservationRequest,userRequest,hallId);

        if(message.isEmpty()){
            return "redirect:/u/reservation/success";
        }else{
            Hall hall = reservationService.getHallDetailsWithoutManagerUsernameAndPassword(hallId);
            model.addAttribute("hall",hall);
            model.addAttribute("formmessage",message);
            return userDir + "/reservationform";
        }
    }

    @GetMapping("/success")
    public String reservationSuccess(){
        return userDir + "/reservationsuccess";
    }

    @GetMapping("/list")
    public String reservationsList(Authentication authentication,Model model){
        User userRequest = (User) authentication.getPrincipal();
        List<Reservation> reservations = reservationService.reservationList(userRequest.getId());
        if(!reservations.isEmpty()){
            model.addAttribute("reservations",reservations);
        }else{
            model.addAttribute("message","No Reservation Found");
        }
        return userDir + "/reservations";
    }

    @GetMapping("/{rId}")
    public String reservationsDetails(@PathVariable("rId") Long rId, Authentication authentication, Model model){
        User userRequest = (User) authentication.getPrincipal();
        Reservation reservation = reservationService.getReservationDetails(rId, userRequest.getId());
        List<Event> events = eventService.eventList(rId);
        if(reservation != null){
            if(!events.isEmpty()){
                model.addAttribute("events",events);
            }
            model.addAttribute("reservation",reservation);
        }else{
            model.addAttribute("message","reservation does not exist");
        }

        return userDir + "/reservationdetails";
    }

}
