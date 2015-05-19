package model;

import javax.swing.JFrame;

import view.AdministratorGUI;
import view.ParkManagerGUI;

public class Main {
	public static MainUI UI;
	
	public static void main(String[] args) {
		
		SaveLoad saveManager = new SaveLoad();
		String[] userInfo;
		
		UI = new MainUI();
		
		UI.initialize();
		
		//We return to the top of the loop whenever the user logs out, and prompt them to login again.
		//The loop continues until the user selects Exit.
		while(true) {
			JobList jobList = saveManager.loadJobList();
			UserList userList = saveManager.loadUserList();
			Schedule schedule = new Schedule(jobList, userList);
			DataPollster pollster = new DataPollster(jobList, userList);
			userInfo = directLogin(schedule, pollster);
			
			//If the command or information entered was invalid, we try again.
			if(userInfo == null || schedule == null || pollster == null) {
				directLogin(schedule, pollster);
			}
			
			try {
				if(userInfo[0].equals("login")) {
					giveControl(userInfo, schedule, pollster);
				}
			} catch (NullPointerException e) {
				System.out.println("\nWe ran into a problem while logging you in. Please try again.");
				directLogin(schedule,pollster);
			}
			
			if(userInfo[0].equals("register")) {
				schedule.addUser(userInfo[1], userInfo[2], userInfo[3], userInfo[4]);
				giveControl(userInfo, schedule, pollster);
			}
			
			saveManager.saveJobList(jobList);
			saveManager.saveUserList(userList);
			
			UI.greetUser();
		}
		
	}
	
	/**
	 * Prompt the user to either login, register, or exit.<br>
	 * Then, ask the user for login or register details.
	 */
	private static String[] directLogin(Schedule theSchedule, DataPollster thePollster) {
		int loginCommand = UI.getLoginChoice();
		String[] userInfo = null;
		switch (loginCommand) {
			case 1: userInfo = loginUser(theSchedule, thePollster); break;
			case 2: userInfo = registerUser(theSchedule, thePollster); break;
			case 3: UI.displayExit(); closeProgram(); break; //Ends program.
			default: UI.displayInvalidChoice(); break;
		}
		return userInfo;
	}
	
	/*
	 * Return a String array that specifies the user as logging in, and the e-mail of the user.
	 */
	private static String[] loginUser(Schedule mySchedule, DataPollster myPollster) {
		
		String userEmail = UI.getReturnEmail();
		String[] userInfo = null;
		
		if(myPollster.checkEmail(userEmail)) {
			userInfo = new String[2];
			userInfo[0] = "login";
			userInfo[1] = userEmail;
			
		} else {
			UI.displayInvalidEmail();
		}
		return userInfo;
	}
	
	/*
	 * Return a String array that specifies the user as registering, along with info on that user.
	 */
	private static String[] registerUser(Schedule mySchedule, DataPollster myPollster) {
		String[] userInfo = new String[5];
		
		userInfo[0] = "register";
		userInfo[1] = UI.getNewEmail();
		userInfo[2] = UI.getFirstName();
		userInfo[3] = UI.getLastName();
		userInfo[4] = UI.getUserType();
		
		if(checkDuplicate(myPollster, userInfo[1])) {
			userInfo = null;
			UI.displayDuplicateError();
		}

		if(userInfo == null || userInfo[4] == null) userInfo = null;
		return userInfo;
	}
	
	/*
	 * Check if the email is already being used. If so, return true. If not, return false.
	 */
	private static boolean checkDuplicate(DataPollster thePollster, String theEmail) {
		boolean status = false;
		
		for (User user : thePollster.getAllUserList()) {
			if (user.getEmail().equals(theEmail)) {
				status = true;
			}
		}
		
		return status;
	}
	
	/**
	 * Transfer control to the user, specified by their e-mail address.
	 */
	private static void giveControl(String[] theUserInfo, Schedule theSchedule, DataPollster thePollster) {
		String userType = thePollster.getUserType(theUserInfo[1]);
		String email = theUserInfo[1];
		
		if(userType.equals("ParkManager")) {
			ParkManager manager = thePollster.getParkManager(email);
			manager.initialize(theSchedule, thePollster);
			ParkManagerGUI managerGUI = new ParkManagerGUI(manager);
			managerGUI.setVisible(true);
			
			stallMainLoop(managerGUI);
		}
		
		// These need to be changed with GUI object instantiations - need to call diff constructors!
		
		if(userType.equals("Volunteer")) {
//			Volunteer volunteer = new Volunteer(theSchedule, thePollster, email);
//			volunteer.initialize();
		}
		
		if(userType.equals("Administrator")) {
			Administrator admin = thePollster.getAdministrator(email);
			admin.initialize(theSchedule, thePollster);
			AdministratorGUI adminGUI = new AdministratorGUI(admin);
			adminGUI.setVisible(true);
			
			stallMainLoop(adminGUI);
		}
	}
	
	private static void stallMainLoop(JFrame theGUI) {
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!theGUI.isDisplayable()) {
				return;
			} 
		}
	}
	
	/**
	 * Breaks out of infinite loop in main method.
	 */
	 private static void closeProgram() {
		System.exit(0);
	}
}
