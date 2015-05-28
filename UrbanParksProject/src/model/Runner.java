package model;

import java.util.List;

import view.LoginUI;

public class Runner {

	public static void main(String[] args) {
		SaveLoad saveManager = new SaveLoad();
        LoginUI UI = new LoginUI();

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
            
            UI.commandLoop();

            saveManager.saveJobList(jobList);
            saveManager.saveUserList(userList);
        }
	}
}
