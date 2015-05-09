package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Main {

	public static MainUI myUI;
	

	public static void main(String[] args) {
		
		JobList myJobList = new JobList();
		UserList myUserList = new UserList();
		Schedule mySchedule = new Schedule(myJobList);
		DataPollster myPollster = new DataPollster(myJobList, myUserList);
		myUI = new MainUI();
		
		myUI.initialize();
		directLogin(mySchedule, myPollster);
	}
	
	
	
	/**
	 * Prompt the user to either login, register, or exit.<br>
	 * Then, ask the user for login or register details.
	 */
	public static User directLogin(Schedule mySchedule, DataPollster myPollster) {
		int loginCommand = myUI.getLoginChoice();
		User myUser = null;
		
		switch (loginCommand) {
			case 1: myUser = loginUser(mySchedule, myPollster); break;
			case 2: myUser = registerUser(mySchedule, myPollster); break;
			case 3: myUI.displayExit(); closeProgram(); break; //Ends program.
			
			//If invalid, loop back to get a different command.
			default: myUI.displayInvalidChoice(); directLogin(mySchedule, myPollster); break;
		}
		
		return myUser;
	}
	
	//Return the User, unless the email was not recognized, in which we loop back to directLogin()
	private static User loginUser(Schedule mySchedule, DataPollster myPollster) {
		String userEmail = myUI.getUserEmail();
		
		if(myPollster.checkEmail(userEmail)) {
			return mySchedule.getUser(userEmail);
		} else {
			myUI.displayInvalidEmail();
			directLogin(mySchedule, myPollster);
		}
		
		return null; //Not accessible.
	}
	
	private static User registerUser(Schedule mySchedule, DataPollster myPollster) {
		
		
		return null;
	}
		
		
		/*
		ArrayList<Park> myParkList = new ArrayList<Park>();
		Park myPark = new Park("Bobcat Park", "Hugo", 98335);
		Park myPark2 = new Park("Seahurt Park", "Burien", 98106);
		
		myParkList.add(myPark);
		myParkList.add(myPark2);
		
		
		ParkManager myManager = new ParkManager(mySchedule, myPollster, myParkList);
		//myManager.initialize();

		GregorianCalendar myStartDate = stringToCalendar("06052015");
		GregorianCalendar myEndDate = stringToCalendar("06062015");
		Job aJob = new Job(myPark, 1, 2, 3, myStartDate, myEndDate, myManager); //create a new job to test volunteer
		mySchedule.receiveJob(aJob);
		
		Volunteer theVol = new Volunteer("Arsh", "Singh", myPollster, mySchedule);
		theVol.initialize();
		*/
	
	

	
	
	/**
	 * Convert a date string to a Gregorian Calendar object.
	 * @param stringDate A string representing a date, of format mmddyyyy
	 * @return A Gregorian Calendar object representing that date.
	 */
	private static GregorianCalendar stringToCalendar(String stringDate) {
		int myDay = Integer.parseInt(stringDate.substring(0, 2));
		int myMonth = Integer.parseInt(stringDate.substring(2, 4));
		int myYear = Integer.parseInt(stringDate.substring(4, 8));		
		
		return new GregorianCalendar(myYear, myDay, myMonth);
	}
	
	public static void closeProgram() {
		System.exit(0);
	}
	
	
	//NOTE: when creating an account for volunteer, don't forget to add him into the myVolunteerList in JobList class 

}
