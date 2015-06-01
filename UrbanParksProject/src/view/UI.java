package view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public abstract class UI {

    private Scanner myScanner;
    
    public UI()
    {
        myScanner = new Scanner(System.in);
    }
    
    /**
     * The loop for the User's menu.
     */
    public abstract void commandLoop();
	
	 /**
     * Return an integer that the user has typed.
     */
    protected int getUserInt() {
        int userInput = 0;

        if(myScanner.hasNextInt()) {
            userInput = myScanner.nextInt();
        } else {
            myScanner.next();
        }

        return userInput;
    }
    
    /**
     * Return a String that the user has typed.
     */
    protected String getUserString() {     
        String userInput = myScanner.nextLine();
        
        if(userInput.equals("")) {
            userInput = myScanner.nextLine();
        }
        return userInput;
    }
    
    /**
     * Convert a GregorianCalendar object to string of format mmddyyyy.
     */
    protected String calendarToString(GregorianCalendar theCalendar) {
        String returnString = theCalendar.get(Calendar.MONTH) + "/" +
                theCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
                theCalendar.get(Calendar.YEAR);
        return returnString;
    }
    
    /**
     * All UIs display this message when, inside their menu, an invalid command
     * was entered by the User.
     */
    protected void displayInvalidCommandEntered() {
    	System.out.println("Invalid command entered. Please try again.\n");
    }
    
    /**
     * All UIs display this message when the user selects to logout of their menu.
     */
    protected void displayLogoutMessage() {
    	System.out.println("Logging out now.\n");
    }
}
