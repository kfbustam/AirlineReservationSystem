package com.example.restservice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.*;
import org.springframework.boot.context.*;
import com.example.restservice.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.text.SimpleDateFormat;
import java.text.Format;

/**
 * The type Airline reservation system rest controller.
 */
@RestController
public class AirlineReservationSystemRESTController {

	@Autowired
	private Service service;

	/**
	 * Gets passenger.
	 *
	 * @param id    the id
	 * @param isXML the is xml
	 * @return the passenger
	 */
	@GetMapping("passenger/{id}")
	@ResponseBody
	public ResponseEntity<String> getPassenger(@PathVariable String id, @RequestParam(name="xml", required=false, defaultValue="false") String isXML) {
		String errorMessage = "{\"BadRequest\": {\"code\": \" HttpStatus.BAD_REQUEST \",\"msg\": \" Sorry, the requested passenger with ID " + id + " does not exist\"}}";
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Passenger> optionalPassenger = service.findPassenger(id);

			return optionalPassenger.map(
				passenger -> {
					JSONObject json = new JSONObject()
					.put("id", passenger.getId())
					.put("firstname", passenger	.getFirstName())
					.put("lastname", passenger.getLastName())
					.put("birthyear", passenger.getBirthYear())
					.put("gender", passenger.getGender())
					.put("phone", passenger.getPhone())
					.put("reservations", passenger.getReservations());
					ResponseEntity<String> res = new ResponseEntity<String>(
						isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
						responseHeaders,
						200
					);
					return res;
				}
			)
		.orElseGet(() -> new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST));
		} catch (Exception ex) {
			return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Create passenger response entity.
	 *
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 * @param birthyear the birthyear
	 * @param gender    the gender
	 * @param phone     the phone
	 * @param isXML     the is xml
	 * @return the response entity
	 */
	@PostMapping("passenger")
	@ResponseBody
	public ResponseEntity<String> createPassenger(
		@RequestParam(name="firstname", required=true) String firstname,
		@RequestParam(name="lastname", required=true) String lastname,
		@RequestParam(name="birthyear", required=true) int birthyear,
		@RequestParam(name="gender", required=true) String gender,
		@RequestParam(name="phone", required=true) String phone,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Passenger> optionalPassenger = service.findPassengerByPhone(phone);

			if (optionalPassenger == null) {
				return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": \"another passenger with the same number already exists.\"}}", HttpStatus.BAD_REQUEST);
			}

			Passenger passengerResponse = service.createPassenger(firstname, lastname, birthyear, gender, phone);

			JSONObject json = new JSONObject()
				.put("id", passengerResponse.getId())
				.put("firstname", passengerResponse	.getFirstName())
				.put("lastname", passengerResponse.getLastName())
				.put("birthyear", passengerResponse.getBirthYear())
				.put("gender", passengerResponse.getGender())
				.put("phone", passengerResponse.getPhone())
				.put("reservations", passengerResponse.getReservations());

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);

	  return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Update passenger response entity.
	 *
	 * @param id        the id
	 * @param firstname the firstname
	 * @param lastname  the lastname
	 * @param birthyear the birthyear
	 * @param gender    the gender
	 * @param phone     the phone
	 * @param isXML     the is xml
	 * @return the response entity
	 */
	@PutMapping("passenger/{id}")
	@ResponseBody
	public ResponseEntity<String> updatePassenger(
		@PathVariable String id,
		@RequestParam(name="firstname", required=true) String firstname,
		@RequestParam(name="lastname", required=true) String lastname,
		@RequestParam(name="birthyear", required=true) int birthyear,
		@RequestParam(name="gender", required=true) String gender,
		@RequestParam(name="phone", required=true) String phone,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			String errorMsg = "";
			Optional<Passenger> passengerFound = service.findPassenger(id);
			if (!passengerFound.isPresent()) {
				return new ResponseEntity<String>("Sorry, the requested passenger with ID " + id + " does not exist", HttpStatus.BAD_REQUEST);
			}

			Passenger passengerResponse = passengerFound.map((passenger)->{
				passenger.setFirstName(firstname);
				passenger.setLastName(lastname);
				passenger.setBirthYear(birthyear);
				passenger.setGender(gender);
				passenger.setPhone(phone);
				return service.updatePassenger(passenger);
			}).orElse(null);

			if (passengerResponse == null) {
				return new ResponseEntity<String>("Sorry, could not update passenger with ID " + id, HttpStatus.BAD_REQUEST);
			}

			JSONObject json = new JSONObject()
				.put("id", passengerResponse.getId())
				.put("firstname", passengerResponse	.getFirstName())
				.put("lastname", passengerResponse.getLastName())
				.put("birthyear", passengerResponse.getBirthYear())
				.put("gender", passengerResponse.getGender())
				.put("phone", passengerResponse.getPhone())
				.put("reservations", passengerResponse.getReservations());

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);

	  return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Delete passenger response entity.
	 *
	 * @param id    the id
	 * @param isXML the is xml
	 * @return the response entity
	 */
	@DeleteMapping("passenger/{id}")
	@ResponseBody
	public ResponseEntity<String> deletePassenger(
		@PathVariable String id,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Passenger> passengerFound = service.findPassenger(id);
			if (!passengerFound.isPresent()) {
				return new ResponseEntity<String>("Sorry, the requested passenger with ID " + id + " does not exist", HttpStatus.BAD_REQUEST);
			}

			service.deletePassenger(passengerFound, id);

			JSONObject json = new JSONObject()
				.put(
					"Response",
					new JSONObject()
						.put(
							"code",
							200
						)
						.put(
							"msg",
							"Passenger with id " + id + " is deleted successfully"
						)
				);

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);

	  return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Gets reservation.
	 *
	 * @param reservationNumber the reservation number
	 * @param isXML             the is xml
	 * @return the reservation
	 */
	@GetMapping("reservation/{reservationNumber}")
	@ResponseBody
	public ResponseEntity<String> getReservation(@RequestParam(name="reservationNumber", required=true) String reservationNumber, @RequestParam(name="xml", required=false, defaultValue="false") String isXML) {
		String errorMessage = "{\"BadRequest\": {\"code\": \" HttpStatus.BAD_REQUEST \",\"msg\": \" Sorry, the requested reservation with number " + reservationNumber + " does not exist\"}}";
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Reservation> optionalReservation = service.findReservation(reservationNumber);

			if (!optionalReservation.isPresent()) {
				return new ResponseEntity<String>("Sorry, could not find reservation with reservation number: " + reservationNumber, HttpStatus.BAD_REQUEST);
			}

			return optionalReservation.map(
				reservation -> {
					JSONObject json = new JSONObject()
					.put("flights", reservation	.getFlights())
					.put("price", reservation.getPrice())
					.put("destination", reservation.getDestination())
					.put("origin", reservation.getOrigin())
					.put("passenger", reservation.getPassenger())
					.put("reservationNumber", reservation.getReservationNumber());
					ResponseEntity<String> res = new ResponseEntity<String>(
						isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
						responseHeaders,
						200
					);
					return res;
				}
			)
		.orElseGet(() -> new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST));
		} catch (Exception ex) {
			return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Create reservation response entity.
	 *
	 * @param passengerId    the passenger id
	 * @param departureDates the departure dates
	 * @param flightNumbers  the flight numbers
	 * @param isXML          the is xml
	 * @return the response entity
	 */
	@PostMapping("reservation")
	@ResponseBody
	public ResponseEntity<String> createReservation(
		@RequestParam(name="passengerId", required=true) String passengerId,
		@RequestParam(name="departureDates", required=true) List<String> departureDates,
		@RequestParam(name="flightNumbers", required=true) List<String> flightNumbers,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {

		if (departureDates.size() != flightNumbers.size()) {
			return new ResponseEntity<String>("Sorry, the length of the departure dates does not match the length of the flight numbers", HttpStatus.BAD_REQUEST);
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Passenger> optionalPassenger = service.findPassenger(passengerId);

			if (!optionalPassenger.isPresent()) {
				return new ResponseEntity<String>("Sorry, could not find passenger with ID: " + passengerId, HttpStatus.BAD_REQUEST);
			}

			Passenger passenger = optionalPassenger.map(pass -> pass).orElse(null);

			List<Flight> flights = new ArrayList<Flight>();

			for (int i=0; i<flightNumbers.size(); i++) {
				SimpleDateFormat dateFormatter=new SimpleDateFormat("yyyy-mm-dd");
				Optional<Flight> flightFound = service.findFlightByID(new FlightID(flightNumbers.get(i),dateFormatter.parse(departureDates.get(i))));
				if (!flightFound.isPresent()) {
					return new ResponseEntity<String>("Sorry, we could not find the flight with flight number: " + flightNumbers.get(i) + " and departure date: " + departureDates.get(i), HttpStatus.BAD_REQUEST);
				}
				Flight flight = flightFound.map(actualFlight -> actualFlight).orElse(null);

				Date flightDepartureTime = flight.getDepartureTime();
				Date flightArrivalTime = flight.getArrivalTime();

				if (flights.size() == 0) {
					flights.add(flight);
				} else {
					for (int j=0; j < flights.size(); j++) {
						Date existingFlightDepartureTime = flights.get(j).getDepartureTime();
						Date existingFlightArrivalTime = flights.get(j).getArrivalTime();
						if(flightDepartureTime.compareTo(existingFlightArrivalTime) > 0 || existingFlightDepartureTime.compareTo(flightArrivalTime) > 0) {
							if (flight.getSeatsLeft() == 0) {
								return new ResponseEntity<String>("Sorry, flight number: " + flight + " has no more seats left", HttpStatus.BAD_REQUEST);
							}
							flight.setSeatsLeft(flight.getSeatsLeft() - 1);
							service.upsertFlight(flight);
							flights.add(flight);
						} else {
							return new ResponseEntity<String>("Sorry, a flight you gave overlaps with an existing flight in this reservation", HttpStatus.BAD_REQUEST);
						}
					}
				}

			}

			Reservation reservation = new Reservation(passenger, flights);
			service.upsertReservation(reservation);

			ArrayList<JSONObject> listOfFlightsAsJSON = new ArrayList<JSONObject>();
			int totalPriceOfAllFlights = 0;
			for (int i=0; i<flights.size(); i++) {
				listOfFlightsAsJSON.add(
					new JSONObject()
						.put("flightNumber", flights.get(i).getFlightID().getFlightNumber())
						.put("departureDate", flights.get(i).getFlightID().getDepartureDate())
						.put("departureTime", flights.get(i).getDepartureTime())
						.put("arrivalTime", flights.get(i).getArrivalTime())
						.put("origin", flights.get(i).getOrigin())
						.put("destination", flights.get(i).getDestination())
						.put("seatsLeft", flights.get(i).getSeatsLeft())
				);
				totalPriceOfAllFlights += flights.get(i).getPrice();
			}

			JSONObject json = new JSONObject()
			.put("flights", new JSONArray(listOfFlightsAsJSON))
			.put("price", totalPriceOfAllFlights)
			.put("destination", reservation.getDestination())
			.put("origin", reservation.getOrigin())
			.put("passenger", new JSONObject()
				.put("id", passenger.getId())
				.put("firstname", passenger	.getFirstName())
				.put("lastname", passenger.getLastName())
			)
			.put("reservationNumber", reservation.getReservationNumber());

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);
			return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Update reservation response entity.
	 *
	 * @param reservationNumber     the reservation number
	 * @param flightsAdded          the flights added
	 * @param departureDatesAdded   the departure dates added
	 * @param flightsRemoved        the flights removed
	 * @param departureDatesRemoved the departure dates removed
	 * @param isXML                 the is xml
	 * @return the response entity
	 */
	@PutMapping("reservation/{reservationNumber}")
	@ResponseBody
	public ResponseEntity<String> updateReservation(
		@PathVariable String reservationNumber,
		@RequestParam(name="flightsAdded", required=true) List<String> flightsAdded,
		@RequestParam(name="departureDatesAdded", required=true) List<String> departureDatesAdded,
		@RequestParam(name="flightsRemoved", required=true) List<String> flightsRemoved,
		@RequestParam(name="departureDatesRemoved", required=true) List<String> departureDatesRemoved,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		if (flightsAdded.size() != departureDatesAdded.size()) {
			return new ResponseEntity<String>("Sorry, the size of the list of flightsAdded does not match the size of the list of departureDatesAdded", HttpStatus.BAD_REQUEST);
		}

		if (flightsRemoved.size() != departureDatesRemoved.size()) {
			return new ResponseEntity<String>("Sorry, the size of the list of flightsRemoved does not match the size of the list of departureDatesRemoved", HttpStatus.BAD_REQUEST);
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Reservation> optionalReservation = service.findReservation(reservationNumber);

			Reservation reservation = optionalReservation.map(reserve -> reserve).orElse(null);

			if (reservation == null) {
				return new ResponseEntity<String>("Sorry, the requested reservation with reservation number " + reservationNumber + " does not exist", HttpStatus.BAD_REQUEST);
			}

			List<Flight> reservationFlights = reservation.getFlights();

			for (int j = 0; j < flightsRemoved.size(); j++)
			{
				Format df = new SimpleDateFormat("yyyy-mm-dd");
				String flightToBeRemoved = flightsRemoved.get(j);
				String departureDatesToBeRemoved = departureDatesRemoved.get(j);
				reservationFlights.removeIf(
					(reservationFlight) ->
						(
							reservationFlight.getFlightID().getFlightNumber() == flightToBeRemoved &&
							df.format(reservationFlight.getFlightID().getDepartureDate()) == departureDatesToBeRemoved
						)
				);
				reservation.setFlights(reservationFlights);
				service.upsertReservation(reservation);
			}

			for (int j = 0; j < flightsAdded.size(); j++)
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
				Optional<Flight> flightOptional = service.findFlightByID(new FlightID(flightsAdded.get(j), df.parse(departureDatesAdded.get(j))));
				flightOptional.ifPresent(flight -> reservationFlights.add(flight));
				reservation.setFlights(reservationFlights);
				service.upsertReservation(reservation);
			}


			List<Flight> flights = reservation.getFlights();

			ArrayList<JSONObject> listOfFlightsAsJSON = new ArrayList<JSONObject>();
			int totalPriceOfAllFlights = 0;
			for (int i=0; i<flights.size(); i++) {
				listOfFlightsAsJSON.add(
					new JSONObject()
						.put("flightNumber", flights.get(i).getFlightID().getFlightNumber())
						.put("departureDate", flights.get(i).getFlightID().getDepartureDate())
						.put("departureTime", flights.get(i).getDepartureTime())
						.put("arrivalTime", flights.get(i).getArrivalTime())
						.put("origin", flights.get(i).getOrigin())
						.put("destination", flights.get(i).getDestination())
						.put("seatsLeft", flights.get(i).getSeatsLeft())
				);
				totalPriceOfAllFlights += flights.get(i).getPrice();
			}

			Passenger passenger = reservation.getPassenger();

			JSONObject json = new JSONObject()
			.put("flights", new JSONArray(listOfFlightsAsJSON))
			.put("price", totalPriceOfAllFlights)
			.put("destination", reservation.getDestination())
			.put("origin", reservation.getOrigin())
			.put("passenger", new JSONObject()
				.put("id", passenger.getId())
				.put("firstname", passenger	.getFirstName())
				.put("lastname", passenger.getLastName())
			)
			.put("reservationNumber", reservation.getReservationNumber());

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);

			return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Delete reservation response entity.
	 *
	 * @param reservationNumber the reservation number
	 * @param isXML             the is xml
	 * @return the response entity
	 */
	@DeleteMapping("reservation/{reservationNumber}")
	@ResponseBody
	public ResponseEntity<String> deleteReservation(
		@PathVariable String reservationNumber,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Reservation> optionalReservation = service.findReservation(reservationNumber);

			if (!optionalReservation.isPresent()) {
				return new ResponseEntity<String>("Sorry, could not find reservation with reservation number: " + reservationNumber, HttpStatus.BAD_REQUEST);
			}

			optionalReservation.ifPresent((reservation) -> {
				List<Flight> passengerFlights = reservation.getFlights();
				for (int j = 0; j < passengerFlights.size(); j++)
				{
					passengerFlights.get(j).getPassengers().removeIf(passengerInFlight -> passengerInFlight.getId() == reservation.getPassenger().getId());
					service.upsertFlight(passengerFlights.get(j));
				}
				service.deleteReservation(reservation);
			});

			JSONObject json = new JSONObject()
				.put(
					"Response",
					new JSONObject()
						.put(
							"code",
							200
						)
						.put(
							"msg",
							"Reservation with number " + reservationNumber + " is deleted successfully"
						)
				);

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);

	  return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Gets flight.
	 *
	 * @param flightNumber  the flight number
	 * @param departureDate the departure date
	 * @param isXML         the is xml
	 * @return the flight
	 */
	@GetMapping("flight/{flightNumber}/{departureDate}")
	@ResponseBody
	public ResponseEntity<String> getFlight(
		@PathVariable String flightNumber,
		@PathVariable String departureDate,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		try {
			Optional<Flight> flightOptional = service.findFlightByID(new FlightID(flightNumber, df.parse(departureDate)));

			if (!flightOptional.isPresent()) {
				return new ResponseEntity<String>("Sorry, could not find flight with flightNumber: " + flightNumber + " and departureDate: " + departureDate, HttpStatus.BAD_REQUEST);
			}

			return flightOptional.map(
				flight -> {
					Plane plane = flight.getPlane();
					ArrayList<JSONObject> listOfPassengersAsJSON = new ArrayList<JSONObject>();

					flight.getPassengers().forEach(
						passenger -> {
							listOfPassengersAsJSON.add(
								new JSONObject()
									.put("id", passenger.getId())
									.put("firstname", passenger	.getFirstName())
									.put("lastname", passenger.getLastName())
									.put("birthyear", passenger.getBirthYear())
									.put("gender", passenger.getGender())
									.put("phone", passenger.getPhone())
									.put("reservations", passenger.getReservations())
							);
						}
					);

					JSONObject json = new JSONObject()
						.put("flightID", new JSONObject()
							.put("flightNumber",flightNumber)
							.put("departureDate",departureDate)
						)
						.put("departureTime", df.format(flight.getDepartureTime()))
						.put("arrivalTime", df.format(flight.getArrivalTime()))
						.put("price", flight.getPrice())
						.put("origin", flight.getOrigin())
						.put("seatsLeft", flight.getSeatsLeft())
						.put("description", flight.getDescription())
						.put("plane",
							new JSONObject()
								.put("model", plane.getModel())
								.put("capacity", plane.getCapacity())
								.put("manufacturer", plane.getManufacturer())
								.put("yearOfManufacturer", plane.getYearOfManufacturer())
						)
						.put("passengers", new JSONArray(listOfPassengersAsJSON));
					ResponseEntity<String> res = new ResponseEntity<String>(
						isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
						responseHeaders,
						200
					);
					return res;
				}
			)
		.orElseGet(() -> new ResponseEntity<String>("Flight is not found", HttpStatus.BAD_REQUEST));
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Upsert flight response entity.
	 *
	 * @param flightNumber      the flight number
	 * @param departureDate     the departure date
	 * @param price             the price
	 * @param origin            the origin
	 * @param destination       the destination
	 * @param departureTime     the departure time
	 * @param arrivalTime       the arrival time
	 * @param description       the description
	 * @param capacity          the capacity
	 * @param model             the model
	 * @param manufacturer      the manufacturer
	 * @param yearOfManufacture the year of manufacture
	 * @param isXML             the is xml
	 * @return the response entity
	 */
	@PostMapping("flight/{flightNumber}/{departureDate}")
	@ResponseBody
	public ResponseEntity<String> upsertFlight(
		@PathVariable(name="flightNumber", required=true) String flightNumber,
		@PathVariable(name="departureDate", required=true) String departureDate,
		@RequestParam(name="price", required=true) int price,
		@RequestParam(name="origin", required=true) String origin,
		@RequestParam(name="destination", required=true) String destination,
		@RequestParam(name="departureTime", required=true) String departureTime,
		@RequestParam(name="arrivalTime", required=true) String arrivalTime,
		@RequestParam(name="description", required=true) String description,
		@RequestParam(name="capacity", required=true) int capacity,
		@RequestParam(name="model", required=true) String model,
		@RequestParam(name="manufacturer", required=true) String manufacturer,
		@RequestParam(name="yearOfManufacture", required=true) int yearOfManufacture,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Optional<Flight> flightOptional = service.findFlightByID(new FlightID(flightNumber, df.parse(departureDate)));

			Flight flightFoundOrCreated = null;
			if (flightOptional.isPresent()) {
				flightFoundOrCreated = flightOptional.map(
					flight -> {
						flight.setDepartureTime(flight.getDepartureTime());
						flight.setArrivalTime(flight.getArrivalTime());
						flight.setPrice(price);
						flight.setOrigin(origin);
						flight.setDestination(destination);
						flight.setDescription(description);
						flight.setPlane(new Plane(model, capacity, manufacturer, yearOfManufacture));
						return service.upsertFlight(flight);
					}
				).orElse(null);
			} else {
				Plane plane = new Plane(model, capacity, manufacturer, yearOfManufacture);
				flightFoundOrCreated = new Flight(flightNumber, df.parse(departureDate), df.parse(departureTime), df.parse(arrivalTime), price, origin, destination, capacity, description, plane, new ArrayList<Passenger>());
				service.upsertFlight(flightFoundOrCreated);
			}

			Plane plane = flightFoundOrCreated.getPlane();
			ArrayList<JSONObject> listOfPassengersAsJSON = new ArrayList<JSONObject>();

			flightFoundOrCreated.getPassengers().forEach(
				passenger -> {
					listOfPassengersAsJSON.add(
						new JSONObject()
							.put("id", passenger.getId())
							.put("firstname", passenger	.getFirstName())
							.put("lastname", passenger.getLastName())
							.put("birthyear", passenger.getBirthYear())
							.put("gender", passenger.getGender())
							.put("phone", passenger.getPhone())
							.put("reservations", passenger.getReservations())
					);
				}
			);

			JSONObject json = new JSONObject()
				.put("flightID", new JSONObject()
					.put("flightNumber",flightNumber)
					.put("departureDate",departureDate)
				)
				.put("departureTime", df.format(flightFoundOrCreated.getDepartureTime()))
				.put("arrivalTime", df.format(flightFoundOrCreated.getArrivalTime()))
				.put("price", flightFoundOrCreated.getPrice())
				.put("origin", flightFoundOrCreated.getOrigin())
				.put("seatsLeft", flightFoundOrCreated.getSeatsLeft())
				.put("description", flightFoundOrCreated.getDescription())
				.put("plane",
					new JSONObject()
						.put("model", plane.getModel())
						.put("capacity", plane.getCapacity())
						.put("manufacturer", plane.getManufacturer())
						.put("yearOfManufacturer", plane.getYearOfManufacturer())
				)
				.put("passengers", new JSONArray(listOfPassengersAsJSON));
			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);
			return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Delete flight response entity.
	 *
	 * @param flightNumber  the flight number
	 * @param departureDate the departure date
	 * @param isXML         the is xml
	 * @return the response entity
	 */
	@DeleteMapping("airline/{flightNumber}/{departureDate}")
	@ResponseBody
	public ResponseEntity<String> deleteFlight(
		@PathVariable(name="flightNumber", required=true) String flightNumber,
		@PathVariable(name="departureDate", required=true) String departureDate,
		@RequestParam(name="xml", required=false, defaultValue="false") String isXML
	) {
		HttpHeaders responseHeaders = new HttpHeaders();
		if (isXML.equals("true")) {
			responseHeaders.add("Content-Type", "application/xml; charset=utf-8");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Optional<Flight> flightOptional = service.findFlightByID(new FlightID(flightNumber, df.parse(departureDate)));

			if (!flightOptional.isPresent()) {
				return new ResponseEntity<String>("Sorry, could not find flight with flightNumber: " + flightNumber + " and departureDate: " + departureDate, HttpStatus.BAD_REQUEST);
			}

			Flight flight = flightOptional.map(foundFlight -> foundFlight).orElse(null);

			if (flight.getPassengers().size() > 0) {
				return new ResponseEntity<String>("Sorry, flight cannot be deleted because it needs to carry at least one passenger", HttpStatus.BAD_REQUEST);
			}

			service.deleteFlight(flight);

			JSONObject json = new JSONObject()
				.put(
					"Response",
					new JSONObject()
						.put(
							"code",
							200
						)
						.put(
							"msg",
							"Flight with number " + flightNumber + " and departure date: " + departureDate + " is deleted successfully"
						)
				);

			ResponseEntity<String> res = new ResponseEntity<String>(
				isXML.equals("true") ? "<Response>" + XML.toString(json) + "</Response>"  : json.toString(),
				responseHeaders,
				200
			);

	  return res;
		} catch (Exception ex) {
			return new ResponseEntity<String>("{\"BadRequest\": {\"code\": \" 400 \",\"msg\": " + ex.getMessage() +"}}", HttpStatus.BAD_REQUEST);
		}
	}
}
