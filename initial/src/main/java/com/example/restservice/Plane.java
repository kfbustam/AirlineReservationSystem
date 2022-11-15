package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The type Plane.
 */
@Embeddable
public class Plane {
	private String model;
	private int capacity;
	private String manufacturer;
	private int yearOfManufacture;

    /**
     * Instantiates a new Plane.
     */
    public Plane () {}

    /**
     * Instantiates a new Plane.
     *
     * @param model             the model
     * @param capacity          the capacity
     * @param manufacturer      the manufacturer
     * @param yearOfManufacture the year of manufacture
     */
    public Plane(String model, int capacity, String manufacturer, int yearOfManufacture) {
		this.model = model;
		this.capacity = capacity;
		this.manufacturer = manufacturer;
		this.yearOfManufacture = yearOfManufacture;
	}

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
		return this.model;
	}

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
		this.model = model;
	}

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
		return this.capacity;
	}

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

    /**
     * Gets manufacturer.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
		return this.manufacturer;
	}


    /**
     * Sets manufacturer.
     *
     * @param manufacturer the manufacturer
     */
    public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

    /**
     * Gets year of manufacturer.
     *
     * @return the year of manufacturer
     */
    public int getYearOfManufacturer() {
		return this.yearOfManufacture;
	}

    /**
     * Sets year of manufacture.
     *
     * @param yearOfManufacture the year of manufacture
     */
    public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}
}
