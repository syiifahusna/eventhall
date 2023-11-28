package com.example.eventhall.service;

import com.example.eventhall.entity.Event;
import com.example.eventhall.entity.Reservation;
import com.example.eventhall.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService{

    private final EventRepository eventRepository;
    private final ReservationService reservationService;

    public EventService(EventRepository eventRepository, ReservationService reservationService) {
        this.eventRepository = eventRepository;
        this.reservationService = reservationService;
    }

    public Reservation getHallReservationDetails(Long rId, Long userId) {

        Reservation reservation = reservationService.getReservationDetails(rId,userId);
        return reservation;
    }

    public String attemptCreateEvent(Event eventRequest, Long rId) {

        //validation

        //convert
        Reservation reservation = reservationService.getReservationById(rId);
        Event event = new Event(eventRequest.getEventName(),
                                eventRequest.getDescription(),
                                eventRequest.getStartDate(),
                                eventRequest.getEndDate(),
                                reservation);

        //save
        Event saved = eventRepository.save(event);
        if(saved != null){
            return "";
        }else{
            return "There something wrong";
        }
    }

    public List<Event> eventList(Long rId) {
        try{
            List<Event> events  = eventRepository.findEventByReservationId(rId);
            return events;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch reservations", e);
        }

    }

    public Event getEventDetails(Long eId, Long userId) {
        try{
            Event event= eventRepository.findEventByEventIdExceptUserAndHallManager(eId,userId);
            if(event !=null){
                return event;
            }else{
                return null;
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch event entity", e);
        }

    }
}
