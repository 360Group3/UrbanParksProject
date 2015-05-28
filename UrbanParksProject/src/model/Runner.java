package model;

import view.LoginUI;

/**
 * A "Runner" class for the main method of the Urban Parks project.
 * 
 * @author Reid Thompson - most recent implementation
 * @version 5.27.2015
 */
public class Runner {

	public static void main(String[] args) {
		SaveLoad saveManager = new SaveLoad();
        LoginUI UI = new LoginUI();

        String jobFile = "jobList.ser";
        String userFile = "userList.ser";
        // We return to the top of the loop whenever the user logs out, and
        // prompt them to login again.
        // The loop continues until the user selects Exit.
        while (true) {
            JobList jobList = saveManager.loadJobList(jobFile);
            UserList userList = saveManager.loadUserList(userFile);

            Schedule.getInstance().setJobList(jobList);
            Schedule.getInstance().setUserList(userList);

            DataPollster.getInstance().setJobList(jobList);
            DataPollster.getInstance().setUserList(userList);
            
            UI.commandLoop();

            saveManager.saveJobList(jobList, jobFile);
            saveManager.saveUserList(userList, userFile);
        }
	}
}
