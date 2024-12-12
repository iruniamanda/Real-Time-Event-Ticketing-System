package com.example.ticketingsystem.service.impl;

import com.example.ticketingsystem.domain.SystemConfig;
import com.example.ticketingsystem.repository.SystemConfigRepository;
import com.example.ticketingsystem.resource.SystemConfigResource;
import com.example.ticketingsystem.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemConfigServiceimpl implements SystemConfigService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Override
    public SystemConfig add(SystemConfigResource systemConfigResource) {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setVendorId(systemConfigResource.getVendorId());
        systemConfig.setMaxCapacity(systemConfigResource.getMaxCapacity());
        systemConfig.setTotalTickets(systemConfigResource.getTotalTickets());
        systemConfig.setTicketReleaseRate(systemConfigResource.getTicketReleaseRate());
        systemConfig.setCustomerRetrievalRate(systemConfigResource.getCustomerRetrievalRate());
        systemConfig.setVendor_count(systemConfigResource.getVendorCount());
        systemConfig.setCustomer_count(systemConfigResource.getCustomerCount());
        return systemConfigRepository.save(systemConfig);

    }

    @Override
    public ResponseEntity getSystemConfig(){
        List<SystemConfig> systemConfig = systemConfigRepository.findAll();
        return ResponseEntity.ok(systemConfig);
    }

    @Override
    public SystemConfig update(SystemConfigResource systemConfigResource) {
        Optional<SystemConfig> systemConfig =systemConfigRepository.findAll().stream().findFirst();
        SystemConfig systemConfigs = systemConfig.get();
        systemConfigs.setVendorId(systemConfigResource.getVendorId());
        systemConfigs.setMaxCapacity(systemConfigResource.getMaxCapacity());
        systemConfigs.setTotalTickets(systemConfigResource.getTotalTickets());
        systemConfigs.setTicketReleaseRate(systemConfigResource.getTicketReleaseRate());
        systemConfigs.setCustomerRetrievalRate(systemConfigResource.getCustomerRetrievalRate());
        systemConfigs.setVendor_count(systemConfigResource.getVendorCount());
        systemConfigs.setCustomer_count(systemConfigResource.getCustomerCount());
        return systemConfigRepository.save(systemConfigs);

    }

    @Override
    public SystemConfig getSystemConfigs() {
        // Retrieve the first configuration record (assuming only one exists)
        return systemConfigRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No configuration found in the database"));
    }
}
