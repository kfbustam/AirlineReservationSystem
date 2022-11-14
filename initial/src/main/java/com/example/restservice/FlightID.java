package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embedded;
import javax.persistence.Embeddable;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Embeddable
public class FlightID implements Serializable {
	private String flightNumber; // part of the primary key

	/*  Date format: yy-mm-dd, do not include hours, minutes, or seconds.
	** Example: 2022-03-22
	**The system only needs to support PST. You can ignore other time zones.  
	*/
    @JsonFormat(pattern="yyyy-mm-dd")
	private Date departureDate; //  serve as the primary key together with flightNumber    

    // default constructor
    public FlightID() {}

    public FlightID(String flightNumber, Date departureDate) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
    }

    // getters, equals() and hashCode() methods
    public String getFlightNumber() {
        return this.flightNumber;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }
}