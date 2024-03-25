package eus.ehu.ridesfx.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@Entity
public class Driver implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String email;
    private String name;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Ride> rides = new Vector<Ride>();

    public Driver() {
        super();
    }

    public Driver(String email, String name, String username, String password) {
        this.email = email;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * This method returns the Password of the driver
     *
     * @return the password of the driver
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method returns the email of the driver
     *
     * @return the email of the driver
     */

    public String getEmail() {
        return email;
    }


    /**
     * This method sets the email of the driver
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method returns the name of the driver
     *
     * @return the name of the driver
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the name of the driver
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns  the username of the driver
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the username of the driver
     *
     * @return the username of the driver
     */
    public String toString() {
        return email + ";" + name + rides;
    }

    /**
     * This method creates a new ride for the driver
     *
     * @param
     * @param
     * @return
     */
    public Ride addRide(String from, String to, Date date, int nPlaces, float price) {
        Ride ride = new Ride(from, to, date, nPlaces, price, this);
        rides.add(ride);
        return ride;
    }

    /**
     * This method checks if the ride already exists for that driver
     *
     * @param from the origin location
     * @param to   the destination location
     * @param date the date of the ride
     * @return true if the ride exists and false in other case
     */
    public boolean doesRideExists(String from, String to, Date date) {
        for (Ride r : rides)
            if ((java.util.Objects.equals(r.getFromLocation(), from)) && (java.util.Objects.equals(r.getToLocation(), to)) && (java.util.Objects.equals(r.getDate(), date)))
                return true;

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Driver other = (Driver) obj;
        if (email != other.email)
            return false;
        return true;
    }

    public Ride removeRide(String from, String to, Date date) {
        boolean found = false;
        int index = 0;
        Ride r = null;
        while (!found && index <= rides.size()) {
            r = rides.get(++index);
            if ((java.util.Objects.equals(r.getFromLocation(), from)) && (java.util.Objects.equals(r.getToLocation(), to)) && (java.util.Objects.equals(r.getDate(), date)))
                found = true;
        }

        if (found) {
            rides.remove(index);
            return r;
        } else return null;
    }

}