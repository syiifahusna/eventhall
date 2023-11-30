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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes,
                                     Model model){

        LocalDate startDate = LocalDate.parse(dateStart);
        LocalDate endDate = LocalDate.parse(dateEnd);

        User userRequest = (User) authentication.getPrincipal();
        Event eventRequest = new Event(eventName,description,startDate,endDate);
        String message = eventService.attemptCreateEvent(eventRequest,rId);

        if(message.isEmpty()){
            redirectAttributes.addFlashAttribute("rId",  rId);
            return "redirect:/u/event/success";
        }else{
            Reservation reservation = eventService.getHallReservationDetails(rId,userRequest.getId());
            model.addAttribute("reservation",reservation);
            model.addAttribute("formmessage",message);
            return userDir + "/eventform";
        }
    }

    @GetMapping("/success")
    public String createEventSuccess(){
        return userDir + "/eventsuccess";
    }

    @GetMapping("/{eId}")
    public String eventDetails(@PathVariable("eId")Long eId, Authentication authentication, Model model){
        User userRequest = (User) authentication.getPrincipal();
        Event event = eventService.getEventDetails(eId, userRequest.getId());
        if(event != null){
            model.addAttribute("event",event);
        }else{
            model.addAttribute("message","Event does not exist");
        }

        return userDir + "/eventdetails";
    }

    @GetMapping("/edit/{eId}")
    public String editEvent(@PathVariable("eId") Long eId,Authentication authentication, Model model){
        User userRequest = (User) authentication.getPrincipal();
        Event event = eventService.getEventDetails(eId, userRequest.getId());

       if(event != null){
            model.addAttribute("event",event);
        }else{
            model.addAttribute("message","Event does not exist");
        }
        return userDir + "/editevent";
    }

}
