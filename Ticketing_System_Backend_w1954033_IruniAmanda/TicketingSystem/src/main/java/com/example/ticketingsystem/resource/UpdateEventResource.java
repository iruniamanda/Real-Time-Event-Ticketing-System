package com.example.ticketingsystem.resource;

import lombok.Data;//create getters and setters

import java.sql.Timestamp;

@Data
public class UpdateEventResource {
    private String eventName;
    private String eventType;
    private  String eventStatus;
    private  String description;
    private  String startdate;
    private  String endDate;
    private String  modifiedUser;
    private Timestamp modifiedDate;

}