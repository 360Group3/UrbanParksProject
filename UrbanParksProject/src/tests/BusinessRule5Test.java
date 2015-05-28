package tests;

import static org.junit.Assert.assertEquals;

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
	public void setup() throws Exception {
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
	public void JobTimeTest() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "06122015", "06122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		assertEquals( Tenenberg.addJob(defeatFrieza),true);
	}
	
	/**
	 * This test creates a job that is way in the past.
	 */
	@Test
	public void JobTimeTest2() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "03122015", "03122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());

		assertEquals(new BusinessRule5().pastTest(defeatFrieza), false);
		
	}
	
	/**
	 * This test creates a job that is more than 3 months into the future.
	 */
	@Test
	public void JobTimeTest3() {
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "10122015", "10122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());

		assertEquals(new BusinessRule5().futureTest(defeatFrieza), false);
	}
	
}
