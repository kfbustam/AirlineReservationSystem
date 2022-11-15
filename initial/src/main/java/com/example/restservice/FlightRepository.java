package com.example.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Flight;
import com.example.restservice.FlightID;

/**
 * The interface Flight repository.
 */
public interface FlightRepository extends JpaRepository<Flight, FlightID> {

}