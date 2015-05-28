package view;

import java.util.Scanner;

import model.DataPollster;
import model.Login;
import model.Schedule;

/**
 * A user interface for the login/register sequence of the program.
 * 
 * @author Taylor Gorman
 * @author Reid Thompson - changed dependency relationship between this and Login class.
 * @version 9 May 2015
 *
 */
public class LoginUI implements UI {

	private Login myLogin;
	
    private Scanner myScanner;

    public LoginUI() {
    	myLogin = new Login();
        myScanner = new Scanner(System.in);
    }
    
    /**
     * Outer loop of the UI that controls the options presented to the user. 
     */
    @Override
	public void commandLoop() {
		String[] userInfo = null;
		
    	displayIntro();
		handleLoginAndReg();
		
		userInfo = myLogin.getUserInfo();

        // If the command or information entered was invalid, we try and try again.
        while (userInfo == null) {
            handleLoginAndReg();
            userInfo = myLogin.getUserInfo();
        }

        try { // try to 
            if (userInfo[0].equals("login")) {
                myLogin.giveControl(userInfo[1]);
            }
        }
        catch (NullPointerException e) {
            System.out
                    .println("\nWe ran into a problem while logging you in. Please try again.");
            while (userInfo == null) {
                handleLoginAndReg();
                userInfo = myLogin.getUserInfo();
            }
        }

        if (userInfo[0].equals("register")) {
            Schedule.getInstance().addUser(userInfo[1], userInfo[2], userInfo[3],
                    userInfo[4]);
            myLogin.giveControl(userInfo[1]);
        }
	}
    
    /**
     * Prompt the user to either login, register, or exit.<br>
     * Then, ask the user for login or register details.
     */    
    private void handleLoginAndReg() {
    	displayLoginChoices();

    	int userChoice = getUserInt();
    	boolean validUserChoice = userChoice > 0 && userChoice < 4;
    	if (validUserChoice) { // userChoice must be 1, 2, or 3
    		switch (userChoice) {
    		case 1: // login
    			loginUserInfo();
    			myLogin.processLoginAndReg(userChoice);
    			break;
    		case 2: // register
    			registerUserInfo();
    			myLogin.processLoginAndReg(userChoice);
    			break;
    		case 3:
    			displayExit();
    			myLogin.closeProgram();
    			break;
    		}
    	} else { // invalid choice selected
    		displayInvalidChoice();
    		handleLoginAndReg();
    	}
    }
    
    private void registerUserInfo() {
        myLogin.setUserInfoRegister("register", getNewEmail(), getFirstName(), getLastName(),
        							getUserType());
    }
    
    private void loginUserInfo() {
    	myLogin.setUserInfoLogin("login", getReturnEmail());
    }

    private String getReturnEmail() {
        System.out.println("------------------------------------------\n"
                       + "\nPlease enter your e-mail address to login:");
        return getUserString();
    }

    private String getNewEmail() {
        System.out.println("------------------------------------------\n"
                        + "\nWhat is your e-mail address?");
        return getUserString();
    }

    private String getFirstName() {
        System.out.println("\nWhat is your first name?");
        return getUserString();
    }

    private String getLastName() {
        System.out.println("\nWhat is your last name?");
        return getUserString();
    }

    private int getUserInt() {
        int userInput = 0;

        if (myScanner.hasNextInt()) {
            userInput = myScanner.nextInt();
        }
        else {
            myScanner.next();
        }

        return userInput;
    }

    private String getUserString() {

        String userInput = myScanner.nextLine();

        if (userInput.equals("")) { // TODO, maybe make this a while so that it will
                                    // continuously
                                    // prompt the user, instead of just once?
            userInput = myScanner.nextLine();
        }

        return userInput;
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
                displayInvalidChoice();
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

    private void displayInvalidChoice() {
        System.out.println("\nSorry, but your choice was invalid.");
    }

    private void displayInvalidEmail() {
        System.out.println("\nSorry, but your e-mail address was not recognized.");
    }

    private void displayDuplicateError() {
        System.out.println("\nSorry, but this email address is already in use.");
    }
}
