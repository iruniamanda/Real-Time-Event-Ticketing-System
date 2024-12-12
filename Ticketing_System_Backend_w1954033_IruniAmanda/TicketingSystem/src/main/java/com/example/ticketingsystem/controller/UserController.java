package com.example.ticketingsystem.controller;

import com.example.ticketingsystem.domain.User;
import com.example.ticketingsystem.exception.CommonException;
import com.example.ticketingsystem.resource.SuccessAndErrorDetailsResource;
import com.example.ticketingsystem.resource.UserResource;
import com.example.ticketingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password) {
        try {
            userService.validateUser(userName, password);
            return ResponseEntity.ok("Login successful");
        } catch (RuntimeException e) {
            // Return a structured error response with HTTP status
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessAndErrorDetailsResource> registerUser(@Valid @RequestBody UserResource userResource) throws CommonException {
            SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
            User user = userService.save(userResource);
            if (user == null) {
                successAndErrorDetailsResource.setMessages("Record Not Saved");
                return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
            } else {
                successAndErrorDetailsResource.setMessages("Record saved successfully!");
                return new ResponseEntity<>(successAndErrorDetailsResource,HttpStatus.CREATED);
            }
        }
    }
