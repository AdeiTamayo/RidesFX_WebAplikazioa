/**
 * The Traveler class represents a traveler user in the ridesharing system.
 * It extends the User class and adds no additional fields or methods.
 * <p>
 * This class is marked as an Entity, meaning that it is mapped to a table in the database.
 * The DiscriminatorValue annotation is used to distinguish between different types of users in the single table.
 * <p>
 * The class implements Serializable, which means it can be converted to a byte stream and recovered later.
 */
package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)

    //FIXME why does it give an error?
    private List<Reservation> reservations;

    /**
     * Default constructor for the Traveler class.
     * Calls the superclass constructor with no arguments.
     */
    public Traveler() {
        super();
        reservations = new Vector<Reservation>();
    }

    /**
     * Parameterized constructor for the Traveler class.
     * Calls the superclass constructor with the provided arguments.
     *
     * @param email The email of the traveler.
     * @param name The name of the traveler.
     * @param username The username of the traveler.
     * @param password The password of the traveler.
     */
    public Traveler(String email, String name, String username, String password) {
        super(username, password, name, email);
        reservations = new Vector<Reservation>();
    }

    /**
     * Returns a string representation of the traveler.
     * The string contains the email the name of the traveler and its Reservations, separated by a semicolon.
     *
     * @return A string representation of the traveler.
     */
    public String toString() {
        return super.getEmail() + ";" + super.getName() + getReservations();
    }


    /**
     * Returns the reservations of the traveler.
     *
     * @return The reservations of the traveler.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the reservations of the traveler.
     *
     * @param reservations The new reservations of the traveler.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Adds a reservation to the traveler.
     *
     * @param reservation The reservation to add.
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }



}