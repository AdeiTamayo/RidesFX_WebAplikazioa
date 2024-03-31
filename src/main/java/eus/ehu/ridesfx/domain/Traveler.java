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

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for the Traveler class.
     * Calls the superclass constructor with no arguments.
     */
    public Traveler() {
        super();
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
    }

    /**
     * Returns a string representation of the traveler.
     * The string contains the email and name of the traveler, separated by a semicolon.
     *
     * @return A string representation of the traveler.
     */
    public String toString() {
        return super.getEmail() + ";" + super.getName();
    }

}