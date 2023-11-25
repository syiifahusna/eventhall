package com.example.eventhall.controller;

import com.example.eventhall.entity.Hall;
import com.example.eventhall.entity.Reservation;
import com.example.eventhall.entity.User;
import com.example.eventhall.service.HallService;
import com.example.eventhall.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping({"/u/reservation","/u/reservation/"})
public class ReservationController {

    @Value("${app.user.dir}")
    private String userDir;

    private final HallService hallService;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(HallService hallService, ReservationService reservationService) {
        this.hallService = hallService;
        this.reservationService = reservationService;
    }

    @GetMapping("/form/{hallId}")
    public String reservation(@PathVariable("hallId") Long hallId, Model model){

        Hall hall = hallService.getHallDetails(hallId);
        if(!hall.getId().toString().isEmpty()){
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
                                 @RequestParam("dateEnd") String dataEnd,
                                 @RequestParam("note") String note,
                                 @PathVariable("hallId") Long hallId,
                                 Authentication authentication,
                                 Model model){

        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dataEnd);
        User userRequest = (User) authentication.getPrincipal();
        Reservation reservationRequest = new Reservation(title,purpose,startDate,endDate,note);

        String message = reservationService.attemptReservation(reservationRequest,userRequest,hallId);
        if(message.isEmpty()){
            return "redirect:/u/reservation/success";
        }else{
            model.addAttribute("message",message);
            return userDir + "/reservationform";
        }


    }

    @GetMapping("/success")
    public String reservationSuccess(){
        return userDir + "/reservationsuccess";
    }

}
