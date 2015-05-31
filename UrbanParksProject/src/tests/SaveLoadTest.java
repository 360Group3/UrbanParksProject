package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.Job;
import model.JobList;
import model.ParkManager;
import model.SaveLoad;
import model.User;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

public class SaveLoadTest {

	public JobList myJobList;
	public UserList myUserList;
	public SaveLoad mySaveLoader;
	
	/**
	 * Before each test, we reset the JobList and UserList, and then add various data to them.
	 */
	@Before
	public void setUp() throws Exception {
		//Reset the lists, SaveLoader, and the .ser files.
		myJobList = new JobList();
		myUserList = new UserList();
		mySaveLoader = new SaveLoad();
		
		mySaveLoader.saveJobList(myJobList, "testJobList.ser");
		mySaveLoader.saveUserList(myUserList, "testUserList.ser");
		
		//Fill the Job List with several Jobs.
		ArrayList<ArrayList<String>> volunteerArray = new ArrayList<ArrayList<String>>();
		
		Job job1 = new Job(0, "Test Park", 5, 5, 5, "06202015", "06202015", "testmanager@gmail.com", volunteerArray);
		Job job2 = new Job(1, "Test Park 2", 15, 15, 15, "06222015", "06222015", "testmanager2@gmail.com", volunteerArray);
		Job job3 = new Job(2, "Test Park 2", 25, 25, 25, "06232015", "06242015", "testmanager2@gmail.com", volunteerArray);
		Job job4 = new Job(3, "Test Park 3", 35, 35, 35, "06272015", "06272015", "testmanager3@gmail.com", volunteerArray);
		
		List<Job> listOfJobs = new ArrayList<Job>();
		listOfJobs.add(job1);
		listOfJobs.add(job2);
		listOfJobs.add(job3);
		listOfJobs.add(job4);
		
		myJobList.setJobList(listOfJobs);
		
		//Fill the User List with several different types of Users.
		User volunteer1 = new Volunteer("testvolunteer@gmail.com", "Test1", "Volunteer1");
		User volunteer2 = new Volunteer("testvolunteer2@gmail.com", "Test2", "Volunteer2");
		
		User manager1 = new ParkManager("testmanager@gmail.com", "Test3", "Manager3", new ArrayList<String>());
		User manager2 = new ParkManager("testmanager2@gmail.com", "Test4", "Manager4", new ArrayList<String>());
		
		User admin1 = new Administrator("testadministrator@gmail.com", "Test5", "Administrator5");
		
		myUserList.addNewUser(volunteer1);
		myUserList.addNewUser(volunteer2);
		myUserList.addNewUser(manager1);
		myUserList.addNewUser(manager2);
		myUserList.addNewUser(admin1);		
	}

	/**
	 * Test that we can successfully save and load Job Lists.
	 * We do this by saving the Job List we created in setup, then loading it, and testing to see if
	 * the data has persisted.
	 */
	@Test
	public void testSaveLoadJobList() {
		//Save myJobList into testJobList.ser
		mySaveLoader.saveJobList(myJobList, "testJobList.ser");	
		
		//Load testJobList.ser into loadJobList
		JobList loadJobList = mySaveLoader.loadJobList("testJobList.ser");
		
		//Test to see if loadJobList's contents are the same as myJobList
		assertEquals(loadJobList.getNumberOfJobs(), 4);
		assertEquals(loadJobList.getJobList().get(0).getJobID(), 0);
		assertEquals(loadJobList.getJobList().get(1).getLightMax(), 15);
		assertEquals(loadJobList.getJobList().get(2).getPark(), "Test Park 2");
		assertEquals(loadJobList.getJobList().get(3).getManager(), "testmanager3@gmail.com");
	}

	/**
	 * Test that we can successfully save and load User Lists.
	 * We do this by saving the User List we created in setup, then loading it, and testing to see if
	 * the data has persisted.
	 */
	@Test
	public void testSaveLoadUserList() {
		//Save myUserList into testUserList.ser
		mySaveLoader.saveUserList(myUserList, "testUserList.ser");
		
		//Load testUserList.ser into loadUserList
		UserList loadUserList = mySaveLoader.loadUserList("testUserList.ser");
		
		//Test to see if loadUserList's contents are the same as myUserList
		assertEquals(loadUserList.getUserListCopy().size(), 5);
		assertEquals(loadUserList.getVolunteerListCopy().size(), 2);
		assertEquals(loadUserList.getVolunteerListCopy().get(0).getEmail(), "testvolunteer@gmail.com");
		assertEquals(loadUserList.getVolunteerListCopy().get(1).getFirstName(), "Test2");
		assertEquals(loadUserList.getParkManagerListCopy().get(0).getEmail(), "testmanager@gmail.com");
		assertEquals(loadUserList.getAdministratorListCopy().get(0).getFirstName(), "Test5");
	}

}
