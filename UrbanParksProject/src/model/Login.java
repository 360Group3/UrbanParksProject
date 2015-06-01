package model;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Handles all of the login and registration functionality for one specific user.
 * @author Reid Thompson - most recent implementation.
 * @version May 31 2015
 */
public class Login {
	
	//Class Constants
    public static String LOGIN_ACTION = "login";
    public static String REGISTER_ACTION = "register";
    
    //Class Variable
	private String[] myUserInfo;
	
    /**
     * Pattern for an email based on the following criteria, a partial implementation of RFC 3696.
     * <ul>
     *  <li>The local part must be between 1 and 64 characters.</li>
     *  <li>The local part can consist of ASCII word characters, as well as the special characters !#$%&'*+-/=?^_`.{|}~.
     *  The local part cannot begin or end with a period (.).</li>
     *  <li>The domain part must be between 1 and 255 characters.</li>
     *  <li>The domain part can consist of ASCII word characters, as well as periods (.) and hyphens (-), 
     *  though they cannot start or end with periods or hyphens. 
     *  A period or hyphen cannot be followed by a period or hyphen.</li>
     *  <li>The local part and the domain part must be separated by a @ character.</li>
     * </ul>
     */
	private Pattern myEmailPattern;
	
	/**
	 * Constructor for Login model.
	 */
	public Login() {
		myUserInfo = new String[5];
		
		//Hand-crafted by Mike
        myEmailPattern = Pattern.compile("(?=^[^@]{1,64}@)([\\w!#$%&'*+\\-/=?^_`{|}~]+(?:\\.[\\w!#$%&'*+\\-/=?^_`{|}~]+)*)@(?=[^@]{1,255}$)(\\w+(?:(?:\\.|-)\\w+)*)");
	}
    
	

	/*=================*
	 * Setters/Getters *
	 *=================*/
	
	/**
	 * Set the information needed for an existing User to Login.
	 * @param theEmail the email of the User.
	 */
	public void setUserInfoLogin(String theEmail) {
    	myUserInfo[0] = LOGIN_ACTION;
    	myUserInfo[1] = theEmail;
    }
    
	/**
	 * Set the information needed for a new User to register.
	 * @param theEmail the email of the User.
	 * @param theFirstName the first name of the User.
	 * @param theLastName the last name of the User.
	 * @param theUserType the type of the User, either "Volunteer", "ParkManager", or "Administrator"
	 */
    public void setUserInfoRegister(String theEmail, String theFirstName,
    								String theLastName, String theUserType) {
    	myUserInfo[0] = REGISTER_ACTION;
    	myUserInfo[1] = theEmail;
    	myUserInfo[2] = theFirstName;
    	myUserInfo[3] = theLastName;
    	myUserInfo[4] = theUserType;
    }
    
    /**
     * Return the login/register information of the User, which is an array of strings consisting of:
     * <ol>
     *  <li>the Action of the user</li>
     *  <li>the user's email</li>
     *  <li>the user's first name</li>
     *  <li>the user's last name</li>
     *  <li>the user's type</li>
     * </ol>
     */
    public String[] getUserInfo() {
    	return Arrays.copyOf(myUserInfo, myUserInfo.length);
    }
	
    

    /*========================*
     * Login/Register Process *
     *========================*/
	
    /**
     * Return true if the registration was successful and false otherwise.
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
     * Return true if the login was successful and false otherwise.
     */
    public boolean loginUser() {
    	boolean loginSuccess = false;
        if (duplicateUserRegistrationCheck()) {
            loginSuccess = true;
        }
        return loginSuccess;
    }
    
    /*==================*
     * Email Validation *
     *==================*/
    
    /**
     * Validate an email based on myEmailPattern.
     * 
     * @param theEmail the email to be tested
     * @return true if it is a valid email, false otherwise
     */
    public boolean checkEmailFormat(String theEmail) {
        if (theEmail == null)
            return false;
        
    	return myEmailPattern.matcher(theEmail).matches();
    }
    
    
    
    /*================*
     * Helper Methods *
     *================*/
    
    /**
     * Return true if email is already used; false otherwise.
     */
    private boolean duplicateUserRegistrationCheck() {
        return DataPollster.getInstance().checkEmail(myUserInfo[1]);
    }
    
    /**
     * Return true if the User Info is valid by making sure no elements are null; false otherwise.
     */
    private boolean validUserRegistrationCheck() {
        if (myUserInfo == null)
            return false;
        
        boolean isValid = true;
        for (String str : myUserInfo) {
            if (str == null) {
                isValid = false;
            }
        }
        return isValid;
    }
}
