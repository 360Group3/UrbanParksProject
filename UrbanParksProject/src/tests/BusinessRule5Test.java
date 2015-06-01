package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;
import model.businessRules.BusinessRule5;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test class for the fifth business rule.
 * @author Arsh 
 *
 */
public class BusinessRule5Test {
	
	ParkManager Tenenberg;
	UserList myUserList;
	JobList myJobList;
	
	@Before
	public void setup() {
		myUserList = new UserList();
		myJobList = new JobList();
		Schedule.getInstance().setJobList(myJobList);
		Schedule.getInstance().setUserList(myUserList);
		DataPollster.getInstance().setJobList(myJobList);
		DataPollster.getInstance().setUserList(myUserList);
		
		
		List<String> parkList = new ArrayList<String>();
		parkList.add("Namek");
		parkList.add("Konoha");
		parkList.add("Kento");
		Tenenberg = new ParkManager("ten@uw.edu", "Mr", "Teacher", parkList);
		myUserList.addNewUser(Tenenberg);
	}
	
	/**
	 * This test creates a job that is within the proper time frame.
	 */
	@Test
	public void testPastTestOnCorrectTimeframe() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "07122015", "07122015", "ten@uw.edu", 
				new ArrayList<List<String>>());
		assertTrue(new BusinessRule5().pastTest(defeatFrieza));
	}
	
	/**
	 * This test creates a job that is within the proper time frame.
	 */
	@Test
	public void testFutureTestOnCorrectTimeframe() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "07122015", "07122015", "ten@uw.edu", 
				new ArrayList<List<String>>());
		assertTrue(new BusinessRule5().futureTest(defeatFrieza));
	}
	
	/**
	 * This test creates a job that is way in the past.
	 */
	@Test
	public void testTestForPastJob() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "03122015", "03122015", "ten@uw.edu", 
			new ArrayList<List<String>>());
		assertFalse(new BusinessRule5().pastTest(defeatFrieza));
		
	}
	
	/**
	 * This test creates a job that is more than 3 months into the future.
	 */
	@Test
	public void testFutureTestOnFarFutureJob() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "10122015", "10122015", "ten@uw.edu", 
				new ArrayList<List<String>>());
		assertFalse(new BusinessRule5().futureTest(defeatFrieza));
	}	
}
