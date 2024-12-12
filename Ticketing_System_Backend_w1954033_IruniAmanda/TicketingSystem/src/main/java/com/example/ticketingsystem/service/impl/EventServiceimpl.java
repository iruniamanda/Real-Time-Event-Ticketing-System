package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.domain.Event;
import com.example.ticketingsystem.exception.ValidationRecordException;
import com.example.ticketingsystem.repository.EventRepository;
import com.example.ticketingsystem.resource.AddEventResource;
import com.example.ticketingsystem.resource.UpdateEventResource;
import com.example.ticketingsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)

@Service
public class EventServiceimpl implements EventService {

    @Autowired
    private Environment environment;

    @Autowired
    private EventRepository eventRepository;

    private Timestamp getCreatedOrModifiyDate(){
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        return  new Timestamp(now.getTime());
    }



    @Override
    public List<Event> findall() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findbyId(Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            return Optional.ofNullable(eventOpt.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Event> findbyEventName(String eventName) {
        List<Event> eventList = eventRepository.findByEventName(eventName);
        if (eventList.isEmpty()) {
            System.out.println("No events found for the given event name: " + eventName);
            return Collections.emptyList();
        } else {
            return eventList;

        }
    }

    @Override
    public List<Event> findbyEventStatus(String eventStatus) {
        return eventRepository.findByEventStatus(eventStatus);
    }

  //Add method
    @Override
    public Event add(AddEventResource addEventResource) {
        Event event = new Event();
        event.setEventName(addEventResource.getEventName());
        event.setEventType(addEventResource.getEventType());
        event.setEventStatus(addEventResource.getEventStatus());
        event.setDescription(addEventResource.getDescription());
        event.setCreatedUser(addEventResource.getCreatedUser());
        event.setCreatedDate(getCreatedOrModifiyDate());
        try {
            event.setStartDate(String.valueOf(LocalDate.parse(addEventResource.getStartdate())));
            event.setEndDate(String.valueOf(LocalDate.parse(addEventResource.getEndDate())));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format.");
        }
        return eventRepository.saveAndFlush(event);

    }

    @Override
    public Event update(Long id, UpdateEventResource updateEventResource) {
        Optional<Event>eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()){
            Event event = eventOptional.get();
            event.setEventName(updateEventResource.getEventName());
            event.setEventType(updateEventResource.getEventType());
            event.setEventStatus(updateEventResource.getEventStatus());
            event.setDescription(updateEventResource.getDescription());
            event.setModifiedUser(updateEventResource.getModifiedUser());
            event.setModifiedDate(getCreatedOrModifiyDate()); try {
                event.setStartDate(String.valueOf(LocalDate.parse(updateEventResource.getStartdate())));
                event.setEndDate(String.valueOf(LocalDate.parse(updateEventResource.getEndDate())));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format.");
            }
            return eventRepository.saveAndFlush(event);
        }
     else {
        throw new ValidationRecordException("Event with ID " + id + " not found.");
    }

    }


    //delete method
    @Override
    public String delete(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(id);
            return environment.getProperty(" Record has been successfully deleted.");
        } else {
           throw new ValidationRecordException("Event with ID " + id + " does not exist.");
        }
    }
}