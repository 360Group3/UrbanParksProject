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

    // private List<Volunteer> myVolunteerList;
    // private List<ParkManager> myParkManagerList;
    // private List<Administrator> myAdministratorList;

    public UserList() {
        myUserList = new ArrayList<User>(MAX_NUM_USERS);
    }

    /*
     * List Getters.
     * Returns copies of lists so that they cannot be changed without passing them to a
     * setter.
     */

    public List<User> getUserListCopy() {
        return new ArrayList<User>(myUserList);
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
        boolean alreadyExists = false;
        if (theUser != null) {
            for (int i = 0; i < myUserList.size(); i++) {
                final User currUser = myUserList.get(i);
                alreadyExists = currUser.getEmail().equals(theUser.getEmail());
            }
        }

        if (!alreadyExists) {
            myUserList.add(theUser);
            hasBeenAdded = true;
        }
        return hasBeenAdded;
    }

    /**
     * 
     * @author Reid Thompson
     * @return
     */
    public List<User> getVolunteerListCopy() {
        List<User> vols = new ArrayList<>();

        for (int i = 0; i < myUserList.size(); i++) {
            final User currUser = myUserList.get(i);
            if (currUser instanceof Volunteer) {
                vols.add(currUser);
            }
        }

        return vols;
    }

    /**
     * @author Reid Thompson
     * @return
     */
    public List<User> getAdministratorListCopy() {
        List<User> admins = new ArrayList<>();

        for (int i = 0; i < myUserList.size(); i++) {
            final User currUser = myUserList.get(i);
            if (currUser instanceof Administrator) {
                admins.add(currUser);
            }
        }

        return admins;
    }

    /**
     * @author Reid Thompson
     * @return
     */
    public List<User> getParkManagerListCopy() {
        List<User> parkMngrs = new ArrayList<>();

        for (int i = 0; i < myUserList.size(); i++) {
            final User currUser = myUserList.get(i);
            if (currUser instanceof ParkManager) {
                parkMngrs.add(currUser);
            }
        }

        return parkMngrs;
    }

}
