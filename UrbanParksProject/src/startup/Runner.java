package startup;


import model.DataPollster;
import model.JobList;
import model.SaveLoad;
import model.Schedule;
import model.UserList;
import view.LoginUI;

/**
 * A "Runner" class for the main method of the Urban Parks project.<br>
 * Initializes the program and starts the login/registration process for the User.
 * 
 * @author Reid Thompson - most recent implementation
 * @version 5.27.2015
 */
public class Runner {

	public static void main(String[] args) {
		
		//Create SaveManager and UI objects
		SaveLoad saveManager = new SaveLoad();
        LoginUI UI = new LoginUI();

        //Set File Names
        String jobFile = "rsc/jobListBR2.ser";
        String userFile = "rsc/userListBR2.ser";
        
        /*
         * This constitutes the main, outer loop of the program.
         * Here, we load data from .ser files, update Schedule and DataPollster with that data,
         * start the Command Loop for the User, and when the User logs out, we save the data to the .ser files.
         * 
         * The loop returns to the top whenever a User logs out, and only terminates when the User selects Exit.
         */
        while (true) {
        	//Load JobList and UserList.
            JobList jobList = saveManager.loadJobList(jobFile);
            UserList userList = saveManager.loadUserList(userFile);

            //Update Schedule with loaded Lists.
            Schedule.getInstance().setJobList(jobList);
            Schedule.getInstance().setUserList(userList);

            //Update DataPollster with loaded Lists.
            DataPollster.getInstance().setJobList(jobList);
            DataPollster.getInstance().setUserList(userList);
            
            //Start the Command Loop for the User.
            UI.commandLoop();

            //User logs out.
            
            //Save JobList and UserList.
            saveManager.saveJobList(jobList, jobFile);
            saveManager.saveUserList(userList, userFile);
        }
	}
}
