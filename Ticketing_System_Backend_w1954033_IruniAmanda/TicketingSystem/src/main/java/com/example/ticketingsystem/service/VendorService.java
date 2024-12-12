package com.example.ticketingsystem.service;
import com.example.ticketingsystem.domain.Vendor;

public interface VendorService {
    Vendor validateVendor(String username, String password);
}
