package model;

import view.AdministratorUI;
import view.MainUI;
import view.ParkManagerUI;
import view.UI;
import view.VolunteerUI;

public class Main {

    public static MainUI UI;

    public static void main(String[] args) {

        SaveLoad saveManager = new SaveLoad();
        String[] userInfo;

        UI = new MainUI();

        UI.initialize();

        // We return to the top of the loop whenever the user logs out, and
        // prompt them to login again.
        // The loop continues until the user selects Exit.
        while (true) {
            JobList jobList = saveManager.loadJobList("jobList.ser");
            UserList userList = saveManager.loadUserList("userList.ser");

            Schedule.getInstance().setJobList(jobList);
            Schedule.getInstance().setUserList(userList);

            DataPollster.getInstance().setJobList(jobList);
            DataPollster.getInstance().setUserList(userList);

            userInfo = directLogin();

            // If the command or information entered was invalid, we try again.
            if (userInfo == null) {
                continue;
            }

            try {
                if (userInfo[0].equals("login")) {
                    giveControl(userInfo[1]);
                }
            }
            catch (NullPointerException e) {
                System.out.println("\nWe ran into a problem while logging you in. Please try again.");
                continue;
            }

            if (userInfo[0].equals("register")) {
                Schedule.getInstance().addUser(userInfo[1], userInfo[2], userInfo[3],
                        userInfo[4]);
                giveControl(userInfo[1]);
            }

            saveManager.saveJobList(jobList, "jobList.ser");
            saveManager.saveUserList(userList, "userList.ser");

            UI.greetUser();
        }

    }

    /**
     * Prompt the user to either login, register, or exit.<br>
     * Then, ask the user for login or register details.
     */
    private static String[] directLogin() {
        int loginCommand = UI.getLoginChoice();
        String[] userInfo = null;
        switch (loginCommand) {
            case 1:
                userInfo = loginUser();
                break;
            case 2:
                userInfo = registerUser();
                break;
            case 3:
                UI.displayExit();
                closeProgram();
                break; // Ends program.
            default:
                UI.displayInvalidChoice();
                break;
        }
        return userInfo;
    }

    /*
     * Return a String array that specifies the user as logging in, and the
     * e-mail of the user.
     */
    private static String[] loginUser() {

        String userEmail = UI.getReturnEmail();
        String[] userInfo = null;

        if (DataPollster.getInstance().checkEmail(userEmail)) {
            userInfo = new String[2];
            userInfo[0] = "login";
            userInfo[1] = userEmail;

        }
        else {
            UI.displayInvalidEmail();
        }
        return userInfo;
    }

    /*
     * Return a String array that specifies the user as registering, along with
     * info on that user.
     */
    private static String[] registerUser() {
        String[] userInfo = new String[5];

        userInfo[0] = "register";
        userInfo[1] = UI.getNewEmail();
        userInfo[2] = UI.getFirstName();
        userInfo[3] = UI.getLastName();
        userInfo[4] = UI.getUserType();

        if (checkDuplicate(userInfo[1])) {
            userInfo = null;
            UI.displayDuplicateError();
        }

        if (userInfo == null || userInfo[4] == null)
            userInfo = null;
        return userInfo;
    }

    /*
     * Check if the email is already being used. If so, return true. If not,
     * return false.
     */
    private static boolean checkDuplicate(String theEmail) {
        boolean status = false;

        for (User user : DataPollster.getInstance().getAllUserList()) {
            if (user.getEmail().equals(theEmail)) {
                status = true;
            }
        }

        return status;
    }

    /**
     * Transfer control to the user, specified by their e-mail address.
     */
    private static void giveControl(String theEmail) {

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

    /**
     * Breaks out of infinite loop in main method.
     */
    private static void closeProgram() {
        System.exit(0);
    }
}
