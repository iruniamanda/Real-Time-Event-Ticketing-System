package com.example.ticketingsystem.repository;
import com.example.ticketingsystem.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> { //tableName and primarykeyType
    Optional<Vendor> findByUserName(String username);

}