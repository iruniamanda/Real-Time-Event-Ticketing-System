package com.example.ticketingsystem.repository;
import com.example.ticketingsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> { //tableName and primarykeyType
    Optional<User> findByUserName(String username);

}