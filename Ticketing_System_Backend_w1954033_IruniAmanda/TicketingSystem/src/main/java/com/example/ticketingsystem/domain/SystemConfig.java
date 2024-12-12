package com.example.ticketingsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Entity
@Table(name ="system_config")
@Data
public class SystemConfig  implements Serializable {
    @Serial
    private  static  final  long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private  Long id;

    @Column(name = "total_tickets",nullable = false)
    private int totalTickets;

    @Column(name = " max_capacity",nullable = false)
    private int maxCapacity;

    @Column(name = " ticket_release_rate",nullable = false)
    private int ticketReleaseRate;

    @Column(name = " customer_retrieval_rate",nullable = false)
    private int customerRetrievalRate;

    @Column(name = "vendor_count",nullable = false)
    private int vendor_count;

    @Column(name = " customer_count",nullable = false)
    private int customer_count;

    @Column(name = "vendor_id",nullable = false)
    private int  vendorId;

}
