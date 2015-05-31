package view;

import java.util.List;
import java.util.Scanner;

import model.Administrator;
import model.DataPollster;
import model.Job;
import model.User;

/**
 * A class for the console user interface for Administrators.
 * 
 * @author Reid Thompson
 * @version 5.10.2015
 */
public class AdministratorUI extends UI {

    /**
     * A Scanner object to read input for the Administrator's UI.
     */
    private Scanner myIn;

    private Administrator myAdmin;

    /**
     * Constructs an AdministratorUI object.
     * 
     * @param theAdmin
     *            the administrator
     */
    public AdministratorUI(Administrator theAdmin) {
        myIn = new Scanner(System.in);
        myAdmin = theAdmin;
    }

    @Override
    public void commandLoop() {
        boolean stayLoggedIn = true;
        while (stayLoggedIn) {
            listCommands();
            int choice = getUserInt();
            stayLoggedIn = executeOptionChosen(choice);
        }
    }

    /**
     * Calls appropriate methods if valid input is given. Otherwise, it
     * reprompts the user with the original list of commands.
     * 
     * @param theChoice
     *            is the integer of the option to be executed by the user.
     * @return false if the user wishes to log out, and true otherwise.
     */
    private boolean executeOptionChosen(final int theChoice) {
        boolean stayLoggedIn = true;

        if (theChoice <= 0 || theChoice > 3) {
            System.out.println("Invalid command selected. Please try again.\n");
        }
        else { // valid input was given
            switch (theChoice) { // no default case needed because of original
                                 // if test
                case 1: // list all volunteers by last name, first name (sorted ascending)
                    displayVolunteers(myAdmin.getAllVolunteersByLNFN());
                    break;
                case 2: // search volunteers by last name
                    searchByLastName();
                    break;
                case 3: // logout
                    System.out.println("Logging out now.\n");
                    stayLoggedIn = false;
                    break;
            }
        }
        return stayLoggedIn;
    }

    /**
     * Prints the commands that the Administrator can select.
     */
    public void listCommands() {
        System.out.println("\n------------------------------------------\n"
                + "Administrator Menu\n" + "\nWhat would you like to do?");
        System.out.println("1) List All Volunteers");
        System.out.println("2) Search Volunteers by Last Name");
        System.out.println("3) Logout");
    }

    private List<User> searchByLastName() {
        String lastName = promptForVolsLastName();

        // get and output list of Volunteers with matching last names
        List<User> matchingVols = myAdmin.getMatchingVolunteers(lastName);

        if (!matchingVols.isEmpty()) {
            System.out.println("Here is every Volunteer with the last name " 
                                + lastName + ":");

            displayVolunteers(matchingVols);
        }
        else {
            System.out.println("There were no Volunteers matching the last name "
                                + lastName);
        }

        return matchingVols;
    }

    /**
     * Outputs the list of Volunteers with matching last names sorted by first
     * name in ascending order.
     * 
     * @param theVols
     *            is the list of Volunteers with matching last names.
     */
    private void displayVolunteers(final List<User> theMatchingVols) {

        for (int i = 0; i < theMatchingVols.size(); i++) {
            final User v = theMatchingVols.get(i);
            System.out.println(v.getFirstName() + " " + v.getLastName());
            System.out.println("Email: " + v.getEmail());
            List<Job> jobs = DataPollster.getInstance().getVolunteerJobs(v.getEmail());
            if (!jobs.isEmpty()) {
                System.out.println("Jobs signed up for: ");
                for (int j = 0; j < jobs.size(); j++) {
                    final Job job = jobs.get(j);
                    System.out.println("\tJob ID #" + job.getJobID());
                }
            }
            else {
                System.out.println("This volunteer has not signed up for any jobs.\n");
            }
        }
    }

    /**
     * Returns a String of the last name of the Volunteer to search for.
     * 
     * @return a String of the last name of the Volunteer to search for.
     */
    public String promptForVolsLastName() {
        System.out.print("Please enter the last name of the Volunteer to search for: ");
        String lastName = getUserString();

        boolean lastNameValid = !lastName.equals(""); // other conditions to

        // add?
        while (!lastNameValid) {
            System.out.println("No last name was entered. Please try again.\n");
            lastName = getUserString();
            lastNameValid = !lastName.equals("");
        } // lastName is valid now

        return lastName;
    }
}
