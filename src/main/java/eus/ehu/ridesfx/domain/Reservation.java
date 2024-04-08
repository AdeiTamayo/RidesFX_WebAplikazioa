/**
 * The Reservation class represents a reservation in the ridesharing system.
 * It is marked as an Entity, meaning that it is mapped to a table in the database.
 * The class implements Serializable, which means it can be converted to a byte stream and recovered later.
 *
 * Each reservation has a unique reservation number, a number of places, a date, a state, and a reference to the Traveler who made the reservation.
 */
package eus.ehu.ridesfx.domain;

import eus.ehu.ridesfx.utils.Dates;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Reservation implements Serializable{

    @Id
    @GeneratedValue
    private int reservationNumber;
    private int nPlaces;
    private Dates date;

    //States can be "accept","reject", "pending".
    private String state;

    @ManyToOne
    private Traveler traveler;

    /**
     * Default constructor for the Reservation class.
     */
    public Reservation() {
    }

    /**
     * Parameterized constructor for the Reservation class.
     *
     * @param reservationNumber The unique identifier of the reservation.
     * @param nPlaces The number of places in the reservation.
     * @param date The date of the reservation.
     * @param state The state of the reservation. Can be "accept", "reject", or "pending".
     * @param traveler The Traveler who made the reservation.
     */
    public Reservation(Integer reservationNumber, int nPlaces, Dates date, String state, Traveler traveler) {
        this.reservationNumber = reservationNumber;
        this.nPlaces = nPlaces;
        this.date = date;
        this.state = state;
        this.traveler = traveler;
    }

    /**
     * Returns a string representation of the reservation.
     * The string contains the number of places, the date, and the state of the reservation, separated by a semicolon.
     *
     * @return A string representation of the reservation.
     */
    public int getReservationNumber() {
        return reservationNumber;
    }

    /**
     * Sets the reservation number of the reservation.
     *
     * @param reservationNumber The new reservation number.
     */


    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    /**
     * Returns the number of places in the reservation.
     *
     * @return The number of places in the reservation.
     */

    public int getNPlaces() {
        return nPlaces;
    }

    /**
     * Sets the number of places in the reservation.
     *
     * @param nPlaces The new number of places.
     */

    public void setNPlaces(int nPlaces) {
        this.nPlaces = nPlaces;
    }

    /**
     * Returns the date of the reservation.
     *
     * @return The date of the reservation.
     */

    public Dates getDate() {
        return date;
    }
    /**
     * Sets the date of the reservation.
     *
     * @param date The new date of the reservation.
     */

    public void setDate(Dates date) {
        this.date = date;
    }

    /**
     * Returns the state of the reservation.
     *
     * @return The state of the reservation.
     */

    public String getState() {
        return state;
    }

    /**
     * Sets the state of the reservation.
     *
     * @param state The new state of the reservation.
     */

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the Traveler who made the reservation.
     *
     * @return The Traveler who made the reservation.
     */

    public Traveler getTraveler() {
        return traveler;
    }

    /**
     * Sets the Traveler who made the reservation.
     * @param traveler The Traveler who made the reservation.
     */

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    /**
     * Returns a string representation of the reservation.
     * The string contains the number of places, the date, and the state of the reservation, separated by a semicolon.
     *
     * @return A string representation of the reservation.
     */

    @Override
    public String toString() {
        return "Reservation{" +
                "nPlaces=" + nPlaces +
                ", date=" + date +
                ", state='" + state + '\'' +
                '}';
    }




}
