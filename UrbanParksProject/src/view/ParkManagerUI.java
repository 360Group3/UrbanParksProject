package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.ParkManager;
import model.Volunteer;

/**
 * A console-based user interface for park managers to use.
 * @author Taylor Gorman
 * @author Reid Thompson
 * @version 6 May 2015
 */
public class ParkManagerUI implements UI{
	
	//Class Variables
	private Scanner myScanner;
	private ParkManager myManager;
	
	/**
	 * Construct the manager.
	 * @param theManager the manager
	 */
	public ParkManagerUI(ParkManager theManager) {
		myScanner = new Scanner(System.in);
		this.myManager = theManager;
	}
	
	
	@Override
	public void commandLoop() {
		boolean stayLoggedIn = true;
		
		while (stayLoggedIn) {
			listCommands();		
			int command = getUserInt();
			
			switch(command) {
			case 1: createNewJob(); break;
			case 2: displayJobs(); break;
			case 3: viewJobVolunteers(); break;
			case 4: stayLoggedIn = false; break;
			}
		}
	}
	
	
	/*===========*
	 * Main Menu *
	 *===========*/
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	public void listCommands() {
		System.out.println("\n------------------------------------------\nPark Manager Menu\n\nWhat would you like to do?");
		System.out.println("1) Create New Job");
		System.out.println("2) View My Jobs");
		System.out.println("3) View Volunteers for a Job");
		System.out.println("4) Logout");
	}
	
	
	
	/*===============*
	 * View All Jobs *
	 *===============*/
	
	/**
	 * Take an ArrayList of Jobs, parse them, and then display their information in the console.
	 */
	public void displayJobs() {
		
		List<Job> jobList = myManager.getJobs();
		
		if(jobList.size() == 0) {
			System.out.println("\nYou do not have any upcoming jobs to display.");
		}
		
		for(Job job : jobList) {
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
	
	
	
	
	/*=====================*
	 * View Job Volunteers *
	 *=====================*/
	
	public void viewJobVolunteers() {
		int jobID = getJobID();
		List<Volunteer> volunteerList = myManager.getJobVolunteerList(jobID);
		
		if(volunteerList != null) {
			displayVolunteers(volunteerList);
		} else {
			//The ParkManager does not manage this job.
			System.out.println("Sorry, but you are not the manager for this job.");
		}
		
		
	}
	
	/**
	 * Prompt the user to enter the ID of a Job, and then return it.
	 */
	public int getJobID() {
		System.out.println("------------------------------------------\n\n"
				+ "Please input the ID of the job whose volunteers you would"
				+ " like to view.");
		int jobID = getUserInt();
		return jobID;
	}
	
	/**
	 * Take a List of Volunteers, and display their information to the console.
	 */
	public void displayVolunteers(List<Volunteer> theVolunteerList) {
		if (!theVolunteerList.isEmpty()) {
			for(Volunteer volunteer : theVolunteerList) {
				System.out.println("Name: " + volunteer.getFirstName() + " " + volunteer.getLastName());
				System.out.println("Email: " + volunteer.getEmail());
			}
		} else {
			System.out.println("There are no Volunteers associated with this Job.");
		}
	}
	
	
	/*==============*
	 * Job Creation *
	 *==============*/
	
	public void createNewJob() {
		List<String> managedParks = new ArrayList<String>();
		managedParks.addAll(myManager.getManagedParks());
				
		
		displayParkNumberRequest();
		displayParks(managedParks);
		int parkNumber = getUserInt();
		
		if(parkNumber >= managedParks.size()) {
			String newParkName = createNewPark();
			managedParks.add(newParkName);
			myManager.setManagedParks(managedParks);
			createNewJob();
		} else {
			int jobID = myManager.getNewJobID();
			String parkName = managedParks.get(parkNumber);
			
			int lightSlots = getLightSlots();
			int mediumSlots = getMediumSlots();
			int heavySlots = getHeavySlots();
			
			String startDate = getStartDate();
			String endDate = getEndDate();
			
			ArrayList<ArrayList<String>> volunteerList = new ArrayList<ArrayList<String>>();
			
			Job newJob = new Job(jobID, parkName, lightSlots, mediumSlots, heavySlots, startDate,
					endDate, myManager.getEmail(), volunteerList);
			
			boolean jobAdded = myManager.addJob(newJob);
			
			displayJobStatus(jobAdded);
		}
	}
	
	/**
	 * Request the user to select the appropriate park number.
	 */
	public void displayParkNumberRequest() {
		System.out.println("\n------------------------------------------\n"
				+ "Please select the number preceding the park where the job is located.");
	}
	
	/**
	 * Display all of the parks that the Park Manager manages in the console.
	 */
	public void displayParks(List<String> myManagedParks) {
		int i;
		for(i = 0; i < myManagedParks.size(); i++) {
			System.out.println(i + ") " + myManagedParks.get(i));
		}		
		System.out.println(i + ") Add New Park...");
	}
	
	public String createNewPark() {
		System.out.println("\n------------------------------------------");
		
		System.out.println("What is the name of the park?");
		String parkName = getUserString();
		
		return parkName;
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for light grade work, then
	 * return it.
	 */
	public int getLightSlots() {		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = 0;
		myLight = getUserInt();
		return myLight;
	}

	/**
	 * Prompt the user to enter how many volunteers they want for medium grade work, then
	 * return it.
	 */
	public int getMediumSlots() {		
		System.out.println("\nHow many volunteers do you want for medium grade work?");
		int myMedium = getUserInt();
		return myMedium;
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for heavy grade work, then
	 * return it.
	 */
	public int getHeavySlots() {	
		System.out.println("\nHow many volunteers do you want for heavy grade work?");
		int myHeavy = getUserInt();
		return myHeavy;
	}
	
	/**
	 * Prompt the user to enter when they want to start the job, then return it.
	 * @return The start date in format mmddyyyy
	 */
	public String getStartDate() {
		System.out.println("Please enter the start date of the job in the following format:"
				+ " mmddyyyy");
		String myStartDate = getUserString();
		return myStartDate;
	}
	
	/**
	 * Prompt the user to enter when they want to end the job, then return it.
	 * @return The end date in format ddmmyyyy
	 */
	public String getEndDate() {
		System.out.println("Please enter the end date of the job in the following format:"
				+ " mmddyyyy");
		String myEndDate = getUserString();
		System.out.println("\n");
		return myEndDate;
	}	
	
	/**
	 * Tell the user whether or not the job was successfully added.
	 */
	public void displayJobStatus(boolean addSuccess) {
		if(addSuccess){
			System.out.println("Your job was successfully added!");
		} else {
			System.out.println("Job not added...\nReturning to Park Manager Menu...");
		}
	}		

	
	
	/*================*
	 * Helper Classes *
	 *================*/
	
	/**
	 * Convert a GregorianCalendar object to string of format mmddyyyy
	 */
	private String calendarToString(GregorianCalendar theCalendar) {
		String returnString = theCalendar.get(Calendar.MONTH) + "/" +
				theCalendar.get(Calendar.DAY_OF_MONTH) + "/" +
				theCalendar.get(Calendar.YEAR);
		return returnString;
	}
	
	/**
	 * Return an integer that the user has typed.
	 */
	public int getUserInt() {
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
	public String getUserString() {		
		String userInput = myScanner.nextLine();
		
		if(userInput.equals("")) {
			userInput = myScanner.nextLine();
		}
		return userInput;
	}
}
