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

	//Class Constants
    private static final long serialVersionUID = 7L;
    private static final int MAX_NUM_USERS = 10000;

    // User Lists
    private List<User> myUserList;
    public UserList() {
        myUserList = new ArrayList<User>(MAX_NUM_USERS);
    }
    
    
    /*==============*
     * Add New User *
     *==============*/

    /**
     * Add a new User to the User List.
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
    
    

    /*==============*
     * List Getters *
     *==============*/

    public List<User> getUserListCopy() {
        return new ArrayList<User>(myUserList);
    }
    
    /**
     * Return a copy of the Volunteer List.
     * @author Reid Thompson
     * @return
     */
    public List<User> getVolunteerListCopy() {
        return getUserTypeListCopy(Volunteer.class);
    }

    /**
     * Return a copy of the Administrator List.
     * @author Reid Thompson
     * @return
     */
    public List<User> getAdministratorListCopy() {
        return getUserTypeListCopy(Administrator.class);
    }

    /**
     * Return a copy of the Park Manager List.
     * @author Reid Thompson
     * @return
     */
    public List<User> getParkManagerListCopy() {
        return getUserTypeListCopy(ParkManager.class); 
    }
    
    
    
    /*==============*
     * Helper Class *
     *==============*/
    
    /**
     * Return a list of all Users that match the specified type.
     * @author Reid Thompson
     */
    private List<User> getUserTypeListCopy(Class<? extends User> userType) {
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
