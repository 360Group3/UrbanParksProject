package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a general class for all Users of our Urban Parks program.
 * 
 * It applies to Administrators, ParkManagers, and Volunteers.
 * 
 * @author Reid Thompson - initial implementation
 * @version 5.16.2015
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 6L;

    private String myFirstName;
    private String myLastName;
    private String myEmail;

    public User(String theFirstName, String theLastName, String theEmail) {
        myFirstName = theFirstName;
        myLastName = theLastName;
        myEmail = theEmail;
    }

    public String getFirstName() {
        return myFirstName;
    }

    public String getLastName() {
        return myLastName;
    }

    public String getEmail() {
        return myEmail;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null)
            return false;
        if (o instanceof User) {
            User other = (User) o;
            return myEmail.equals(other.getEmail());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myFirstName, myLastName, myEmail);
    }
}
