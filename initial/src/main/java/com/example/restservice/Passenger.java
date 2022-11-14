package com.example.restservice;

import com.example.restservice.Flight;

import org.springframework.boot.SpringApplication;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.*;
import java.util.List;

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

	public Passenger() {}

	public Passenger(String firstname, String lastname, int birthyear, String gender, String phone) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthyear = birthyear;
		this.gender = gender;
		this.phone = phone;
	}

	public String getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getLastName() {
		return this.lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public int getBirthYear() {
		return this.birthyear;
	}

	public void setBirthYear(int birthyear) {
		this.birthyear = birthyear;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
