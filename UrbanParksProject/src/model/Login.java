package model;

import view.AdministratorUI;
import view.ParkManagerUI;
import view.UI;
import view.VolunteerUI;

public class Login {
	
	private String[] myUserInfo;
	
	public Login() {
		myUserInfo = new String[5];
	}
	
	/**
     * Transfer control to the user, specified by their e-mail address.
     */
    public void giveControl(String theEmail) {

        User user = DataPollster.getInstance().getUser(theEmail);

        UI userUI = null;

        if (user instanceof ParkManager) {
            ParkManager manager = DataPollster.getInstance().getParkManager(theEmail);
            userUI = new ParkManagerUI(manager);
        }

        if (user instanceof Administrator) {
            Administrator admin = DataPollster.getInstance().getAdministrator(theEmail);
            userUI = new AdministratorUI(admin);

        }

        if (user instanceof Volunteer) {
            Volunteer volunteer = DataPollster.getInstance().getVolunteer(theEmail);
            userUI = new VolunteerUI(volunteer);
        }

        if (userUI != null)
            userUI.commandLoop();
    }

    public boolean processUserChoice(int theUserChoice) {
    	boolean isInvalidChoice = false;
    	switch (theUserChoice) {
    	case 1:
    		userInfo = loginUser();
    		break;
    	case 2:
    		userInfo = registerUser();
    		break;
    	case 3:
    		closeProgram();
    		break; // Ends program.
    	default:
    		isInvalidChoice = true;
    		break;
    	return isInvalidChoice;
    	}
    }
    
    /**
     * Return a String array that specifies the user as logging in, and the
     * e-mail of the user.
     */
    private void loginUser() {
        String userEmail = getReturnEmail();

        if (DataPollster.getInstance().checkEmail(userEmail)) {
            setUserInfoLogin("login", userEmail);
        } else {
            displayInvalidEmail();
        }
    }
    
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
    
    public boolean duplicateUserRegistrationCheck(String[] theUserInfo) {
        boolean isDuplRgstrn = false;
    	if (checkDuplicate(theUserInfo[1])) {
            isDuplRgstrn = true;
    	}
    	return isDuplRgstrn;
    }
    
    public String[] validUserRegistrationCheck(String[] theUserInfo) {
        String[] userInfoToReturn = theUserInfo;
    	if (theUserInfo == null || theUserInfo[4] == null)
            userInfoToReturn = null;
        return userInfoToReturn;
    }
    
    /**
     * Check if the email is already being used. If so, return true. If not,
     * return false.
     */
    public boolean checkDuplicate(String theEmail) {
        boolean status = false;

        for (User user : DataPollster.getInstance().getAllUserList()) {
            if (user.getEmail().equals(theEmail)) {
                status = true;
            }
        }

        return status;
    }
    
	/**
     * Breaks out of infinite loop in main method.
     */
    public void closeProgram() {
        System.exit(0);
    }
}
