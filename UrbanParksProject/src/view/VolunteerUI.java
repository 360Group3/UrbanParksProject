package view;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.Volunteer;


/**This is the user interface for volunteer.
 * 
 * @author Arsh
 * 
 * @version 2 (May 22, 2015)
 */
public class VolunteerUI extends UI {

	private Volunteer myVol;
	
	/**
	 * Constructor.
	 * @param theVol is a volunteer.
	 */
	public VolunteerUI(String theVolunteerEmail) {
		super();
		this.myVol = new Volunteer(theVolunteerEmail);
	}
	
	/**
	 * Lists possible commands, prompts the user for one, and then acts on it.<br>
	 * If the user chooses to quit, or inputs an invalid command, then the loop terminates.
	 */
	@Override
	public void commandLoop() {
		boolean stayLoggedIn = true;
		
		while (stayLoggedIn) {
			listCommands();
			String command = getCommand();
	
			stayLoggedIn = parseCommand(command);
		}
	}
	
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	private void listCommands() {
		System.out.println("\n------------------------------------------");
		System.out.println("Volunteer Menu");
		System.out.println("\nWhat would you like to do?");
		System.out.println("1) View All Jobs");
		System.out.println("2) Sign Up for a Job");
		System.out.println("3) View My Jobs");
		System.out.println("4) Logout");
	}
	
	
	
	/**
	 * Parse a command, and call other methods to execute the command.
	 */
	private boolean parseCommand(String command) {
		command = command.toLowerCase(); //lower case to avoid ambiguity

		boolean status = true;
		
		switch(command) { 
		case "view jobs":
		case "view job":
		case "view":	
		case "v":
		case "1":
			displayJobs(myVol.getPendingJobs());
			break;

		case "sign up":
		case "sign":
		case "s":
		case "2":
		    try {
		        signUp();
		    }
		    catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
			break;

		case "view my jobs":
		case "view my job":
		case "j":
		case "m":
		case "3":
			viewMyJobs();
			break;

		case "exit":
		case "close":
		case "quit":
		case "4":
			status = false; //return false if user wants to exit.
			displayLogoutMessage();
			break;
		default: 
			displayInvalidCommandEntered();
			break; //return true and prompt the user again.
		}
		
		return status;
	}


	/**
	 * Return the command that the user has typed.
	 */
	private String getCommand() {	
		return getUserString();
	}
	


	/**
	 * The volunteer can sign up for jobs from here.
	 */
	private void signUp() {
		int jobID = getJobID();
		//display the available grades
		printGradeInfo(jobID);
		
		String level = getDifficultyLevel();

		ArrayList<String> volArray = new ArrayList<String>();
		volArray.add(myVol.getEmail());
		volArray.add(level);

		if(myVol.signUpForJob(volArray, jobID)) {
			displaySuccessMessage();
		}

	}


	private void printGradeInfo(int jobID) {
		Job leJob = findJob(jobID);

		if (leJob.hasLightRoom() || leJob.hasHeavyRoom() || leJob.hasMediumRoom()) {
			System.out.println();			
			System.out.println("There are available spots in the following grades:");
			if (leJob.hasLightRoom()) {
				System.out.println("LIGHT");
			}
			if (leJob.hasMediumRoom()) {
				System.out.println("MEDIUM");
			}
			if (leJob.hasHeavyRoom()) {
				System.out.println("HEAVY");
			}
		}
	}

	/**
	 * The volunteer can view the jobs that he/she has signed up for.
	 */
	private void viewMyJobs() {
		List<Job> jobList = myVol.getMyJobs(); //get the list of jobs so we can traverse it.
		if (jobList.size() > 0) {
			
			for(Job job : jobList) {
				printJobInfo(job);
			}
			
		} else {
			System.out.println("\nYou are not signed up for any upcoming jobs.");
		}
	}
	
	
	/**
	 * Prints the jobs information to the console.
	 * @param job is the job whose info is needed.
	 */
	private void printJobInfo(Job job) {
		String startDate = calendarToString(job.getStartDate());
		String endDate = calendarToString(job.getEndDate());

		String jobString = "\n";
		jobString += "Job ID: " + job.getJobID();
		jobString += "\n    " + job.getPark();

		jobString += "\n    Begins: " + startDate;
		jobString += " , Ends: " + endDate;

		jobString += "\n    Light Slots: " + job.getLightCurrent() + "/" + job.getLightMax();
		jobString += "\n    Medium Slots: " + job.getMediumCurrent() + "/" + job.getMediumMax();
		jobString += "\n    Heavy Slots: " + job.getHeavyCurrent() + "/" + job.getHeavyMax() + "\n";

		System.out.println(jobString);
	}

	

	/**
	 * This prints out the information on all the jobs in the 
	 * passed-in list.
	 * @param theJobList is the list whose jobs are needed to be printed.
	 */
	private void displayJobs(List<Job> theJobList) {
		
		if(theJobList.size() == 0) {
			System.out.println("\nThere are no upcoming jobs to display..");
		}

		for(Job job : theJobList) {
			if (!job.isInPast()) {
				String startDate = calendarToString(job.getStartDate());
				String endDate = calendarToString(job.getEndDate());

				String jobString = "\n";
				jobString += "Job ID: " + job.getJobID();
				jobString += "\n    " + job.getPark();

				jobString += "\n    Begins: " + startDate;
				jobString += " , Ends: " + endDate;

				jobString += "\n    Light Slots: " + job.getLightCurrent() + "/" + job.getLightMax();
				jobString += "\n    Medium Slots: " + job.getMediumCurrent() + "/" + job.getMediumMax();
				jobString += "\n    Heavy Slots: " + job.getHeavyCurrent() + "/" + job.getHeavyMax() + "\n";

				System.out.println(jobString);
			}
		}
	}

	
	/**
	 * Prompts the user to enter a number which represents the job's ID.
	 * @return ID number
	 */
	private int getJobID() {
		System.out.println("Please enter the Job ID of the job you would like to sign up for:");
		int ID = 0;
		
		ID = getUserInt();
		
		return ID;
	}
	
	/**
	 * Prompts the user to enter in a number depending on the difficulty of the work
	 * he wants to do.
	 * @return difficulty level.
	 */
	private String getDifficultyLevel() {
		System.out.println("\nPlease enter the work intensity you would like to sign up for:");
		int level = 0;
		
		System.out.println("1) Light");
		System.out.println("2) Medium");
		System.out.println("3) Heavy");
		
		level = getUserInt();
		String levelString = null;
		
		switch(level) {
		case 1: levelString = "Light"; break;
		case 2: levelString = "Medium"; break; 
		case 3: levelString = "Heavy"; break;
		}
		
		return levelString;
	}
	
	/**
     * Finds the job associated with this jobID.
     * @param theJobID is the jobs ID
     * @return the Job that has this Job ID, null otherwise.
     */
    private Job findJob(int theJobID) {
        //Calls JobList.getJobList() to get the master job list which is editable
        List<Job> editableJobList = myVol.getPendingJobs();


        for (int i = 0; i < editableJobList.size(); i++) {
            if (editableJobList.get(i).getJobID() == theJobID) {
                return editableJobList.get(i);
            }
        }
        return null;
    }

	/**
	 * Display a success method to let the volunteer know that he/she has been added
	 * to a job.
	 */
	private void displaySuccessMessage() {
		System.out.println("\nYou were successfully added to the job!\nThank you for helping to make the world a better place,"
				+ " one park at a time!");
		
	}
}
