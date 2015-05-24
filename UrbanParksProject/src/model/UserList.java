package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User List contains a list of all users, including Volunteers, Park Managers, and Administrators. <br>
 * @author Taylor Gorman - initial implementation
 * @author Reid Thompson - changed to work with new User abstract class
 * @version 8 May 2015
 *
 */
public class UserList implements Serializable {

	private static final int MAX_NUM_USERS = 10000;
	
	//User Lists
	private List<User> myUserList;
	
//	private List<Volunteer> myVolunteerList;
//	private List<ParkManager> myParkManagerList;
//	private List<Administrator> myAdministratorList;
	
		
	public UserList() {
		myUserList = new ArrayList<User>(MAX_NUM_USERS);
	}
	
	
	/*
	 * List Getters.
	 * Returns copies of lists so that they cannot be changed without passing them to a setter.	
	 */
		
	public List<User> getUserListCopy() {
		return new ArrayList<User>(myUserList);
	}
	
	/*
	 * List Setters
     * Because we only give out copies of lists, any modified version must be passed through here for changes
	 * to be made permanent. Thus, Schedule can still check for any business rule violations.
	 */	
	
	// Reid: we shouldn't be allowing other classes to willingly change data in UserList.
	// The following methods should only specific "user" objects and then we should be checking to see if they already
	// exist in the system or if we need to add them to the list of Users (returing a boolean in the process)
	
	/**
	 * 
	 * @author Reid Thompson
	 * @param theVolunteer
	 * @return
	 */
	public boolean addNewVolunteer(Volunteer theVolunteer) {
		boolean hasBeenAdded = false;
		boolean alreadyExists = false;
		if (theVolunteer != null) {
			for (int i = 0; i < myUserList.size(); i++) {
				final User currUser = myUserList.get(i);
				if (currUser instanceof Volunteer) {
					alreadyExists = currUser.getEmail().equals(theVolunteer.getEmail());
				}
			}
		}
		
		if (!alreadyExists) {
			myUserList.add(theVolunteer);
			hasBeenAdded = true;
		}
		
		return hasBeenAdded;
	}
	
	/**
	 * 
	 * @author Reid Thompson
	 * @param theAdministrator
	 * @return
	 */
	public boolean addNewAdministrator(Administrator theAdministrator) {
		boolean hasBeenAdded = false;
		boolean alreadyExists = false;
		if (theAdministrator != null) {
			for (int i = 0; i < myUserList.size(); i++) {
				final User currUser = myUserList.get(i);
				if (currUser instanceof Administrator) {
					alreadyExists = currUser.getEmail().equals(theAdministrator.getEmail());
				}
			}
		}
		
		if (!alreadyExists) {
			myUserList.add(theAdministrator);
			hasBeenAdded = true;
		}
		return hasBeenAdded;
	}

	/**
	 * 
	 * @author Reid Thompson
	 * @param theParkManager
	 * @return
	 */
	public boolean addNewParkManager(ParkManager theParkManager) {
		boolean hasBeenAdded = false;
		boolean alreadyExists = false;
		if (theParkManager != null) {
			for (int i = 0; i < myUserList.size(); i++) {
				final User currUser = myUserList.get(i);
				if (currUser instanceof ParkManager) {
					alreadyExists = currUser.getEmail().equals(theParkManager.getEmail());
				}
			}
		}
		
		if (!alreadyExists) {
			myUserList.add(theParkManager);
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
