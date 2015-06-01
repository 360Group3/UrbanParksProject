package view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.DataPollster;
import model.Job;
import model.ParkManager;
import model.Volunteer;

/**
 * A console-based user interface for park managers to use.
 * @author Taylor Gorman
 * @author Reid Thompson
 * @version 6 May 2015
 */
public class ParkManagerUI extends UI {
	
	//Class Variables
	private ParkManager myManager;
	
	/**
	 * Construct the manager.
	 * @param theManagerEmail the manager email
	 */
	public ParkManagerUI(String theManagerEmail) {
		super();
		this.myManager = DataPollster.getInstance().getParkManager(theManagerEmail);
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
			case 4: stayLoggedIn = false; displayLogoutMessage(); break;
			default: displayInvalidCommandEntered(); break;
			}
		}
	}
	
	
	/*===========*
	 * Main Menu *
	 *===========*/
	
	/**
	 * Display the possible commands that the user could type. 
	 */
	private void listCommands() {
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
	private void displayJobs() {
		
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
	
	private void viewJobVolunteers() {
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
	private int getJobID() {
		System.out.println("------------------------------------------\n\n"
				+ "Please input the ID of the job whose volunteers you would"
				+ " like to view.");
		int jobID = getUserInt();
		return jobID;
	}
	
	/**
	 * Take a List of Volunteers, and display their information to the console.
	 */
	private void displayVolunteers(List<Volunteer> theVolunteerList) {
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
	
	private void createNewJob() {
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
			
			List<List<String>> volunteerList = new ArrayList<>();
			
            boolean jobAdded = false;
			
            Pattern p = Pattern.compile("\\d{6}");
            if (p.matcher(startDate).matches() && p.matcher(endDate).matches()) {
            
    			Job newJob = new Job(jobID, parkName, lightSlots, mediumSlots, heavySlots, startDate,
    					endDate, myManager.getEmail(), volunteerList);
    			
    			try {
    				jobAdded = myManager.addJob(newJob);
    			} catch(IllegalArgumentException e) {
    				System.out.println(e.getMessage());
    			}
            }
            else
            {
                System.out.println("At least one of your dates was improperly formed.");
            }
			displayJobStatus(jobAdded);
		}
	}
	
	/**
	 * Request the user to select the appropriate park number.
	 */
	private void displayParkNumberRequest() {
		System.out.println("\n------------------------------------------\n"
				+ "Please select the number preceding the park where the job is located.");
	}
	
	/**
	 * Display all of the parks that the Park Manager manages in the console.
	 */
	private void displayParks(List<String> myManagedParks) {
		int i;
		for(i = 0; i < myManagedParks.size(); i++) {
			System.out.println(i + ") " + myManagedParks.get(i));
		}		
		System.out.println(i + ") Add New Park...");
	}
	
	private String createNewPark() {
		System.out.println("\n------------------------------------------");
		
		System.out.println("What is the name of the park?");
		String parkName = getUserString();
		
		return parkName;
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for light grade work, then
	 * return it.
	 */
	private int getLightSlots() {		
		System.out.println("\nHow many volunteers do you want for light grade work?");
		int myLight = 0;
		myLight = getUserInt();
		return myLight;
	}

	/**
	 * Prompt the user to enter how many volunteers they want for medium grade work, then
	 * return it.
	 */
	private int getMediumSlots() {		
		System.out.println("\nHow many volunteers do you want for medium grade work?");
		int myMedium = getUserInt();
		return myMedium;
	}
	
	/**
	 * Prompt the user to enter how many volunteers they want for heavy grade work, then
	 * return it.
	 */
	private int getHeavySlots() {	
		System.out.println("\nHow many volunteers do you want for heavy grade work?");
		int myHeavy = getUserInt();
		return myHeavy;
	}
	
	/**
	 * Prompt the user to enter when they want to start the job, then return it.
	 * @return The start date in format mmddyyyy
	 */
	private String getStartDate() {
		System.out.println("Please enter the start date of the job in the following format:"
				+ " mmddyyyy");
		String myStartDate = getUserString();
		return myStartDate;
	}
	
	/**
	 * Prompt the user to enter when they want to end the job, then return it.
	 * @return The end date in format ddmmyyyy
	 */
	private String getEndDate() {
		System.out.println("Please enter the end date of the job in the following format:"
				+ " mmddyyyy");
		String myEndDate = getUserString();
		System.out.println("\n");
		return myEndDate;
	}	
	
	/**
	 * Tell the user whether or not the job was successfully added.
	 */
	private void displayJobStatus(boolean addSuccess) {
		if(addSuccess){
			System.out.println("Your job was successfully added!");
		} else {
			System.out.println("Job not added...\nReturning to Park Manager Menu...");
		}
	}
}
