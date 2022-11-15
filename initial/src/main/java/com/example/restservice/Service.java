package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class Service {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Optional<Passenger> findPassenger(String id) {
        return passengerRepository.findById(id);
    }

    public Optional<Passenger> findPassengerByPhone(String phone) {
        return passengerRepository.findByPhone(phone);
    }

    public Passenger createPassenger(String firstname, String lastname, int birthyear, String gender, String phone) {
        Passenger newPassenger = new Passenger(firstname, lastname, birthyear, gender, phone);
        Passenger passengerResponse = passengerRepository.saveAndFlush(newPassenger);
        return passengerResponse;
    }

    public Passenger updatePassenger(Passenger passenger) {
        return passengerRepository.saveAndFlush(passenger);
    }

    public void deletePassenger(Optional<Passenger> passengerFound, String id) {
        passengerFound.ifPresent((passenger) -> {
            List<Reservation> passengerReservations = passenger.getReservations();
            for (int i = 0; i < passengerReservations.size(); i++)
            {
                List<Flight> passengerFlights = passengerReservations.get(i).getFlights();
                for (int j = 0; j < passengerFlights.size(); j++)
                {
                    passengerFlights.get(j).getPassengers().removeIf(passengerInFlight -> passengerInFlight.getId() == id);
                    flightRepository.saveAndFlush(passengerFlights.get(j));
                }
                reservationRepository.delete(passengerReservations.get(i));
            }
            passengerRepository.delete(passenger);
        });
    }

    public Optional<Reservation> findReservation(String reservationNumber) {
        return reservationRepository.findById(reservationNumber);
    }

    public Optional<Flight> findFlightByID(FlightID flightID) {
        return flightRepository.findById(flightID);
    }

    public Flight upsertFlight(Flight flight) {
        return flightRepository.saveAndFlush(flight);
    }

    public void deleteFlight(Flight flight) {
        flightRepository.delete(flight);
    }

    public Reservation upsertReservation(Reservation reservation) {
        return reservationRepository.saveAndFlush(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
