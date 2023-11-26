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
import java.util.List;

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
        if(reservation != null){
            model.addAttribute("reservation",reservation);
        }else{
            model.addAttribute("message","reservation does not exist");
        }

        return userDir + "/reservationdetails";
    }

}
