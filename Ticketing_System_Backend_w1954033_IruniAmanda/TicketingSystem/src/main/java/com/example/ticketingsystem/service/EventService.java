package com.example.ticketingsystem.service;

import com.example.ticketingsystem.domain.Event;
import com.example.ticketingsystem.resource.AddEventResource;
import com.example.ticketingsystem.resource.UpdateEventResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EventService {
    List<Event>findall();
    Optional<Event>findbyId(Long id);

    List<Event>findbyEventName(String eventName);

    List<Event>findbyEventStatus(String eventStatus);

    Event add(AddEventResource addEventResource);

    Event update(Long id,UpdateEventResource updateEventResource);

    String delete(Long id);



}
