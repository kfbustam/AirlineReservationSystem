package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Embedded;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.restservice.FlightID;
import com.example.restservice.Passenger;
import java.util.Date;
import java.util.List;
import javax.persistence.EmbeddedId;

@Entity
public class Flight {
	@EmbeddedId
	private FlightID flightID;  

	/*  Date format: yy-mm-dd-hh, do not include minutes or seconds.
	** Example: 2017-03-22-19
	*/
	@JsonFormat(pattern="yyyy-mm-dd-hh")
	private Date departureTime; // Must be within the same calendar day as departureDate.
	@JsonFormat(pattern="yyyy-mm-dd-hh") 
	private Date arrivalTime;
	private int price;    // Full form only
	private String origin;
	private String destination;  
	private int seatsLeft; 
	private String description;   // Full form only

	@Embedded
	private Plane plane;  // Embedded,    Full form only
	
	@OneToMany(targetEntity=Passenger.class, mappedBy="flight", fetch=FetchType.EAGER)
	private List<Passenger> passengers;    // Full form only

	public Flight() {}
	
	public Flight(String flightNumber, Date departureDate, Date departureTime, Date arrivalTime, int price, String origin, String destination, int seatsLeft, String description, Plane plane, List<Passenger> passengers) {
		FlightID flightID = new FlightID(flightNumber, departureDate);
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
		this.origin = origin;
		this.destination = destination;
		this.seatsLeft = seatsLeft;
		this.description = description;
		this.plane = plane;
		this.passengers = passengers;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getFlightNumber() {
		return this.getFlightID().getFlightNumber();
	}

	public Date getDepartureDate() {
		return this.getFlightID().getDepartureDate();
	}

	public Date getDepartureTime() {
		return this.departureTime;
	}

	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getSeatsLeft() {
		return this.seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public List<Passenger> getPassengers() {
		return this.passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public FlightID getFlightID() {
		return this.flightID;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
