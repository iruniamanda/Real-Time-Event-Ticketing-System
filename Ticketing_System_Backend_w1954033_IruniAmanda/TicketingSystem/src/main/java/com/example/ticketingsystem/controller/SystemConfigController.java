package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.domain.SystemConfig;
import com.example.ticketingsystem.exception.CommonException;
import com.example.ticketingsystem.resource.SuccessAndErrorDetailsResource;
import com.example.ticketingsystem.resource.SystemConfigResource;
import com.example.ticketingsystem.service.SystemConfigService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;


    @PostMapping("/system_config")
    public ResponseEntity<SuccessAndErrorDetailsResource> saveSytemConfig(@Valid @RequestBody SystemConfigResource systemConfigResource) throws CommonException {
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        SystemConfig systemConfig = systemConfigService.add(systemConfigResource);
        if (systemConfig == null) {
            successAndErrorDetailsResource.setMessages("Record Not Saved");
            return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            successAndErrorDetailsResource.setMessages("Record saved successfully!");
            return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);
        }
    }

    @GetMapping("/getSystemConfig")
    public ResponseEntity getAllSystemConfig(){
      return systemConfigService.getSystemConfig();
    }

    @PostMapping("/updateSystemConfig")
    public ResponseEntity updateSystemConfig(@RequestBody SystemConfigResource systemConfigResource ){
        SystemConfig systemConfig = systemConfigService.update(systemConfigResource);
        SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
        successAndErrorDetailsResource.setMessages("Record Updated successfully!");
        return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);
    }
}
