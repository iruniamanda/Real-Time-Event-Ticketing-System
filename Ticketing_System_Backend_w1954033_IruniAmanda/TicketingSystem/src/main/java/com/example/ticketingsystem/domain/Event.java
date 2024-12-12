package com.example.ticketingsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name ="event")
@Data
public class Event implements Serializable {//interface in springboot

    @Serial
    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_name",nullable = false,length = 200)
    private String eventName;//variable

    @Column(name = "event_type",nullable = false,length = 200)
    private String eventType;//variable

    @Column(name = "event_status",nullable = false,length = 50)
    private  String eventStatus;

    @Column(name = "description",length =600)
    private String description;

    @Column(name = "created_user",nullable = false,length = 200)
    private  String createdUser;

    @Column(name = "created_date",nullable = false)
    private Timestamp createdDate;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "modified_user",nullable = false,length = 200)
    private  String modifiedUser;

    @Column(name = "modified_date",nullable = false)
    private Timestamp modifiedDate;

}