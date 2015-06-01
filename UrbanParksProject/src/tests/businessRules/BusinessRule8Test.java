package tests.businessRules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.businessRules.BusinessRule8;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the eighth business rule.
 * @author Arsh
 *
 */
public class BusinessRule8Test {
	
    BusinessRule8 br8;
    
    List<String> parkList;
    
	@Before
	public void setUp() {
		br8 = new BusinessRule8();
		
		parkList = new ArrayList<String>();
		parkList.add("Namek");
		parkList.add("Konoha");
		parkList.add("Kento");
		parkList.add("Egypt");
	}
	
	/**
	 * This tests whether a job can be created with a Job at a Park that already
	 * exists in the system.
	 */
	@Test
	public void testTestOnJobAtParkThatAlreadyExists() {
		Job findSasuke = new Job(1, "Konoha", 2, 2, 0, "07032015", "07032015", "ten@uw.edu", new ArrayList<>());

		assertTrue(br8.test(findSasuke, parkList));
	}
	
	/**
	 * This tests whether a job can be created with a Job at a Park that doesn't
	 * exist in the system.
	 */
	@Test
	public void testParkForJobAtParkThatDoesNotAlreadyExist() {
		Job findSasuke = new Job(1, "Seattle Park", 2, 2, 0, "07032015", "07032015", "ten@uw.edu", new ArrayList<>());

		assertFalse(br8.test(findSasuke, parkList));
	}
}
