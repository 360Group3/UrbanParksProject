package model;

import view.AdministratorUI;
import view.ParkManagerUI;
import view.UI;
import view.VolunteerUI;

/**
 * A class to handle all of the login functionality for one specific user.
 * 
 * @author Reid Thompson - most recent implementation.
 * @version 5.26.2015
 */
public class Login {
	
	private String[] myUserInfo;
	
	public Login() {
		myUserInfo = new String[5];
	}
    
	// setters and getters
	
	public void setUserInfoLogin(String theAction, String theEmail) {
    	myUserInfo[0] = theAction;
    	myUserInfo[1] = theEmail;
    }
    
    public void setUserInfoRegister(String theAction, String theEmail, String theFirstName,
    								String theLastName, String theUserType) {
    	myUserInfo[0] = theAction;
    	myUserInfo[1] = theEmail;
    	myUserInfo[2] = theFirstName;
    	myUserInfo[3] = theLastName;
    	myUserInfo[4] = theUserType;
    }
    
    public String[] getUserInfo() {
    	return myUserInfo;
    }
	
    // functional methods
    
	/**
     * Transfer control to the user, specified by their e-mail address.
     */
    public void giveControlToUser() {

        User user = DataPollster.getInstance().getUser(myUserInfo[1]);

        UI userUI = null;

        if (user instanceof ParkManager) {
            ParkManager manager = DataPollster.getInstance().getParkManager(myUserInfo[1]);
            userUI = new ParkManagerUI(manager);
        }

        if (user instanceof Administrator) {
            Administrator admin = DataPollster.getInstance().getAdministrator(myUserInfo[1]);
            userUI = new AdministratorUI(admin);

        }

        if (user instanceof Volunteer) {
            Volunteer volunteer = DataPollster.getInstance().getVolunteer(myUserInfo[1]);
            userUI = new VolunteerUI(volunteer);
        }

        if (userUI != null)
            userUI.commandLoop();
    }
	
    /**
     * Return a true if the registration was successful and false otherwise.
     */
    public boolean registerUser() {  
    	boolean registerSuccess = false;
        if (!duplicateUserRegistrationCheck() && validUserRegistrationCheck()) {
        	Schedule.getInstance().addUser(myUserInfo[1], myUserInfo[2], myUserInfo[3],
                    myUserInfo[4]);
        	registerSuccess = true;
        	if (myUserInfo[1] != null) {
        		giveControlToUser();
        	}
        }
        
        return registerSuccess;
    }
    
    /**
     * Return true if the login was successful and false otherwise.
     */
    public boolean loginUser() {
    	boolean loginSuccess = false;
        if (!duplicateUserRegistrationCheck()) {
            loginSuccess = true;
            if (myUserInfo[1] != null) {
        		giveControlToUser();
        	}
        }
        return loginSuccess;
    }
    
    /**
     * Returns true if email is already used and false otherwise.
     * @return true if email is already used and false otherwise.
     */
    public boolean duplicateUserRegistrationCheck() {
        return DataPollster.getInstance().checkEmail(myUserInfo[1]);
    }
    
    /**
     * Returns true if the user info is valid and false otherwise.
     * @return true if the user info is valid and false otherwise.
     */
    public boolean validUserRegistrationCheck() {
        return myUserInfo != null && myUserInfo[4] != null;
    }
}
