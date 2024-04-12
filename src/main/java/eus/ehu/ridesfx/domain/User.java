package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "USERS") // Renames the table to avoid using a reserved keyword
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements Serializable {


    private String userName;
    private String password;
    private String fullName;


    @Id
    private String email;

    /**
     * Constructs a new User.
     *
     * @param userName The user's Name.
     * @param password The password.
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Constructs a new User.
     *
     * @param userName The user's Name.
     * @param password The password.
     * @param fullName The full name.
     * @param email    The email.
     */
    public User(String userName, String password, String fullName, String email) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    /**
     * Constructs a new empty User.
     */
    public User() {

    }

    /**
     * Returns the username.
     *
     * @return The username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName The username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password of the user.
     *
     * @return password The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the full name of the user.
     *
     * @return fullName The full name.
     */
    public String getName() {
        return fullName;
    }

    /**
     * Sets the full name of the user.
     *
     * @param fullName The full name.
     */
    public void setName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the email of the user.
     *
     * @return email The email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The email.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Returns a string representation of the user.
     * The string contains the username and the full name of the user, separated by a semicolon.
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                '}';
    }


}
