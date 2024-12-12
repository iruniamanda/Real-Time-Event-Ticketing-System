package com.example.ticketingsystem.service.impl;
import com.example.ticketingsystem.domain.User;
import com.example.ticketingsystem.repository.UserRepository;
import com.example.ticketingsystem.resource.UserResource;
import com.example.ticketingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUserName(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Directly compare the passwords (not recommended for production)
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public User save(UserResource userResource){
        Optional<User> user = userRepository.findByUserName(userResource.getUserName());
         if (user.isPresent()){
             throw new RuntimeException("User Name already exists");
         }else {
             User regUser = new User();
             regUser.setUserName(userResource.getUserName());
             regUser.setFirstName(userResource.getFirstName());
             regUser.setLastName(userResource.getLastName());
             regUser.setEmail(userResource.getEmail());
             regUser.setAddress(userResource.getAddress());
             regUser.setMobileNo(Long.valueOf(userResource.getMobileNo()));
             regUser.setPassword(userResource.getPassword());
             regUser.setUserType(userResource.getUserType());
             return userRepository.saveAndFlush(regUser);
         }
    }
}