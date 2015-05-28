package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;

/**
 * This class tests the eighth business rule.
 * @author Arsh
 *
 */
public class BusinessRule8Test {
	ParkManager Tenenberg;
	
	UserList myUserList;
	JobList myJobList;
	
	@Before
	public void setUp() throws Exception {
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
		parkList.add("Egypt");
		Tenenberg = new ParkManager("ten@uw.edu", "Mr", "Teacher", parkList);
		myUserList.addNewParkManager(Tenenberg);
		

	}
	
	
	/**
	 * This tests whether a job can be created with a good park.
	 */
	@Test
	public void useParkTest() {
		Job findSasuke = new Job(1, "Konoha", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		assertEquals(Schedule.getInstance().receiveJob(findSasuke), true);
	}
	
	
	/**
	 * This tests whether a job can be created with a bad park.
	 */
	@Test
	public void useParkTest2() {
		Job findSasuke = new Job(1, "Seattle Park", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		String ex = "The park for this job is not in your"
				+ "list of managed parks.";
		try {
			Schedule.getInstance().receiveJob(findSasuke);
		} catch (Exception e) {
			assertEquals(ex, e.getMessage());
		}
	}
	
}
