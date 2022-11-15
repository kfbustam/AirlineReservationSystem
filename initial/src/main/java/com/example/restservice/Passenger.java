package com.example.restservice;

import com.example.restservice.Flight;

import org.springframework.boot.SpringApplication;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.*;
import java.util.List;
import com.example.restservice.*;

/**
 * The type Passenger.
 */
@Entity
public class Passenger{

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;   // primary key

	private String firstname;
	private String lastname;
	private int birthyear;  // Full form only (see definition below)
	private String gender;  // Full form only
	private String phone; // Phone numbers must be unique.   Full form only

	@OneToMany(targetEntity=Reservation.class, mappedBy="passenger", fetch=FetchType.EAGER)
	private List<Reservation> reservations;   // Full form only

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "flightNumber", insertable = false, updatable = false),
		@JoinColumn(name = "departureDate", insertable = false, updatable = false)
	})
	private Flight flight;

    /**
     * Instantiates a new Passenger.
     */
    public Passenger() {}

    /**
     * Instantiates a new Passenger.
     *
     * @param firstname the firstname
     * @param lastname  the lastname
     * @param birthyear the birthyear
     * @param gender    the gender
     * @param phone     the phone
     */
    public Passenger(String firstname, String lastname, int birthyear, String gender, String phone) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthyear = birthyear;
		this.gender = gender;
		this.phone = phone;
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
		return this.id;
	}

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
		return this.firstname;
	}

    /**
     * Gets flight.
     *
     * @return the flight
     */
    public Flight getFlight() {
		return this.flight;
	}

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
		return this.lastname;
	}

    /**
     * Sets last name.
     *
     * @param lastname the lastname
     */
    public void setLastName(String lastname) {
		this.lastname = lastname;
	}

    /**
     * Gets birth year.
     *
     * @return the birth year
     */
    public int getBirthYear() {
		return this.birthyear;
	}

    /**
     * Sets birth year.
     *
     * @param birthyear the birthyear
     */
    public void setBirthYear(int birthyear) {
		this.birthyear = birthyear;
	}

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
		return this.gender;
	}

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
		this.gender = gender;
	}

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
		return this.phone;
	}

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
		this.phone = phone;
	}

    /**
     * Gets reservations.
     *
     * @return the reservations
     */
    public List<Reservation> getReservations() {
		return this.reservations;
	}

    /**
     * Sets reservations.
     *
     * @param reservations the reservations
     */
    public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
