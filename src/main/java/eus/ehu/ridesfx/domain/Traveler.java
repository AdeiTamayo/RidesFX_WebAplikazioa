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
import java.util.Date;


@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Reservation> reservations;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Alert> alerts;

    /**
     * Default constructor for the Traveler class.
     * Calls the superclass constructor with no arguments.
     */
    public Traveler() {
        super();
        reservations = new Vector<Reservation>();
        alerts = new Vector<Alert>();
    }

    /**
     * Parameterized constructor for the Traveler class.
     * Calls the superclass constructor with the provided arguments.
     *
     * @param email    The email of the traveler.
     * @param name     The name of the traveler.
     * @param username The username of the traveler.
     * @param password The password of the traveler.
     */
    public Traveler(String email, String name, String username, String password) {
        super(username, password, name, email);
        reservations = new Vector<Reservation>();
        alerts = new Vector<Alert>();
    }


    /**
     * Returns a string representation of the traveler.
     * The string contains the email the name of the traveler, the alerts and its Reservations, separated by a semicolon.
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

    /**
     * Delets a reservation from the traveler.
     * @param reservation The reservation to delete.
     */
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setTraveler(null);
    }

    /**
     * This method creates a new alert for the traveler and adds it to the alerts list.
     *
     * @param from    The origin location of the alert.
     * @param to      The destination location of the alert.
     * @param date    The date of the alert.
     * @param nPlaces The number of places available in the alert.
     * @return The created alert.
     */
    public Alert addAlert(String from, String to, Date date, int nPlaces) {
        Alert alert = new Alert(from, to, date, nPlaces, this);
        alerts.add(alert);
        return alert;
    }

    /**
     * This method checks if an alert with the given parameters already exists for the traveler.
     *
     * @param from The origin location of the alert.
     * @param to   The destination location of the alert.
     * @param date The date of the alert.
     * @return true if the alert exists and false otherwise.
     */
    public boolean doesAlertExists(String from, String to, Date date) {
        for (Alert a : alerts)
            if ((java.util.Objects.equals(a.getFromLocation(), from)) && (java.util.Objects.equals(a.getToLocation(), to)) && (java.util.Objects.equals(a.getDate(), date)))
                return true;

        return false;
    }

    public void deleteAlert(Alert alert) {
        alerts.remove(alert);
    }

    /**
     * This method checks if the traveler is equal to another object.
     * The method checks if the other object is a Traveler and if their emails are the same.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal and false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Traveler other = (Traveler) obj;
        if (!super.getEmail().equals(other.getEmail()))
            return false;
        return true;

        /*return super.getEmail() == other.getEmail();*/
    }

    /**
     * This method removes an alert with the given parameters from the alerts list.
     *
     * @param from The origin location of the alert.
     * @param to   The destination location of the alert.
     * @param date The date of the alert.
     * @return The removed alert if it was found, null otherwise.
     */
    public Alert removeAlert(String from, String to, Date date) {
        boolean found = false;
        int index = 0;
        Alert a = null;
        while (!found && index <= alerts.size()) {
            a = alerts.get(++index);
            if ((java.util.Objects.equals(a.getFromLocation(), from)) && (java.util.Objects.equals(a.getToLocation(), to)) && (java.util.Objects.equals(a.getDate(), date)))
                found = true;
        }

        if (found) {
            alerts.remove(index);
            return a;
        } else return null;
    }

    /*
    public void addReservation(String state, int nPlaces, Date date, Ride ride) {
        Reservation reservation = new Reservation(state, nPlaces, date, this, ride);
        reservations.add(reservation);
    }

     */


}