/**
 * The Reservation class represents a reservation in the ridesharing system.
 * It is marked as an Entity, meaning that it is mapped to a table in the database.
 * The class implements Serializable, which means it can be converted to a byte stream and recovered later.
 * <p>
 * Each reservation has a unique reservation number, a number of places, a date, a state, and a reference to the Traveler who made the reservation.
 */
package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    private int reservationNumber;

    private int nPlaces;

    private String state;

    private Date reservationDate;

    @ManyToOne
    private Traveler traveler;

    @ManyToOne
    private Ride ride;

    public Reservation() {
        super();
    }

    public Reservation(Traveler traveler, Ride ride, int numSeats, String state, Date reservationDate) {
        super();
        this.nPlaces = numSeats;
        this.state = state; // Set the state of the reservation
        this.traveler = traveler;
        this.ride = ride;
        this.reservationDate = reservationDate;
    }

    public Reservation(int reservationNumber, Traveler traveler, Ride ride, int numSeats, String state) {
        super();
        this.reservationNumber = reservationNumber;
        this.traveler = traveler;
        this.ride = ride;
        this.nPlaces = numSeats;
        this.state = state;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getNumPlaces() {
        return nPlaces;
    }

    public void setNumPlaces(int nPlaces) {
        this.nPlaces = nPlaces;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    public Ride getRide() {
        return this.ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "nPlaces=" + nPlaces +
                ", state='" + state + '\'' +
                ", travelerId=" + traveler.getUserName() +
                ", ride=" + ride +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
