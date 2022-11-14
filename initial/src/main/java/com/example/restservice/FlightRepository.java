package com.example.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.restservice.Flight;
import com.example.restservice.FlightID;

public interface FlightRepository extends JpaRepository<Flight, FlightID> {

}