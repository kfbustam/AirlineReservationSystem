package com.example.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Passenger;
import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, String> {
  Optional<Passenger> findByPhone(String phone); 
}