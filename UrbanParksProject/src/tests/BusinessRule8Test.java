package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.ParkManager;
import model.UserList;
import model.businessRules.BusinessRule8;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the eighth business rule.
 * @author Arsh
 *
 */
public class BusinessRule8Test {
	ParkManager Tenenberg;
	
	UserList myUserList;
	JobList myJobList;
	
    BusinessRule8 br8;
    
    List<String> parkList;
    
	@Before
	public void setUp() throws Exception {
		br8 = new BusinessRule8();
		
		parkList = new ArrayList<String>();
		parkList.add("Namek");
		parkList.add("Konoha");
		parkList.add("Kento");
		parkList.add("Egypt");
	}
	
	/**
	 * This tests whether a job can be created with a good park.
	 */
	@Test
	public void testParkForGoodPark() {
		Job findSasuke = new Job(1, "Konoha", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		assertTrue(br8.test(findSasuke, parkList));
	}
	
	
	/**
	 * This tests whether a job can be created with a bad park.
	 */
	@Test
	public void testParkForBadPark() {
		Job findSasuke = new Job(1, "Seattle Park", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", new ArrayList<ArrayList<String>>());

		assertFalse(br8.test(findSasuke, parkList));
	}
	
}
