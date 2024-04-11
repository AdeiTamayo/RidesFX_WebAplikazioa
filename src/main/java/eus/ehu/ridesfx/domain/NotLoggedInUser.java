package eus.ehu.ridesfx.domain;

/**
 * This class represents a user that is not logged in the system.
 */
public class NotLoggedInUser extends User {

    private String userName;
    private String password;

    /**
     * Constructs a new NotLoggedInUser.
     *
     * @param userName The user's Name.
     * @param password The password.
     */
    public NotLoggedInUser(String userName, String password) {
        super(userName, password);
    }

    /**
     * Constructs a new empty NotLoggedInUser.
     */
    public NotLoggedInUser() {
        super();
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
     * Sets the username.
     *
     * @param userName The username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password.
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the string representation of the NotLoggedInUser.
     *
     * @return The string representation of the NotLoggedInUser.
     */

    @Override
    public String toString() {
        return "NotLoggedInUser{" +
                "userName='" + userName + '\'' +
                '}';
    }

}
