package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.domain.Event;
import com.example.ticketingsystem.exception.CommonException;
import com.example.ticketingsystem.resource.AddEventResource;
import com.example.ticketingsystem.resource.SuccessAndErrorDetailsResource;
import com.example.ticketingsystem.resource.UpdateEventResource;
import com.example.ticketingsystem.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public ResponseEntity<SuccessAndErrorDetailsResource> getAllEvents() throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        List<Event> eventList = eventService.findall();
        if (eventList.isEmpty()) {
            successAndErrorDetailsResource.setMessages("Events Not Found");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else{
            successAndErrorDetailsResource.setMessages("Events Are There!");
            return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);

        }
    }

   @GetMapping("/{id}")
   public ResponseEntity<SuccessAndErrorDetailsResource> getEventById(@PathVariable(value = "id", required = false) Long id)throws CommonException {
       SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
       Optional<Event> eventOptional = eventService.findbyId(id);
       if (eventOptional.isEmpty()) {
           successAndErrorDetailsResource.setMessages("Events Not find with this id");
           return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
       }
       else{
           successAndErrorDetailsResource.setMessages("Events Are There for this id!");
           return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);

       }
   }
    @GetMapping("/eventName/{eventName}")
    public ResponseEntity<SuccessAndErrorDetailsResource> getEventByName(@PathVariable(value = "eventName", required = false) String eventName)throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        List<Event> eventList = eventService.findbyEventName(eventName);
        if (eventList.isEmpty()){
            successAndErrorDetailsResource.setMessages("Events Not Found");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else{
            successAndErrorDetailsResource.setMessages("Events Are There!");
            return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);

        }
        }

    @GetMapping("/eventStatus/{eventStatus}")
    public ResponseEntity<SuccessAndErrorDetailsResource> getEventByStatus(@PathVariable(value = "eventStatus", required = false) String eventStatus)throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        List<Event> eventList = eventService.findbyEventStatus(eventStatus);
        if (eventList.isEmpty()){
            successAndErrorDetailsResource.setMessages("Events Not Found");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else{
            successAndErrorDetailsResource.setMessages("Events Are There!");
            return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);

        }
    }

    @PostMapping("/save")
    public ResponseEntity<SuccessAndErrorDetailsResource> AddEvent(@Valid @RequestBody AddEventResource addEventResource) throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Event event = eventService.add(addEventResource);
        if (event == null) {
            successAndErrorDetailsResource.setMessages("Record Not Saved");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            successAndErrorDetailsResource.setMessages("Record saved successfully!");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessAndErrorDetailsResource> UpdateEvent(@PathVariable(value = "id", required = false) Long id,
                                                                     @Valid @RequestBody UpdateEventResource updateEventResource) throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        Event event = eventService.update(id, updateEventResource);
        if (event == null){
        successAndErrorDetailsResource.setMessages("Record Not Updated");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            successAndErrorDetailsResource.setMessages("Record Updated successfully!");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessAndErrorDetailsResource> DeleteEventById(@PathVariable(value = "id", required = false) Long id)throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        String message = eventService.delete(id);
        successAndErrorDetailsResource.setMessages("Record Deleted successfully!");
        return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.CREATED);
    }





    }








