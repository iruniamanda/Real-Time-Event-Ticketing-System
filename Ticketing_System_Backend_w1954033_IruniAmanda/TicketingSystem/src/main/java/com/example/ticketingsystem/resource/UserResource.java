package com.example.ticketingsystem.resource;

import jakarta.validation.constraints.Pattern;
import lombok.Data;//create getters and setters

@Data
public class UserResource {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobileNo;
    private String password;
    @Pattern(regexp = "CUSTOMER|VENDOR", message = "User type should be either CUSTOMER or VENDOR")
    private String userType;






}
