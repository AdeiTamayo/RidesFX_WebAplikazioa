package eus.ehu.ridesfx.businessLogic;

import eus.ehu.ridesfx.configuration.Config;
import eus.ehu.ridesfx.dataAccess.DataAccess;
import eus.ehu.ridesfx.domain.*;
import eus.ehu.ridesfx.exceptions.RideAlreadyExistException;
import eus.ehu.ridesfx.exceptions.RideMustBeLaterThanTodayException;

import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * Implements the business logic as a web service.
 */
public class BlFacadeImplementation implements BlFacade {

    DataAccess dbManager;
    Config config = Config.getInstance();
    private User currentUser;

    public BlFacadeImplementation() {
        System.out.println("Creating BlFacadeImplementation instance");
        boolean initialize = config.getDataBaseOpenMode().equals("initialize");
        dbManager = new DataAccess(initialize);
        if (initialize)
            dbManager.initializeDB();

    }

    public Ride createRide(Location from, Location to, Date date, int nPlaces, float price, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
        Ride ride = dbManager.createRide(from, to, date, nPlaces, price, driverEmail);
        return ride;
    }

    /**
     * This method invokes the data access to register a new user and return false if the user already exists
     *
     * @param username
     * @param password
     * @param email
     * @param name
     * @param role
     * @return true if the user is registered successfully
     */
    public boolean registerUser(String username, String password, String email, String name, String role) {
        User newUser = null;
        switch (role) {
            case "Driver":
                newUser = new Driver(email, name, username, password);
                break;
            case "Traveler":
                newUser = new Traveler(email, name, username, password);
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Add the user using the dbManager
        return dbManager.addUser(newUser);
    }

    public User checkUser(String email) {
        return dbManager.existsUser(email);
    }

    public boolean checkPassword(String username, String password) {
        if (!dbManager.correctPassword(username, password)) {
            return false;
        }
        return true;
    }

    @Override
    public List<Ride> getRides(Location origin, Location destination, Date date) {
        List<Ride> events = dbManager.getRides(origin, destination, date);
        return events;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Date> getThisMonthDatesWithRides(Location from, Location to, Date date) {
        List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
        return dates;
    }


    /**
     * This method invokes the data access to retrieve the dates a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */

    public Vector<Date> getEventsMonth(Date date) {
        Vector<Date> dates = dbManager.getEventsMonth(date);
        return dates;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }


    public List<Location> getDepartCities() {
        List<Location> departLocations = dbManager.getDepartCities();
        return departLocations;

    }

    public List<Integer> getAvailableSeats(Ride ride) {
        Integer availableSeats = dbManager.getNumSeats(ride);
        //Enter in a list the options of available seats: from 1 to availableSeats
        List<Integer> availableOptions = new Vector<Integer>();
        for (int i = 1; i <= availableSeats; i++) {
            availableOptions.add(i);
        }
        return availableOptions;
    }


    public List<Location> getDestinationCities(Location from) {
        List<Location> targetCities = dbManager.getArrivalCities(from);
        return targetCities;
    }


    public List<Date> getDatesWithRides(Location value, Location value1) {
        List<Date> dates = dbManager.getDatesWithRides(value, value1);
        return dates;
    }


    public boolean makeReservation(Traveler traveler, Ride ride, int numPlaces, Date currentDate) throws NullPointerException{
            return dbManager.makeReservation(traveler, ride, numPlaces, currentDate);
    }

    public List<Reservation> getReservations() {
        return dbManager.getReservations( currentUser.getEmail());
    }

    @Override
    public List<Reservation> getReservationDriver() {
        return dbManager.getReservationsDriver( currentUser.getEmail());
    }


    public void changeReservationState(Reservation selectedItem, String state) {
        dbManager.changeReservationState(selectedItem, state);
    }


    public Traveler getCurrentTraveler() {
        return (Traveler) currentUser;
    }

    /**
     * This method invokes the data access to create a new alert
     *
     * @param from
     * @param to
     * @param numplaces
     * @param date
     * @param email
     * @return the alert created
     */
    public Alert createAlert(Location from, Location to, int numplaces, Date date, String email) {
        Alert alert = dbManager.createAlert(from, to, numplaces, date, email);
        return alert;
    }

    /**
     * This method checks if there are matching rides for the given alert
     *
     * @param alert
     * @return list of matching rides
     */
    public List<Ride> areMatchingRides(Alert alert) {
        List<Ride> Rides = dbManager.getAllRides();
        List<Ride> matchingRides = new Vector<Ride>();
        for (Ride ride : Rides) {
            if (alertMatchesRide(alert, ride)) {
                matchingRides.add(ride);
            }
        }

        return matchingRides;
    }

    /**
     * This method retrieves all the alerts of the current traveler
     *
     * @return list of alerts
     */
    public List<Alert> getAlerts() {
        return dbManager.getAlertsTraveler(currentUser.getEmail());
    }

    /**
     * This method retrieves all the alerts in the database
     *
     * @return list of alerts
     */

    public List<Alert> getAllAlerts() {
        return dbManager.getAllAlerts();
    }

    /**
     * This method checks if an alert with the given parameters exists for the new ride
     *
     * @param alert
     * @param ride
     * @return true if the alert matches the ride
     */
    public boolean alertMatchesRide(Alert alert, Ride ride) {
        return alert.getFromLocation().equals(ride.getFromLocation()) && alert.getToLocation().equals(ride.getToLocation()) && alert.getDate().equals(ride.getDate()) && alert.getNumPlaces() <= ride.getNumPlaces();
    }

    /**
     * This method deletes an alert
     *
     * @param alert
     */
    public void deleteAlert(Alert alert) {
        dbManager.deleteAlert(alert);
        //delete from the list of alerts
        ((Traveler) currentUser).deleteAlert(alert);

    }

    /**
     * This method updates the state of an alert
     *
     * @param alert
     */
    public void updateAlertState(Alert alert) {
        dbManager.updateAlertState(alert);
        alert.setState("Ride found");
    }

    /**
     * This method returns all the cities where rides depart and arrive
     *
     * @return collection of cities
     */
    public List<Location> getLocations() {
        return dbManager.getAllLocations();
    }


    /**
     * This method creates a new location
     *
     * @param name
     */
    public Location createLocation(String name) {
        return dbManager.createLocation(name);
    }


    /**
     * This method deletes the given reservation
     * @param selectedItem
     * @return
     */
    public boolean deleteReservation(Reservation selectedItem) {
        return dbManager.deleteReservation(selectedItem);
    }

    /**
     * This method deletes the location with the given name
     * @param name
     */
    public void deleteLocation(String name) {
        dbManager.deleteLocation(name);
    }

}