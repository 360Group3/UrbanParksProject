package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User List contains a list of all users, including Volunteers, Park Managers, and
 * Administrators. <br>
 * 
 * @author Taylor Gorman - initial implementation
 * @author Reid Thompson - changed to work with new User abstract class
 * @version 8 May 2015
 *
 */
public class UserList implements Serializable {

    private static final long serialVersionUID = 7L;

    private static final int MAX_NUM_USERS = 10000;

    // User Lists
    private List<User> myUserList;

    public UserList() {
        myUserList = new ArrayList<User>(MAX_NUM_USERS);
    }

    // Reid: we shouldn't be allowing other classes to willingly change data in UserList.
    // The following methods should only specific "user" objects and then we should be
    // checking to see if they already
    // exist in the system or if we need to add them to the list of Users (returing a
    // boolean in the process)
    /**
     * List Setter
     * Because we only give out copies of lists, any modified version must be passed
     * through here for changes
     * to be made permanent. Thus, Schedule can still check for any business rule
     * violations.
     * @param theUser the user
     * @return true if the user was added, false otherwise
     */
    public boolean addNewUser(User theUser) {
        boolean hasBeenAdded = false;
        if (theUser != null && !myUserList.contains(theUser)) {
            myUserList.add(theUser);
            hasBeenAdded = true;
        }
        return hasBeenAdded;
    }

    /*
     * List Getters.
     * Returns copies of lists so that they cannot be changed without passing them to a
     * setter.
     */

    public List<User> getUserListCopy() {
        return new ArrayList<User>(myUserList);
    }
    
    /**
     * 
     * @author Reid Thompson
     * @return
     */
    public List<User> getVolunteerListCopy() {
        return getUserTypeListCopy(Volunteer.class);
    }

    /**
     * @author Reid Thompson
     * @return
     */
    public List<User> getAdministratorListCopy() {
        return getUserTypeListCopy(Administrator.class);
    }

    /**
     * @author Reid Thompson
     * @return
     */
    public List<User> getParkManagerListCopy() {
        return getUserTypeListCopy(ParkManager.class); 
    }
    
    /**
     * 
     * @param userType TheUserClass.class for which type of user you want to look for.
     * @return a list of all the members of a user type from the list of users.
     */
    public List<User> getUserTypeListCopy(Class<? extends User> userType) {
        List<User> users = new ArrayList<>();
        
        for (int i = 0; i < myUserList.size(); i++) {
            final User currUser = myUserList.get(i);
            
            if (currUser.getClass().getCanonicalName().equals(userType.getCanonicalName())) {
                users.add(currUser);
            }
        }

        return users;
    }
 }
