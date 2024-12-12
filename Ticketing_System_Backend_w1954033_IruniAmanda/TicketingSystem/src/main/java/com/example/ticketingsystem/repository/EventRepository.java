package com.example.ticketingsystem.repository;

import com.example.ticketingsystem.domain.Event;
import com.example.ticketingsystem.resource.AddEventResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event>findByEventName(String eventName);
    List<Event>findByEventStatus(String eventStatus);
    List<Event>findAll();
    //Event add(AddEventResource addEventResource);
}
