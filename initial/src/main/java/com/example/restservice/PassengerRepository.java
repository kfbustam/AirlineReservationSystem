package com.example.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Passenger;
import java.util.List;
import java.util.Optional;

/**
 * The interface Passenger repository.
 */
public interface PassengerRepository extends JpaRepository<Passenger, String> {
    /**
     * Find by phone optional.
     *
     * @param phone the phone
     * @return the optional
     */
    Optional<Passenger> findByPhone(String phone);
}