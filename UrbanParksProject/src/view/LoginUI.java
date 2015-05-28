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
        myScanner = new Scanner(System.in);
    }

    public void greetUser() {
        System.out.println("\n------------------------------------------\n"
                        + "Welcome to Urban Park's Volunteer Program!");
    }
    
    /**
     * Outer loop of the UI that controls the options presented to the user. 
     */
    @Override
	public void commandLoop() {
		greetUser();
		
		String[] userInfo = startLogin();

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
    	String[] userInfo = getUserInfo();    	
        if (myLogin.duplicateUserRegistrationCheck(userInfo)) {
            userInfo = null;
            displayDuplicateError();
        }
        return myLogin.validUserRegistrationCheck(userInfo);
    }
    
    // Get user login choice.
    public int getLoginChoice() {
        while (true) {
            displayLoginChoices();

            int userChoice = getUserInt();

            if (userChoice > 0 && userChoice < 4)
                return userChoice;
            else
                displayInvalidChoice();
        }
    }
    
    private String[] getUserInfo() {
    	String[] userInfo = new String[5];

        userInfo[0] = "register";
        userInfo[1] = getNewEmail();
        userInfo[2] = getFirstName();
        userInfo[3] = getLastName();
        userInfo[4] = getUserType();
        
        return userInfo;
    }
    
    private void displayLoginChoices() {
        System.out.println("\n1) Existing User");
        System.out.println("2) New User");
        System.out.println("3) Exit\n");
    }

    public String getReturnEmail() {
        System.out.println("------------------------------------------\n"
                       + "\nPlease enter your e-mail address to login:");
        return getUserString();
    }

    public String getNewEmail() {
        System.out.println("------------------------------------------\n"
                        + "\nWhat is your e-mail address?");
        return getUserString();
    }

    public String getFirstName() {
        System.out.println("\nWhat is your first name?");
        return getUserString();
    }

    public String getLastName() {
        System.out.println("\nWhat is your last name?");
        return getUserString();
    }

    public String getUserType() {
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

    public void displayExit() {
        System.out.println("\n--End Program--");
    }

    public void displayInvalidChoice() {
        System.out.println("\nSorry, but your choice was invalid.");
    }

    public void displayInvalidEmail() {
        System.out.println("\nSorry, but your e-mail address was not recognized.");
    }

    public void displayDuplicateError() {
        System.out.println("\nSorry, but this email address is already in use.");
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
}
