package model;

import view.LoginUI;

public class Runner {

	public static void main(String[] args) {
		SaveLoad saveManager = new SaveLoad();
        String[] userInfo;
        LoginUI UI = new LoginUI();
        UI.initialize();

        // We return to the top of the loop whenever the user logs out, and
        // prompt them to login again.
        // The loop continues until the user selects Exit.
        while (true) {
            JobList jobList = saveManager.loadJobList();
            UserList userList = saveManager.loadUserList();

            Schedule.getInstance().setJobList(jobList);
            Schedule.getInstance().setUserList(userList);

            DataPollster.getInstance().setJobList(jobList);
            DataPollster.getInstance().setUserList(userList);

            userInfo = UI.directLogin();

            // If the command or information entered was invalid, we try again.
            if (userInfo == null) {
                directLogin();
            }

            try {
                if (userInfo[0].equals("login")) {
                    giveControl(userInfo[1]);
                }
            }
            catch (NullPointerException e) {
                System.out
                        .println("\nWe ran into a problem while logging you in. Please try again.");
                directLogin();
            }

            if (userInfo[0].equals("register")) {
                Schedule.getInstance().addUser(userInfo[1], userInfo[2], userInfo[3],
                        userInfo[4]);
                giveControl(userInfo[1]);
            }

            saveManager.saveJobList(jobList);
            saveManager.saveUserList(userList);

            UI.greetUser();
        }
	}
}
