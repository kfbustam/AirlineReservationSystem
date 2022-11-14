package com.example.restservice;

import org.springframework.data.jpa.repository.*;
import com.example.restservice.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, String> {

}