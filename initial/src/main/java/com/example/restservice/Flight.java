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

/**
 * The type Flight.
 */
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

    /**
     * Instantiates a new Flight.
     */
    public Flight() {}

    /**
     * Instantiates a new Flight.
     *
     * @param flightNumber  the flight number
     * @param departureDate the departure date
     * @param departureTime the departure time
     * @param arrivalTime   the arrival time
     * @param price         the price
     * @param origin        the origin
     * @param destination   the destination
     * @param seatsLeft     the seats left
     * @param description   the description
     * @param plane         the plane
     * @param passengers    the passengers
     */
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

    /**
     * Sets departure time.
     *
     * @param departureTime the departure time
     */
    public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

    /**
     * Gets flight number.
     *
     * @return the flight number
     */
    public String getFlightNumber() {
		return this.getFlightID().getFlightNumber();
	}

    /**
     * Gets departure date.
     *
     * @return the departure date
     */
    public Date getDepartureDate() {
		return this.getFlightID().getDepartureDate();
	}

    /**
     * Gets departure time.
     *
     * @return the departure time
     */
    public Date getDepartureTime() {
		return this.departureTime;
	}

    /**
     * Gets arrival time.
     *
     * @return the arrival time
     */
    public Date getArrivalTime() {
		return this.arrivalTime;
	}

    /**
     * Sets arrival time.
     *
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

    /**
     * Gets seats left.
     *
     * @return the seats left
     */
    public int getSeatsLeft() {
		return this.seatsLeft;
	}

    /**
     * Sets seats left.
     *
     * @param seatsLeft the seats left
     */
    public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
		return this.description;
	}

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Gets plane.
     *
     * @return the plane
     */
    public Plane getPlane() {
		return this.plane;
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
     * Sets plane.
     *
     * @param plane the plane
     */
    public void setPlane(Plane plane) {
		this.plane = plane;
	}

    /**
     * Gets passengers.
     *
     * @return the passengers
     */
    public List<Passenger> getPassengers() {
		return this.passengers;
	}

    /**
     * Sets passengers.
     *
     * @param passengers the passengers
     */
    public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

    /**
     * Gets flight id.
     *
     * @return the flight id
     */
    public FlightID getFlightID() {
		return this.flightID;
	}

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
		this.price = price;
	}
}
