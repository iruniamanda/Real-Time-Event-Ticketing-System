package com.example.ticketingsystem.resource;
import lombok.Data;//create getters and setters
@Data
public class SystemConfigResource {
    private int totalTickets;
    private int maxCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int vendorId;
    private Long id;
    private int vendorCount;
    private int customerCount;
}
