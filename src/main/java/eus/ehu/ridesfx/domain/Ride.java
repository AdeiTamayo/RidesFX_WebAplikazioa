/**
 * The Ride class represents a ride in the ridesharing system.
 * It is marked as an Entity, meaning that it is mapped to a table in the database.
 * Each Ride is associated with a Driver.
 * The class implements Serializable, which means it can be converted to a byte stream and recovered later.
 */
package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class Ride implements Serializable {
    @Id
    @GeneratedValue
    private Integer rideNumber;
    private String fromLocation;
    private String toLocation;
    private int numPlaces;
    private Date date;
    private float price;

    @ManyToOne
    private Driver driver;

    @OneToOne
    private Location location;


    /**
     * Default constructor for the Ride class.
     * Initializes a new Ride with no parameters.
     */
    public Ride() {
        super();
    }

    /**
     * Parameterized constructor for the Ride class.
     * Initializes a new Ride with the provided parameters.
     *
     * @param rideNumber The number of the ride.
     * @param from       The origin location of the ride.
     * @param to         The destination location of the ride.
     * @param date       The date of the ride.
     * @param numPlaces  The number of places available in the ride.
     * @param price      The price of the ride.
     * @param driver     The driver of the ride.
     */
    public Ride(Integer rideNumber, String from, String to, Date date, int numPlaces, float price, Driver driver) {
        super();
        this.rideNumber = rideNumber;
        this.fromLocation = from;
        this.toLocation = to;
        this.numPlaces = numPlaces;
        this.date = date;
        this.price = price;
        this.driver = driver;
    }

    /**
     * Parameterized constructor for the Ride class.
     * Initializes a new Ride with the provided parameters.
     *
     * @param from      The origin location of the ride.
     * @param to        The destination location of the ride.
     * @param date      The date of the ride.
     * @param numPlaces The number of places available in the ride.
     * @param price     The price of the ride.
     * @param driver    The driver of the ride.
     */
    public Ride(String from, String to, Date date, int numPlaces, float price, Driver driver) {
        super();
        this.fromLocation = from;
        this.toLocation = to;
        this.numPlaces = numPlaces;
        this.date = date;
        this.price = price;
        this.driver = driver;
    }


    /**
     * Get the  number of the ride
     *
     * @return the ride number
     */
    public Integer getRideNumber() {
        return rideNumber;
    }


    /**
     * Set the ride number to a ride
     *
     * @param rideNumber Number to be set
     */

    public void setRideNumber(Integer rideNumber) {
        this.rideNumber = rideNumber;
    }


    /**
     * Get the origin  of the ride
     *
     * @return the origin location
     */

    public String getFromLocation() {
        return fromLocation;
    }


    /**
     * Set the origin of the ride
     *
     * @param origin to be set
     */

    public void setFromLocation(String origin) {
        this.fromLocation = origin;
    }

    /**
     * Get the destination  of the ride
     *
     * @return the destination location
     */

    public String getToLocation() {
        return toLocation;
    }


    /**
     * Set the origin of the ride
     *
     * @param destination to be set
     */
    public void setToLocation(String destination) {
        this.toLocation = destination;
    }


    /**
     * Get the date  of the ride
     *
     * @return the ride date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date of the ride
     *
     * @param date to be set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the free places of the ride
     *
     * @return the available places
     */
    public int getNumPlaces() {
        return numPlaces;
    }

    public void setNumPlaces(int numPlaces) {
        this.numPlaces = numPlaces;
    }

    /**
     * Set the free places of the ride
     *
     * @param numPlaces places to be set
     */

    public void setBetMinimum(int numPlaces) {
        this.numPlaces = numPlaces;
    }

    /**
     * Get the driver associated to the ride
     *
     * @return the associated driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Set the driver associated to the ride
     *
     * @param driver to associate to the ride
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Get the price of the ride
     *
     * @return the price of the ride
     */
    public float getPrice() {
        return price;
    }


    /**
     * Set the price of the ride
     *
     * @param price to be set
     */
    public void setPrice(float price) {
        this.price = price;
    }


    /**
     * This method returns a string with the ride information
     *
     * @return the ride information
     */
    public String toString() {
        return rideNumber + ";" + ";" + fromLocation + ";" + toLocation + ";" + date;
    }


}
