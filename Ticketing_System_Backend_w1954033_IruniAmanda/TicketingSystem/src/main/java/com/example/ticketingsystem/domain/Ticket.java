package com.example.ticketingsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name ="ticket")
@Data
public class Ticket implements Serializable {//interface in springboot

    @Serial
    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_id", nullable = false, length = 200)
    private Long eventId;//variable

    @Column(name = "price", nullable = false)
    private BigDecimal price;//variable

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "description", length = 600)
    private String description;
}





