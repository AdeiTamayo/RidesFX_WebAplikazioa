package eus.ehu.ridesfx;

import eus.ehu.ridesfx.businessLogic.BlFacadeImplementation;
import eus.ehu.ridesfx.configuration.UtilDate;
import eus.ehu.ridesfx.domain.Ride;
import eus.ehu.ridesfx.exceptions.RideAlreadyExistException;
import eus.ehu.ridesfx.exceptions.RideMustBeLaterThanTodayException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class BLFacadeTest extends BlFacadeImplementation {

    private BlFacadeImplementation blFacadeImplementation;

    @BeforeAll
    static void setUpAll() {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE); //could be Level.OFF
    }

    @BeforeEach
    void setUp() {
        blFacadeImplementation = new BlFacadeImplementation();
    }

    private Date getTestDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2024, Calendar.JULY, 7);
        return calendar.getTime();
    }


    @Test

    void testNoAvailableRides(){
        String departure = "Donostia";
        String arrival = "Bilbo";
        Date testDate = getTestDate();
        int seats = 10;

        List<Ride> rides = blFacadeImplementation.getRides(departure, arrival, testDate);
assertTrue(rides.isEmpty(), "No rides should be available for the given parameters.");


    }

    @Test
    void testAvailableRides() {
        String departure = "Donostia";
        String arrival = "Bilbo";
        Date testDate = UtilDate.newDate(2024, 5, 15);
        int seats = 2;

        List<Ride> rides = blFacadeImplementation.getRides(departure, arrival, testDate);
        assertFalse(rides.isEmpty(), "Rides should be available for the given parameters.");
    }

    @Test
    void testCreateRide() throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
        String departure = "Donostia";
        String arrival = "Bilbo";
        Date testDate = UtilDate.newDate(2024, 8, 15);
        int seats = 2;
        float price = 10;
        String email = "driver@";
        Ride ride = blFacadeImplementation.createRide(departure, arrival, testDate, seats, price, email);
        assertNotNull(ride, "Ride should be created.");

    }

    @Test
    void testRideAlredyExist() {
        String departure = "Donostia";
        String arrival = "Bilbo";
        Date testDate = UtilDate.newDate(2024, 5, 17);
        int seats = 2;
        float price = 10;
        String email = "driver@";

        // Create the ride for the first time
        try {
            Ride ride = blFacadeImplementation.createRide(departure, arrival, testDate, seats, price, email);
            assertNotNull(ride, "Ride should be created.");
        } catch (RideAlreadyExistException | RideMustBeLaterThanTodayException e) {
            fail("Exception should not be thrown when creating a new ride.");
        }

        // Try to create the same ride again
        assertThrows(RideAlreadyExistException.class, () -> {
            blFacadeImplementation.createRide(departure, arrival, testDate, seats, price, email);
        }, "RideAlreadyExistException should be thrown when trying to create a ride that already exists.");
    }

    @Test
    void testCreateRideMustBeLaterThanToday() {
        String departure = "Donostia";
        String arrival = "Bilbo";
        Date testDate = UtilDate.newDate(2022, 5, 15);
        int seats = 2;
        float price = 10;
        String email = "driver@";
        assertThrows(RideMustBeLaterThanTodayException.class, () -> {
            blFacadeImplementation.createRide(departure, arrival, testDate, seats, price, email);
        });
    }


}