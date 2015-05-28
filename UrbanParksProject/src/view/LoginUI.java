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
		displayIntro();
		
		handleLogin();
		
		String[] userInfo = myLogin.getUserInfo();

        // If the command or information entered was invalid, we try again.
        if (userInfo == null) {
            userInfo = startLogin();
        }

        try {
            if (userInfo[0].equals("login")) {
                myLogin.giveControl(userInfo[1]);
            }
        }
        catch (NullPointerException e) {
            System.out
                    .println("\nWe ran into a problem while logging you in. Please try again.");
            userInfo = startLogin();
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
    public void startLogin() {
        int loginCommand = getLoginChoice();
        boolean invalidChoice = myLogin.directLogin(loginCommand);
        if (invalidChoice) {
        	displayInvalidChoice();
        }
    }
    
    /**
     * Return a String array that specifies the user as registering, along with
     * info on that user.
     */
    private String[] registerUser() {
    	String[] userInfo = myLogin.getUserInfo();   	
        if (myLogin.duplicateUserRegistrationCheck(userInfo)) {
            userInfo = null;
            displayDuplicateError();
        }
        return myLogin.validUserRegistrationCheck(userInfo);
    }
    
    private void handleLogin() {
        while (true) {
            displayLoginChoices();

            int userChoice = getUserInt();

            if (userChoice > 0 && userChoice < 4)
            	myLogin.processUserChoice(userChoice);
            else
                displayInvalidChoice();
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
