package eus.ehu.ridesfx.businessLogic;

import eus.ehu.ridesfx.domain.*;
import eus.ehu.ridesfx.exceptions.RideAlreadyExistException;
import eus.ehu.ridesfx.exceptions.RideMustBeLaterThanTodayException;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Interface that specifies the business logic.
 */

public interface BlFacade {
    /**
     * This method retrieves the rides from two locations on a given date
     *
     * @param from the origin location of a ride
     * @param to   the destination location of a ride
     * @param date the date of the ride
     * @return collection of rides
     */
    List<Ride> getRides(String from, String to, Date date);

    /**
     * This method retrieves from the database the dates a month for which there are events
     *
     * @param from the origin location of a ride
     * @param to   the destination location of a ride
     * @param date of the month for which days with rides want to be retrieved
     * @return collection of rides
     */
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);


    /**
     * This method retrieves from the database the dates in a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */
    public Vector<Date> getEventsMonth(Date date);

    /**
     * This method sets the current user
     * @param user
     */
    void setCurrentUser(User user);

    /**
     * This method returns the current user
     * @return the current user
     */
    User getCurrentUser();

    /**
     * This method returns the current traveler
     * @return the current traveler
     */
    Traveler getCurrentTraveler();

    /**
     * This method creates a new ride
     *
     * @param text
     * @param text1
     * @param date
     * @param inputSeats
     * @param price
     * @param email
     * @return the created ride
     */
    Ride createRide(String text, String text1, Date date, int inputSeats, float price, String email) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;


    /**
     * This method returns all the cities where rides depart
     *
     * @return collection of cities
     */

    public List<String> getDepartCities();

    /**
     * This method returns all the arrival destinations, from all rides that depart from a given city
     *
     * @param from the departure location of a ride
     * @return all the arrival destinations
     */

    public List<String> getDestinationCities(String from);

    /**
     * This method returns all the dates with rides from a given location to another
     *
     * @param value  the departure location of a ride
     * @param value1 the arrival location of a ride
     * @return all the dates with rides
     */
    List<Date> getDatesWithRides(String value, String value1);

    /**
     * This method registers a new user
     *
     * @param username
     * @param password
     * @param email
     * @param name
     * @param role
     * @return true if the user is registered, false otherwise
     */
    boolean registerUser(String username, String password, String email, String name, String role);

    /**
     * This method checks if a user exists
     *
     * @param email
     * @return the user if it exists, null otherwise
     */
    User checkUser(String email);

    /**
     * This method checks if the password is correct
     *
     * @param username
     * @param password
     * @return true if the password is correct, false otherwise
     */
    boolean checkPassword(String username, String password);

    /**
     * This method creates a new alert for the traveler
     * @param fromPlace
     * @param toPlace
     * @param inputSeats
     * @param date
     * @param email
     * @return the created alert
     */
    Alert createAlert(String fromPlace, String toPlace, int inputSeats, Date date, String email);


    /**
     * This method books a ride for a traveler
     * @param ride
     * @param traveler
     * @param numPlaces
     * @return true if the ride is booked, false otherwise
     */


    boolean makeReservation(Traveler traveler, Ride ride,int numPlaces);

    /**
     * This method returns the reservations of the current traveler
     */

    List<Reservation> getReservations();

    /**
     * This method returns the reservations of a driver
     */
    List<Reservation> getReservationDriver();

    /**
     * This method deletes a reservation
     * @param selectedItem
     */

    boolean deleteReservation(Reservation selectedItem);

    /**
     * This method changes the state of a reservation
     * @param selectedItem
     * @param state
     */

    void changeReservationState(Reservation selectedItem, String state);

    /**
     * This method returns the available seats for a ride
     *
     * @param selectedItem
     * @return collection of available seats
     */
    List<Integer> getAvailableSeats(Ride selectedItem);

    /**
     * This method checks if there are matching rides for the given alert
     *
     * @param alert
     * @return list of matching rides
     */
    List<Ride> areMatchingRides(Alert alert);

    /**
     * This method returns all the alerts of the current traveler
     *
     * @return list of alerts
     */
    List<Alert> getAlerts();

    /**
     * This method returns all the alerts in the database
     *
     * @return list of alerts
     */
    List<Alert> getAllAlerts();



    /**
     * This method deletes an alert
     *
     * @param alert
     */
    void deleteAlert(Alert alert);

    /**
     * This method updates the state of an alert
     *
     * @param alert
     */
    void updateAlertState(Alert alert);

    /**
     * This method returns all the locations
     *
     * @return list of locations
     */

    List<Location> getLocations();

    /**
     * This method converts the rides already created to a location
     */
    void convertRideToLocation();

    /**
     * This method creates a new location
     *
     * @param name
     */
    void createLocation(String name);


}