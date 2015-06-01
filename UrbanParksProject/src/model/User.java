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
public abstract class User implements Serializable, Comparable<User> {

	//Class Constant
    private static final long serialVersionUID = 6L;

    //Class Variables
    private String myFirstName;
    private String myLastName;
    private String myEmail;

    /**
     * General Constructor for a User.
     * @param theFirstName the first name of the user.
     * @param theLastName the last name of the user.
     * @param theEmail the email address of the user.
     */
    public User(String theFirstName, String theLastName, String theEmail) {
        myFirstName = theFirstName;
        myLastName = theLastName;
        myEmail = theEmail;
    }
    
    
    /*=========*
     * Getters *
     *=========*/

    /**
     * Return the first name of the User.
     */
    public String getFirstName() {
        return myFirstName;
    }

    /**
     * Return the last name of the User.
     */
    public String getLastName() {
        return myLastName;
    }

    /**
     * Return the email address of the User.
     */
    public String getEmail() {
        return myEmail;
    }
    
    
    
    
    /*===========*
     * Overrides *
     *===========*/
    
    @Override
    /**
     * Compare users by their last name primarily, and first name secondarily.
     */
    public int compareTo(User theOther) {
            //Sort by last name
            int result = myLastName.compareTo(theOther.getLastName());
            
            //If the last names match, then sort by first name
            if (result == 0) {
                result = myLastName.compareTo(theOther.getFirstName());
            }
            
            return result;
    }
    
    @Override
    /**
     * Return true if the two Users share the same email address; false otherwise.
     */
    public boolean equals(Object o){    	
    	boolean isEqual = false;
        
        if (o instanceof User) {
            User other = (User) o;
            isEqual = myEmail.equals(other.getEmail());
        }
        
        return isEqual;
    }

    @Override
    /**
     * Hash User according to their first name, last name, and email address.
     */
    public int hashCode() {
        return Objects.hash(myFirstName, myLastName, myEmail);
    }
}
