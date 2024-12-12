package com.example.ticketingsystem.service;

import com.example.ticketingsystem.domain.SystemConfig;
import com.example.ticketingsystem.resource.SystemConfigResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SystemConfigService {

    SystemConfig add(SystemConfigResource systemConfigResource);

    ResponseEntity getSystemConfig();

     SystemConfig update(SystemConfigResource systemConfigResource);

     SystemConfig getSystemConfigs();

}
