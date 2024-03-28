package eus.ehu.ridesfx.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    public Traveler() {
        super();
    }

    public Traveler(String email, String name, String username, String password) {
        super(username, password, name, email);
    }

    public String toString() {
        return super.getEmail() + ";" + super.getName();
    }

}
