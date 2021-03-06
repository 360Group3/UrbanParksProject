package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class for the Administrator user for the system.<br>
 * Administrators can search for Volunteers by last name.
 * 
 * @author Reid Thompson
 * @author Taylor Gorman
 * @version 31 May 2015
 */
public class Administrator extends User implements Serializable {

	//Class Constant
    private static final long serialVersionUID = 0L;

    
    /**
     * Constructs an Administrator with a first name, last name, and email.
     * @param theFirstName the first name of the Administrator.
     * @param theLastName the last name of the Administrator.
     * @param theEmail the email of the Administrator.
     */
    public Administrator(String theEmail, String theFirstName, String theLastName) {
        super(theFirstName, theLastName, theEmail);
    }
    
    
    
    /*=================*
     * Sort Volunteers *
     *=================*/

    /**
     * Return a List of all Volunteers matching the given last name. 
     * @param theLastName the last name by which to search for Volunteer matches.
     * @return a List of Volunteers with a matching last name.
     */
    public List<User> getMatchingVolunteers(String theLastName) {
    	List<User> allVolunteers = DataPollster.getInstance().getVolunteerListCopy();
        List<User> matchingVolunteers = new ArrayList<>();

        for (int i = 0; i < allVolunteers.size(); i++) {
            User currentVolunteer = allVolunteers.get(i);
            if (currentVolunteer.getLastName().equals(theLastName)) {
                matchingVolunteers.add(currentVolunteer);
            }
        }

        //Sort all Volunteers with that last name by their first names.
        if (!matchingVolunteers.isEmpty()) {
        	Collections.sort(matchingVolunteers);
        }        
        
        return matchingVolunteers;        
    }
    
    
    
    
    /*===================*
     * Search Volunteers *
     *===================*/

    /**
     * Get a list of all volunteers, sorted by last name (ascending), then first name (ascending). 
     * @return a sorted list of all Volunteers
     */
    public List<User> getSortedVolunteers() {    	
    	//Get the List of all Volunteers, then sort it.
        List<User> volunteerList = DataPollster.getInstance().getVolunteerListCopy();
        Collections.sort(volunteerList);
        return volunteerList;
    }
}
