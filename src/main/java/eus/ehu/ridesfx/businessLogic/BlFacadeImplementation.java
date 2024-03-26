package eus.ehu.ridesfx.businessLogic;

import eus.ehu.ridesfx.configuration.Config;
import eus.ehu.ridesfx.dataAccess.DataAccess;
import eus.ehu.ridesfx.domain.Driver;
import eus.ehu.ridesfx.domain.Traveler;
import eus.ehu.ridesfx.domain.User;
import eus.ehu.ridesfx.domain.Ride;
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

	public BlFacadeImplementation()  {
		System.out.println("Creating BlFacadeImplementation instance");
		boolean initialize = config.getDataBaseOpenMode().equals("initialize");
		dbManager = new DataAccess(initialize);
		if (initialize)
			dbManager.initializeDB();

	}

	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);
		return ride;
	}

	/**
	 * This method invokes the data access to register a new user and return false if the user already exists
	 * @param username
	 * @param password
	 * @param email
	 * @param name
	 * @param role
	 * @return true if the user is registered successfully
	 */
	public boolean registerUser(String username, String password, String email, String name, String role) {
		if(role.equals("Driver")){
			Driver d= new Driver(email, name, username, password);
			return dbManager.addUser(d);
		} else if (role.equals("Traveler")) {
			Traveler t= new Traveler(email, name, username, password);
			return dbManager.addUser(t);
		}
		return false;
	}

	public User checkUser(String username) {
		return dbManager.existsUser(username);
	}

	public boolean checkPassword(String username, String password) {
		if(!dbManager.correctPassword(username, password)){
			return false;
		}
		return true;
	}

	@Override
	public List<Ride> getRides(String origin, String destination, Date date) {
		List<Ride>  events = dbManager.getRides(origin, destination, date);
		return events;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		return dates;
	}


	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */

	public Vector<Date> getEventsMonth(Date date) {
		Vector<Date>  dates = dbManager.getEventsMonth(date);
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



	public List<String> getDepartCities(){
		List<String> departLocations=dbManager.getDepartCities();
		return departLocations;

	}
	/**
	 * {@inheritDoc}
	 */
	public List<String> getDestinationCities(String from){
		List<String> targetCities=dbManager.getArrivalCities(from);
		return targetCities;
	}

	@Override
	public List<Date> getDatesWithRides(String value, String value1) {
		List<Date> dates = dbManager.getDatesWithRides(value, value1);
		return dates;
	}

}