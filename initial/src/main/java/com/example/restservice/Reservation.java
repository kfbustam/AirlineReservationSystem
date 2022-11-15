package com.example.restservice;

import com.example.restservice.Flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

/**
 * The type Reservation.
 */
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

    /**
     * Instantiates a new Reservation.
     */
    public Reservation() {}

    /**
     * Instantiates a new Reservation.
     *
     * @param passenger the passenger
     * @param flights   the flights
     */
    public Reservation(Passenger passenger, List<Flight> flights) {
		this.passenger = passenger;
		this.flights = flights;
	}

    /**
     * Gets reservation number.
     *
     * @return the reservation number
     */
    public String getReservationNumber() {
		return this.reservationNumber;
	}

    /**
     * Sets reservation number.
     *
     * @param reservationNumber the reservation number
     */
    public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

    /**
     * Gets passenger.
     *
     * @return the passenger
     */
    public Passenger getPassenger() {
		return this.passenger;
	}

    /**
     * Sets passenger.
     *
     * @param passenger the passenger
     */
    public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

    /**
     * Gets origin.
     *
     * @return the origin
     */
    public String getOrigin() {
		return this.origin;
	}

    /**
     * Sets origin.
     *
     * @param origin the origin
     */
    public void setOrigin(String origin) {
		this.origin = origin;
	}

    /**
     * Gets destination.
     *
     * @return the destination
     */
    public String getDestination() {
		return this.destination;
	}

    /**
     * Sets destination.
     *
     * @param destination the destination
     */
    public void setDestination(String destination) {
		this.destination = destination;
	}

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
		return this.price;
	}

    /**
     * Sets model.
     *
     * @param price the price
     */
    public void setModel(int price) {
		this.price = price;
	}

    /**
     * Gets flights.
     *
     * @return the flights
     */
    public List<Flight> getFlights() {
		return this.flights;
	}

    /**
     * Sets flights.
     *
     * @param flights the flights
     */
    public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
}
