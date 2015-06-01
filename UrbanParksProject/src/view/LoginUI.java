package view;

import model.Administrator;
import model.DataPollster;
import model.Login;
import model.ParkManager;
import model.User;
import model.Volunteer;

/**
 * A user interface for the login/register sequence of the program.
 * 
 * @author Taylor Gorman
 * @author Reid Thompson - changed dependency relationship between this and Login class.
 * @version 26 May 2015
 *
 */
public class LoginUI extends UI {
    
	private Login myLogin;

    public LoginUI() {
    	myLogin = new Login();
    }
    
    /**
     * Outer loop of the UI that controls the options presented to the user. 
     */
    @Override
	public void commandLoop() {		
    	displayIntro();
		handleLoginAndReg();
	}
    
    /**
     * Prompt the user to either login, register, or exit.<br>
     * Then, ask the user for login or register details.
     */    
    private void handleLoginAndReg() {
    	displayLoginChoices();

    	int userChoice = getUserInt();
    	boolean validUserChoice = userChoice > 0 && userChoice < 4;
    	boolean loginSuccess = true;
    	boolean registerSuccess = true;
    	if (validUserChoice) { // userChoice must be 1, 2, or 3
    		switch (userChoice) {
    		case 1: // login
    			loginUserInfo();
    			if (myLogin.getUserInfo()[0].equals(Login.LOGIN_ACTION)) {
    				loginSuccess = myLogin.loginUser();
                    if (loginSuccess)
                        giveControlToUser();
    			}
    			break;
    		case 2: // register
    			registerUserInfo();
    			if (myLogin.getUserInfo()[0] != null 
    					&& myLogin.getUserInfo()[0].equals(Login.REGISTER_ACTION)) {
    				registerSuccess = myLogin.registerUser();
    				if (registerSuccess)
    				    giveControlToUser();
    			}
    			break;
    		case 3:
    			displayExit();
    			closeProgram();
    			break;
    		}
    	} else { // invalid choice selected
    		displayInvalidCommandEntered();
    		handleLoginAndReg();
    	}
    	// If the command or information entered was invalid, we try and try again.
    	if (!loginSuccess) {
    		displayNoExistingEmailMatch();
    		handleLoginAndReg();
    	} else if (!registerSuccess) {
    		displayDuplicateEmailError();
    		handleLoginAndReg();
    	} else if (myLogin.getUserInfo() == null) {
            commandLoop();
    	}
    }
    
    private void registerUserInfo() {
    	String email = getNewEmail();
    	if (!DataPollster.getInstance().checkEmail(email)) {
    		myLogin.setUserInfoRegister(email, getFirstName(), getLastName(),
        							getUserType());
    	} else {
    		displayDuplicateEmailError();
    	}
    }
    
    /**
     * Transfer control to the user, specified by their e-mail address.
     */
    private void giveControlToUser() {

        String[] userInfo = myLogin.getUserInfo();
        User user = DataPollster.getInstance().getUser(userInfo[1]); //get email
        
        UI userUI = null;

        if (user instanceof ParkManager) {
            userUI = new ParkManagerUI(userInfo[1]);
        }

        else if (user instanceof Administrator) {
            userUI = new AdministratorUI(userInfo[1]);
        }

        else if (user instanceof Volunteer) {
            userUI = new VolunteerUI(userInfo[1]);
        }

        if (userUI != null)
            userUI.commandLoop();
    }
    
    private void loginUserInfo() {
    	myLogin.setUserInfoLogin(getReturnEmail());
    }

    private String getReturnEmail() {
        System.out.println("------------------------------------------\n"
                       + "\nPlease enter your e-mail address to login:");
        String email = getUserString();
        boolean validEmail = myLogin.checkEmailFormat(email);
        while (!validEmail) {
        	System.out.println("------------------------------------------\n"
        			+ "\nInvalid email format. Please try again."
                    + "\nWhat is your e-mail address to login?");
        	email = getUserString();
            validEmail = myLogin.checkEmailFormat(email);
        }
        return email;
    }

    private String getNewEmail() {
        System.out.println("------------------------------------------\n"
                        + "\nWhat is your e-mail address?");
        String email = getUserString();
        boolean validEmail = myLogin.checkEmailFormat(email);
        while (!validEmail) {
        	System.out.println("------------------------------------------\n"
        			+ "\nInvalid email format. Please try again."
                    + "\nWhat is your e-mail address?");
        	email = getUserString();
            validEmail = myLogin.checkEmailFormat(email);
        }
        return email;
    }

    private String getFirstName() {
        System.out.println("\nWhat is your first name?");
        return getUserString();
    }

    private String getLastName() {
        System.out.println("\nWhat is your last name?");
        return getUserString();
    }
    
    private String getUserType() {
        System.out.println("\nWhat type of user are you?");
        System.out.println("1) Volunteer");
        System.out.println("2) Park Manager");
        System.out.println("3) Administrator");

        int userType = getUserInt();

        switch (userType) {
            case 1:
                return "Volunteer";
            case 2:
                return "ParkManager";
            case 3:
                return "Administrator";
            default:
                displayInvalidCommandEntered();
                return getUserType();
        }
    }

    private void displayIntro() {
        System.out.println("\n------------------------------------------\n"
                        + "Welcome to Urban Park's Volunteer Program!");
    }
    
    private void displayLoginChoices() {
        System.out.println("\n1) Existing User");
        System.out.println("2) New User");
        System.out.println("3) Exit\n");
    }
    
    private void displayExit() {
        System.out.println("\n--End Program--");
    }

    private void displayDuplicateEmailError() {
        System.out.println("\nSorry, but this email address is already in use.");
    }
    
    private void displayNoExistingEmailMatch() {
    	System.out.println("\nSorry, but there was no user with "
    			+ "the specified email address in our system.");
    }
    
	/**
     * Breaks out of infinite loop in main method.
     */
    private void closeProgram() {
        System.exit(0);
    }
}