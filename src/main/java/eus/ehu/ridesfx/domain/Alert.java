/**
 * The Alert class represents an alert in the ridesharing system.
 * It is marked as an Entity, meaning that it is mapped to a table in the database.
 * Each Alert is associated with a Traveler.
 *
 * The class implements Serializable, which means it can be converted to a byte stream and recovered later.
 *
 */
package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class Alert implements Serializable {
    @Id
    @GeneratedValue
    private Integer alertNumber;
    private String fromLocation;
    private String toLocation;
    private int numPlaces;
    private Date date;

    @ManyToOne
    private Traveler traveler;

    @OneToOne
    private Location location;

    /**
     * Default constructor for the Alert class.
     * Initializes a new Alert with no parameters.
     */
    public Alert(){
        super();
    }

    /**
     * Parameterized constructor for the Alert class.
     * Initializes a new Alert with the provided parameters.
     *
     * @param alertNumber The number of the alert.
     * @param from The origin location of the alert.
     * @param to The destination location of the alert.
     * @param date The date of the alert.
     * @param numPlaces The number of places available in the alert.
     * @param traveler The traveler of the alert.
     */
    public Alert(Integer alertNumber, String from, String to, Date date, int numPlaces, Traveler traveler) {
        super();
        this.alertNumber = alertNumber;
        this.fromLocation = from;
        this.toLocation = to;
        this.numPlaces = numPlaces;
        this.date=date;
        this.traveler = traveler;
    }

    /**
     * Parameterized constructor for the Alert class.
     * Initializes a new Alert with the provided parameters.
     *
     * @param from The origin location of the alert.
     * @param to The destination location of the alert.
     * @param date The date of the alert.
     * @param numPlaces The number of places available in the alert.
     * @param traveler The traveler of the alert.
     */
    public Alert(String from, String to, Date date, int numPlaces, Traveler traveler) {
        super();
        this.fromLocation = from;
        this.toLocation = to;
        this.numPlaces = numPlaces;
        this.date=date;
        this.traveler = traveler;
    }


    /**
     * Get the  number of the alert
     *
     * @return the alert number
     */
    public Integer getAlertNumber() {
        return alertNumber;
    }


    /**
     * Set the alert number to a alert
     *
     * @param alertNumber Number to be set	 */

    public void setAlertNumber(Integer alertNumber) {
        this.alertNumber = alertNumber;
    }


    /**
     * Get the origin  of the alert
     *
     * @return the origin location
     */
    public String getFromLocation() {
        return fromLocation;
    }


    /**
     * Set the origin of the alert
     *
     * @param origin to be set
     */
    public void setFromLocation(String origin) {
        this.fromLocation = origin;
    }

    /**
     * Get the destination  of the alert
     *
     * @return the destination location
     */
    public String getToLocation() {
        return toLocation;
    }


    /**
     * Set the origin of the alert
     *
     * @param destination to be set
     */
    public void setToLocation(String destination) {
        this.toLocation = destination;
    }

    /**
     * Get the free places of the alert
     *
     * @return the available places
     */

    /**
     * Get the date  of the alert
     *
     * @return the alert date
     */
    public Date getDate() {
        return date;
    }
    /**
     * Set the date of the alert
     *
     * @param date to be set
     */
    public void setDate(Date date) {
        this.date = date;
    }


    public float getNumPlaces() {
        return numPlaces;
    }

    public void setNumPlaces( int numPlaces) {
        this.numPlaces = numPlaces;
    }

    /**
     * Set the free places of the alert
     *
     * @param  numPlaces places to be set
     */

    public void setBetMinimum(int numPlaces) {
        this.numPlaces = numPlaces;
    }

    /**
     * Get the traveler associated to the alert
     *
     * @return the associated traveler
     */
    public Traveler getTraveler() {
        return traveler;
    }

    /**
     * Set the traveler associated to the alert
     *
     * @param traveler to associate to the alert
     */
    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }



    /**
     * This method returns a string with the alert information
     *
     * @return the alert information
     */
    public String toString(){
        return alertNumber+";"+";"+ fromLocation +";"+ toLocation +";"+date;
    }





}
