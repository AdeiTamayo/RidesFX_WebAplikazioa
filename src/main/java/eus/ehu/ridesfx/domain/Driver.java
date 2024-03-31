/**
 * The Driver class represents a driver user in the ridesharing system.
 * It extends the User class and adds additional fields and methods.
 *
 * This class is marked as an Entity, meaning that it is mapped to a table in the database.
 * The DiscriminatorValue annotation is used to distinguish between different types of users in the single table.
 *
 * The class implements Serializable, which means it can be converted to a byte stream and recovered later.
 *
 * @author Your Name
 * @version 1.0
 * @since 2023.3.6
 */
package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import eus.ehu.ridesfx.domain.Ride;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Ride> rides;

    /**
     * Default constructor for the Driver class.
     * Calls the superclass constructor with no arguments and initializes the rides list.
     */
    public Driver() {
        super();
        rides = new Vector<Ride>();
    }

    /**
     * Parameterized constructor for the Driver class.
     * Calls the superclass constructor with the provided arguments and initializes the rides list.
     *
     * @param email The email of the driver.
     * @param name The name of the driver.
     * @param username The username of the driver.
     * @param password The password of the driver.
     */
    public Driver(String email, String name, String username, String password) {
        super(username, password, name, email);
        rides = new Vector<Ride>();
    }

    /**
     * Returns a string representation of the driver.
     * The string contains the email, name and rides of the driver, separated by a semicolon.
     *
     * @return A string representation of the driver.
     */
    public String toString() {
        return super.getEmail() + ";" + super.getName() + rides;
    }

    /**
     * This method creates a new ride for the driver and adds it to the rides list.
     *
     * @param from The origin location of the ride.
     * @param to The destination location of the ride.
     * @param date The date of the ride.
     * @param nPlaces The number of places available in the ride.
     * @param price The price of the ride.
     * @return The created ride.
     */
    public Ride addRide(String from, String to, Date date, int nPlaces, float price) {
        Ride ride = new Ride(from, to, date, nPlaces, price, this);
        rides.add(ride);
        return ride;
    }

    /**
     * This method checks if a ride with the given parameters already exists for the driver.
     *
     * @param from The origin location of the ride.
     * @param to The destination location of the ride.
     * @param date The date of the ride.
     * @return true if the ride exists and false otherwise.
     */
    public boolean doesRideExists(String from, String to, Date date) {
        for (Ride r : rides)
            if ((java.util.Objects.equals(r.getFromLocation(), from)) && (java.util.Objects.equals(r.getToLocation(), to)) && (java.util.Objects.equals(r.getDate(), date)))
                return true;

        return false;
    }

    /**
     * This method checks if the driver is equal to another object.
     * The method checks if the other object is a Driver and if their emails are the same.
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
        Driver other = (Driver) obj;
        if (!super.getEmail().equals(other.getEmail()))
            return false;
        return true;

        /*return super.getEmail() == other.getEmail();*/
    }

    /**
     * This method removes a ride with the given parameters from the rides list.
     *
     * @param from The origin location of the ride.
     * @param to The destination location of the ride.
     * @param date The date of the ride.
     * @return The removed ride if it was found, null otherwise.
     */
    public Ride removeRide(String from, String to, Date date) {
        boolean found = false;
        int index = 0;
        Ride r = null;
        while (!found && index <= rides.size()) {
            r = rides.get(++index);
            if ((java.util.Objects.equals(r.getFromLocation(), from)) && (java.util.Objects.equals(r.getToLocation(), to)) && (java.util.Objects.equals(r.getDate(), date)))
                found = true;
        }

        if (found) {
            rides.remove(index);
            return r;
        } else return null;
    }

}