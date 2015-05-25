package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import model.DataPollster;
import model.Job;
import model.Schedule;
import model.Volunteer;


/**This is the user interface for volunteer.
 * 
 * @author Arsh
 * 
 * @version 2 (May 22, 2015)
 */
public class VolunteerUI implements UI {

	private Scanner myScanner;
	private Volunteer myVol;
	
	/**
	 * Constructor.
	 * @param theVol is a volunteer.
	 */
	public VolunteerUI(Volunteer theVol) {
		myScanner = new Scanner(System.in);
		myVol = theVol;
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
			displayJobs(getTheJobs());
			break;

		case "sign up":
		case "sign":
		case "s":
		case "2":
			signUp();
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
			break;
		default: 
			System.out.println("You did not enter a valid number.");
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

		String level = getDifficultyLevel();

		ArrayList<String> volArray = new ArrayList<String>();
		volArray.add(myVol.getEmail());
		volArray.add(level);

		if(myVol.signUp(volArray, jobID)) {
			displaySuccessMessage();
		}

	}


	/**
	 * The volunteer can view the jobs that he/she has signed up for.
	 */
	private void viewMyJobs() {
		List<Job> jobList = myVol.getJobs(); //get the list of jobs so we can traverse it.
		boolean jobFound = false;

		//go through each job in the list and see if the volunteer has signed up for that job.
		for(Job job : jobList) {
			ArrayList<ArrayList<String>> volunteerList = job.getVolunteerList();
			
			
			for(ArrayList<String> volunteer : volunteerList) {
				if(volunteer.get(0).equals(myVol.getEmail())) {
					printJobInfo(job);
					jobFound = true;
				}
			}
		}
		
		if(!jobFound) {
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
	 * This method gets the list of jobs from DataPollster and sends it on to
	 * wherever its needed.
	 * It also sets each jobs 'myPast' field which indicates whether or not 
	 * the job is in the past.
	 */
	private List<Job> getTheJobs() {
		List<Job> daJobs = myVol.getJobs();
		Calendar currentDate = new GregorianCalendar();
		
		for (Job j: daJobs) { //go through each job and find out what job is in the past.
								//then change that job's JobID to -1 so that it can be
								//checked for and ignored when displaying the jobs.
			if(currentDate.getTimeInMillis() + 2670040009l > j.getStartDate().getTimeInMillis()) {
				j.setIfPast(true);
			}
		}

		return daJobs;
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
	 * Return an integer that the user has typed.
	 */
	private int getUserInt() {
		int userInput = 0;

		if(myScanner.hasNextInt()) {
			userInput = myScanner.nextInt();
		} else {
			myScanner.next();
		}

		return userInput;
	}
	
	/**
	 * Prompt the user to enter something.
	 * @return a string of what the user entered.
	 */
	private String getUserString() {		
		String userInput = myScanner.nextLine();
		
		if(userInput.equals("")) { 
			userInput = myScanner.nextLine();
		}
		return userInput;
	}
	
	/**
	 * Display a success method to let the volunteer know that he/she has been added
	 * to a job.
	 */
	private void displaySuccessMessage() {
		System.out.println("\nYou were successfully added to the job!\nThank you for helping to make the world a better place,"
				+ " one park at a time!");
		
	}
	
	

	/**
	 * Convert a GregorianCalendar object to string of format mmddyyyy
	 */
	private String calendarToString(GregorianCalendar theCalendar) {
		String returnString = theCalendar.get(Calendar.MONTH) + "/" +
				theCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
				theCalendar.get(Calendar.YEAR);
		return returnString;
	}

	
}
