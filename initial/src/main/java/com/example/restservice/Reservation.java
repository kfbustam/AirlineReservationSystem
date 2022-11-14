package com.example.restservice;

import com.example.restservice.Flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Entity
public class Reservation {

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="reservationNumber", strategy = "uuid")
	private String reservationNumber; // primary key

	private String origin;
	private String destination;  
	private int price; // sum of each flight’s price.   // Full form only

	@ManyToOne
	@JoinColumn(name = "passenger_id")
	private Passenger passenger;     // Full form only
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "reservation_id")
	@NotEmpty
	private List<Flight> flights;    // Full form only, CANNOT be empty, ordered chronologically by departureTime

	public Reservation() {}

	public Reservation(Passenger passenger, List<Flight> flights) {
		this.passenger = passenger;
		this.flights = flights;
	}

	public String getReservationNumber() {
		return this.reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public Passenger getPassenger() {
		return this.passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getPrice() {
		return this.price;
	}

	public void setModel(int price) {
		this.price = price;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
}
