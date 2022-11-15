package com.example.restservice;

import org.springframework.data.jpa.repository.*;
import com.example.restservice.Reservation;

/**
 * The interface Reservation repository.
 */
public interface ReservationRepository extends JpaRepository<Reservation, String> {

}