package eus.ehu.ridesfx.dataAccess;

import eus.ehu.ridesfx.configuration.Config;
import eus.ehu.ridesfx.configuration.UtilDate;
import eus.ehu.ridesfx.domain.*;
import eus.ehu.ridesfx.exceptions.RideAlreadyExistException;
import eus.ehu.ridesfx.exceptions.RideMustBeLaterThanTodayException;
import jakarta.persistence.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.*;


/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {

    protected EntityManager db;
    protected EntityManagerFactory emf;

    public DataAccess() {

        this.open();

    }

    public DataAccess(boolean initializeMode) {

        this.open(initializeMode);

    }

    public void open() {
        open(false);
    }


    public void open(boolean initializeMode) {

        Config config = Config.getInstance();

        System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
                config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

        String fileName = config.getDatabaseName();
        if (initializeMode) {
            fileName = fileName + ";drop";
            System.out.println("Deleting the DataBase");
        }

        if (config.isDataAccessLocal()) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                emf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                // so destroy it manually.
                StandardServiceRegistryBuilder.destroy(registry);
            }

            db = emf.createEntityManager();
            System.out.println("DataBase opened");
        }
    }


    public void reset() {
        db.getTransaction().begin();
        //db.createNativeQuery("DELETE FROM USER_RIDE").executeUpdate();
        db.createQuery("DELETE FROM Ride ").executeUpdate();
        db.createQuery("DELETE FROM User ").executeUpdate();
        db.createQuery("DELETE FROM Alert ").executeUpdate();
        db.getTransaction().commit();
    }

    public void initializeDB() {

        this.reset();

        db.getTransaction().begin();

        try {

            Calendar today = Calendar.getInstance();

            int month = today.get(Calendar.MONTH);
            int year = today.get(Calendar.YEAR);
            if (month == 12) {
                month = 1;
                year += 1;
            }


            //Create drivers
            Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez", "aitor", "1234");
            Driver driver2 = new Driver("driver2@gmail.com", "Ane Gaztañaga", "ane", "1234");
            Driver driver3 = new Driver("driver3@gmail.com", "Test driver", "test", "1234");
            Traveler traveler1 = new Traveler("traveler@gmail.com", "Traveler 1", "traveler1", "1234");

            Driver testDriver = new Driver("driver@", "Test driver", "test", "1");
            Traveler testTraveler = new Traveler("traveler@", "Test traveler", "test", "1");


            //Create rides
            driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7);
            driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month + 1, 15), 4, 7);
            driver1.addRide("Donostia", "Gasteiz", UtilDate.newDate(year, month, 6), 4, 8);
            driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4);
            driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year, month, 7), 4, 8);
            driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, 3);
            driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 2, 5);
            driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 2, 5);
            driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 1, 3);

            testDriver.addRide("Donostia", "Bilbo", UtilDate.newDate(2024, 5, 15), 4, 7);
            testDriver.addRide("Donostia", "Bilbo", UtilDate.newDate(2024, 6, 15), 4, 7);
            testDriver.addRide("Donostia", "Bilbo", UtilDate.newDate(2024, 5, 6), 4, 8);


            db.persist(driver1);
            db.persist(driver2);
            db.persist(driver3);
            db.persist(traveler1);
            db.persist(testDriver);
            db.persist(testTraveler);


            db.getTransaction().commit();
            System.out.println("Db initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This method retrieves from the database the dates in a month for which there are events
     *
     * @param date of the month for which days with events want to be retrieved
     * @return collection of dates
     */
    public Vector<Date> getEventsMonth(Date date) {
        System.out.println(">> DataAccess: getEventsMonth");
        Vector<Date> res = new Vector<Date>();

        Date firstDayMonthDate = UtilDate.firstDayMonth(date);
        Date lastDayMonthDate = UtilDate.lastDayMonth(date);


        TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ride.date FROM Ride ride "
                + "WHERE ride.date BETWEEN ?1 and ?2", Date.class);
        query.setParameter(1, firstDayMonthDate);
        query.setParameter(2, lastDayMonthDate);
        List<Date> dates = query.getResultList();
        for (Date d : dates) {
            System.out.println(d.toString());
            res.add(d);
        }
        return res;
    }


    public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
        System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail + " date " + date);
        try {
            if (new Date().compareTo(date) > 0) {
                throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
            }
            db.getTransaction().begin();

            Driver driver = db.find(Driver.class, driverEmail);
            if (driver.doesRideExists(from, to, date)) {
                db.getTransaction().commit();
                throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
            }
            Ride ride = driver.addRide(from, to, date, nPlaces, price);
            //next instruction can be obviated
            db.persist(driver);
            db.getTransaction().commit();

            return ride;
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            db.getTransaction().commit();
            return null;
        }


    }

    public List<Ride> getRides(String origin, String destination, Date date) {
        System.out.println(">> DataAccess: getRides origin/dest/date");
        Vector<Ride> res = new Vector<>();

        TypedQuery<Ride> query = db.createQuery("SELECT ride FROM Ride ride "
                + "WHERE ride.date=?1 ", Ride.class);
        query.setParameter(1, date);

        //If the number of seats of a ride is 0 delete it from the list
        List<Ride> rides = query.getResultList();
        List<Ride> finalRides = new ArrayList<>();
        for (Ride r : rides) {
            if (r.getNumPlaces() > 0) {
                finalRides.add(r);
            }
        }
        return finalRides;
    }


    /**
     * This method returns all the cities where rides depart
     *
     * @return collection of cities
     */
    public List<String> getDepartCities() {
        TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.fromLocation FROM Ride r ORDER BY r.fromLocation", String.class);
        List<String> cities = query.getResultList();
        return cities;

    }

    /**
     * This method returns all the arrival destinations, from all rides that depart from a given city
     *
     * @param from the departure location of a ride
     * @return all the arrival destinations
     */
    public List<String> getArrivalCities(String from) {
        TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.toLocation FROM Ride r WHERE r.fromLocation=?1 ORDER BY r.toLocation", String.class);
        query.setParameter(1, from);
        List<String> arrivingCities = query.getResultList();
        return arrivingCities;

    }

    /**
     * This method retrieves from the database the number of seats available in a ride
     *
     * @param ride
     * @return number of seats available
     */
    public Integer getNumSeats(Ride ride) {
        TypedQuery<Integer> query = db.createQuery("SELECT r.numPlaces FROM Ride r WHERE r.id=?1", Integer.class);
        query.setParameter(1, ride.getRideNumber());
        return query.getSingleResult();
    }

    /**
     * This method retrieves from the database the dates a month for which there are events
     *
     * @param from the origin location of a ride
     * @param to   the destination location of a ride
     * @param date of the month for which days with rides want to be retrieved
     * @return collection of rides
     */
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
        System.out.println(">> DataAccess: getEventsMonth");
        List<Date> res = new ArrayList<>();

        Date firstDayMonthDate = UtilDate.firstDayMonth(date);
        Date lastDayMonthDate = UtilDate.lastDayMonth(date);


        TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.fromLocation=?1 AND r.toLocation=?2 AND r.date BETWEEN ?3 and ?4", Date.class);

        query.setParameter(1, from);
        query.setParameter(2, to);
        query.setParameter(3, firstDayMonthDate);
        query.setParameter(4, lastDayMonthDate);
        List<Date> dates = query.getResultList();
        for (Date d : dates) {
            res.add(d);
        }
        return res;
    }

    public List<Date> getDatesWithRides(String from, String to) {
        System.out.println(">> DataAccess: getEventsFromTo");
        List<Date> res = new ArrayList<>();

        TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.fromLocation=?1 AND r.toLocation=?2", Date.class);

        query.setParameter(1, from);
        query.setParameter(2, to);
        List<Date> dates = query.getResultList();
        for (Date d : dates) {
            res.add(d);
        }
        return res;
    }

    private void generateTestingData() {
        // create domain entities and persist them
    }


    /**
     * This method adds a driver to the database
     *
     * @param user
     */
    public boolean addUser(User user) {
        TypedQuery<User> q2 = db.createQuery(
                "SELECT p FROM User p WHERE p.email = ?1", User.class);
        q2.setParameter(1, user.getEmail());
        try {
            q2.getSingleResult();
            return false;
        } catch (NoResultException e) {
            db.getTransaction().begin();
            db.persist(user);
            db.getTransaction().commit();
            return true; // Return true when no result is found and the driver can be added
        }

    }


    /**
     * This method checks if the driver exists in the database
     *
     * @param email
     * @return
     */
    public User existsUser(String email) {
        TypedQuery<User> q2 = db.createQuery(
                "SELECT p FROM User p WHERE p.email = ?1", User.class);
        q2.setParameter(1, email);

        try {
            return q2.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null when no result is found
        }
    }

    /**
     * Added method. This method checks if the password is correct
     *
     * @param email
     * @param password
     */
    public boolean correctPassword(String email, String password) {
        User user = existsUser(email);
        return user.getPassword().equals(password);

        /**TypedQuery<Driver> q2 = db.createQuery(
         "SELECT p FROM Driver p WHERE p.password = ?1", Driver.class);
         q2.setParameter(1, password);

         try {
         return q2.getSingleResult();
         } catch (NoResultException e) {
         return null; // Return null when no result is found
         }**/
    }


    public boolean makeReservation(Traveler traveler, Ride ride, int numSeats) {
        // Start a transaction
        db.getTransaction().begin();

        // Retrieve the Ride object from the database
        TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.id = :rideId", Ride.class);
        query.setParameter("rideId", ride.getRideNumber());

        Ride dbRide = query.getSingleResult();

        // Create a new Reservation object
        Reservation reservation = new Reservation(traveler, dbRide, numSeats, "pending");

        // Add the reservation to the traveler
        traveler.addReservation(reservation);

        // Persist the Reservation object to the database
        db.persist(reservation);

        // Commit the transaction
        db.getTransaction().commit();
        return true;
    }

    public List<Reservation> getReservations(String mail) {
        System.out.println(">> DataAccess: getReservations");
        TypedQuery<Reservation> query = db.createQuery("SELECT r FROM Reservation r WHERE r.traveler.email = :mail", Reservation.class);
        query.setParameter("mail", mail);
        return query.getResultList();
    }

    public void deleteReservation(Reservation r) {
        db.getTransaction().begin();
        // Remove the reservation from the user
        Traveler traveler = r.getTraveler();
        traveler.removeReservation(r);
        db.persist(traveler);
        // Now you can safely remove the reservation
        db.remove(r);
        db.getTransaction().commit();
    }


    public void close() {
        db.close();
        System.out.println("DataBase is closed");
    }

    /**
     * This method creates an alert for a traveler
     *
     * @param from
     * @param to
     * @param date
     * @param nPlaces
     * @param travelerEmail
     * @return the created alert
     */
    public Alert createAlert(String from, String to, int nPlaces, Date date, String travelerEmail) {
        System.out.println(">> DataAccess: createAlert=> from= " + from + " to= " + to + " traveler=" + travelerEmail + " date " + date);
        try {
            db.getTransaction().begin();
            Traveler traveler = db.find(Traveler.class, travelerEmail);
            if (traveler.doesAlertExists(from, to, date)) {
                db.getTransaction().commit();
                // If the alert already exists, return null
                return null;
            } else {
                Alert alert = traveler.addAlert(from, to, date, nPlaces);
                db.persist(alert);
                db.getTransaction().commit();

                return alert;
            }

        } catch (NullPointerException e) {
            // If a NullPointerException occurs, rollback the transaction and log the exception
            if (db.getTransaction().isActive()) {
                db.getTransaction().rollback();
            }
            e.printStackTrace(); // Log the exception for debugging purposes
            return null;
        }
    }

    /**
     * This method retrieves all the alerts for a traveler
     *
     * @param travelerEmail
     * @return a list of alerts
     */
    public List<Alert> getAllAlerts(String travelerEmail) {
        System.out.println(">> DataAccess: getAlerts");
        TypedQuery<Alert> query = db.createQuery("SELECT a FROM Alert a WHERE a.traveler.email = :travelerEmail", Alert.class);
        query.setParameter("travelerEmail", travelerEmail);
        return query.getResultList();
    }

    /**
     * This method retrieves all the rides from the database
     *
     * @return a list of rides
     */
    public List<Ride> getAllRides() {
        TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r", Ride.class);
        return query.getResultList();
    }

    /**
     * This method deletes an alert from the database
     *
     * @param alert
     */
    public void deleteAlert(Alert alert) {
        db.getTransaction().begin();
        // Remove alert from Alert and USERS_ALERT tables
        String deleteQueryUsersAlert = "DELETE FROM USERS_ALERT WHERE USERS_ALERT.ALERTS_ALERTNUMBER = :alertnum";
        String deleteQueryAlert = "DELETE FROM Alert WHERE alertNumber = :alertnum";

        Query q1 = db.createNativeQuery(deleteQueryUsersAlert);
        q1.setParameter("alertnum", alert.getAlertNumber());
        int rowsAffected1 = q1.executeUpdate();

        Query q2 = db.createNativeQuery(deleteQueryAlert);
        q2.setParameter("alertnum", alert.getAlertNumber());
        int rowsAffected2 = q2.executeUpdate();

        db.getTransaction().commit();

        // Check rowsAffected1 and rowsAffected2 if necessary
    }


    /**
     * This method updates the state of an alert to ride found
     *
     * @param a
     */
    public void updateAlertState(Alert a) {
        db.getTransaction().begin();
        a.setState("Ride found");
        db.getTransaction().commit();
    }

    //TODO method that given an alert and the ride that matches it, returns the reservation

    /**
     * This method converts the rides already created to a location
     */
    public void convertRideToLocation() {
        // Start a transaction
        db.getTransaction().begin();

        // Fetch all distinct departure and arrival cities from the Ride table
        TypedQuery<String> departCitiesQuery = db.createQuery("SELECT DISTINCT r.fromLocation FROM Ride r", String.class);
        TypedQuery<String> arrivalCitiesQuery = db.createQuery("SELECT DISTINCT r.toLocation FROM Ride r", String.class);

        List<String> departCities = departCitiesQuery.getResultList();
        List<String> arrivalCities = arrivalCitiesQuery.getResultList();

        // Combine both lists and remove duplicates
        Set<String> allCities = new HashSet<>(departCities);
        allCities.addAll(arrivalCities);

        // Convert each city into a Location object and persist it in the database
        for (String city : allCities) {
            Location location = new Location(city);
            db.persist(location);
        }

        // Commit the transaction
        db.getTransaction().commit();
    }

    /**
     * This method returns all the locations
     *
     * @return list of locations
     */
    public List<Location> getAllLocations() {
        // Execute a query to fetch all Location objects
        TypedQuery<Location> query = db.createQuery("SELECT l FROM Location l", Location.class);

        // Return the result list
        return query.getResultList();
    }

    /**
     * This method creates a new location
     *
     * @param name
     */
    public void createLocation(String name) {
        db.getTransaction().begin();
        Location location = new Location(name);
        db.persist(location);
        db.getTransaction().commit();
    }


    /**
     * This method returns the Reservation for the rides offered by a driver
     *
     * @param email
     * @return the list of reservations assigned to a driver
     */
    public List<Reservation> getReservationsDriver(String email) {
        System.out.println(">> DataAccess: getReservationsDriver");
        TypedQuery<Reservation> query = db.createQuery("SELECT r FROM Reservation r WHERE r.ride.driver.email = :email", Reservation.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    /**
     * This method changes the state of a reservation
     *
     * @param selectedItem
     * @param state
     */
    public void changeReservationState(Reservation selectedItem, String state) {
        db.getTransaction().begin();
        Reservation managedReservation = db.merge(selectedItem);
        managedReservation.setState(state);
        db.getTransaction().commit();
    }
}