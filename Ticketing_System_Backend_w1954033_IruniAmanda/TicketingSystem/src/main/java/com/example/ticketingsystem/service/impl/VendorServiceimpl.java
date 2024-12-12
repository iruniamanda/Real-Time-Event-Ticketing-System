package com.example.ticketingsystem.service.impl;
import com.example.ticketingsystem.domain.Vendor;
import com.example.ticketingsystem.repository.VendorRepository;
import com.example.ticketingsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorServiceimpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor validateVendor(String username, String password) {
        Optional<Vendor> vendorOpt = vendorRepository.findByUserName(username);
        if (vendorOpt.isPresent()) {
            Vendor vendor = vendorOpt.get();
            // Directly compare the passwords (not recommended for production)
            if (vendor.getPassword().equals(password)) {
                return vendor;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Vendor not found");
        }
    }
}

