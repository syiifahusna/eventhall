package com.example.eventhall.controller;

import com.example.eventhall.entity.Event;
import com.example.eventhall.entity.Reservation;
import com.example.eventhall.entity.User;
import com.example.eventhall.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/u/event")
public class EventController {

    @Value("${app.user.dir}")
    private String userDir;

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/form/{rId}")
    public String event(@PathVariable("rId") Long rId, Authentication authentication, Model model){
        User userRequest = (User) authentication.getPrincipal();
        Reservation reservation = eventService.getHallReservationDetails(rId,userRequest.getId());
        if(reservation != null){
            model.addAttribute("reservation",reservation);
        }else{
            model.addAttribute("message","reservation does not exist");
        }
        return userDir + "/eventform";
    }

    @PostMapping("/attemptcreateevent/{rId}")
    public String attemptCreateEvent(@RequestParam("eventName") String eventName,
                                     @RequestParam("description") String description,
                                     @RequestParam("dateStart") String dateStart,
                                     @RequestParam("dateEnd") String dateEnd,
                                     @PathVariable Long rId,
                                     Model model){

        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);

        Event eventRequest = new Event(eventName,description,startDate,endDate);
        String message = eventService.attemptCreateEvent(eventRequest,rId);

        if(message.isEmpty()){
            return "redirect:/u/event/success";
        }else{
            model.addAttribute("message",message);
            return userDir + "/eventform";
        }
    }

    @GetMapping("/success")
    public String createEventSuccess(){
        return userDir + "/eventsuccess";
    }

}
