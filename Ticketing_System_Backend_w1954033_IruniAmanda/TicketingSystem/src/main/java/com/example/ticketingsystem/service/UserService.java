package com.example.ticketingsystem.service;
import com.example.ticketingsystem.domain.User;
import com.example.ticketingsystem.resource.UserResource;
import org.springframework.stereotype.Service;
@Service
public interface UserService {

    User validateUser(String username, String password);

    User save(UserResource userResource);
}