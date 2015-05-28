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
import model.businessRules.BusinessRule2;
import model.businessRules.BusinessRule8;

/**
 * This class tests the eighth business rule.
 * @author Arsh
 *
 */
public class BusinessRule8Test {
	ParkManager Tenenberg;
	List<String> parkList = new ArrayList<String>();
	
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
		
		
		
		parkList.add("Namek");
		parkList.add("Konoha");
		parkList.add("Kento");
		parkList.add("Egypt");
		Tenenberg = new ParkManager("ten@uw.edu", "Mr", "Teacher", parkList);
		myUserList.addNewUser(Tenenberg);
		

	}
	
	
	/**
	 * This tests whether a job can be created with a good park.
	 */
	@Test
	public void useParkTest() {
		Job findSasuke = new Job(1, "Konoha", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		assertEquals(new BusinessRule8().test(findSasuke, parkList), true);
	}
	
	
	/**
	 * This tests whether a job can be created with a bad park.
	 */
	@Test
	public void useParkTest2() {
		Job findSasuke = new Job(1, "Seattle Park", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		assertEquals(new BusinessRule8().test(findSasuke, parkList), false);
		
	}
	
}
