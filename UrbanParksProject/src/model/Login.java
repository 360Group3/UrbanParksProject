package model;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    /**
     * The User Info is an array of strings with the following meanings:
     * <ol>
     *  <li>the Action of the user</li>
     *  <li>the user's email</li>
     *  <li>the user's first name</li>
     *  <li>the user's last name</li>
     *  <li>the user's type</li>
     * </ol>
     * @return the User Info
     */
    public String[] getUserInfo() {
    	return Arrays.copyOf(myUserInfo, myUserInfo.length);
    }
	
    // functional methods
	
    /**
     * @return true if the registration was successful and false otherwise.
     */
    public boolean registerUser() {  
    	boolean registerSuccess = false;
        if (!duplicateUserRegistrationCheck() && validUserRegistrationCheck()) {
        	Schedule.getInstance().addUser(myUserInfo[1], myUserInfo[2], myUserInfo[3],
                    myUserInfo[4]);
        	registerSuccess = true;
        }
        
        return registerSuccess;
    }
    
    /**
     * @return true if the login was successful and false otherwise.
     */
    public boolean loginUser() {
    	boolean loginSuccess = false;
        if (duplicateUserRegistrationCheck()) {
            loginSuccess = true;
        }
        return loginSuccess;
    }
    
    public boolean checkEmailFormat(String theEmail) {
    	String pat = "\\w{3,}@\\w{1,}.\\w*\\.\\w{2,3}";
    	Pattern pattern = Pattern.compile(pat);
    	Matcher matcher = 
				pattern.matcher(theEmail);
    	boolean validEmail = false;
    	if (matcher.find()) {
    		validEmail = true;
    	}
    	return validEmail;
    }
    
    /**
     * Returns true if email is already used and false otherwise.
     * @return true if email is already used and false otherwise.
     */
    private boolean duplicateUserRegistrationCheck() {
        return DataPollster.getInstance().checkEmail(myUserInfo[1]);
    }
    
    /**
     * Returns true if the user info is valid and false otherwise.
     * @return true if the user info is valid and false otherwise.
     */
    private boolean validUserRegistrationCheck() {
        if (myUserInfo == null)
            return false;
        
        boolean isValid = true;
        for (String str : myUserInfo)
        {
            if (str == null)
            {
                isValid = false;
                break;
            }
        }
        
        return isValid;
    }
}
