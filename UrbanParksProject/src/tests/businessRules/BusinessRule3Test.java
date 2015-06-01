package tests.businessRules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.UserList;
import model.Volunteer;
import model.businessRules.BusinessRule3;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for business rule 3
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule3Test {
	BusinessRule3 br3;
	
	Volunteer Naruto;
	
	JobList myJobList;
	UserList myUserList;
	
	Job defeatFrieza;
	
	@Before
	public void setUp() {
	    br3 = new BusinessRule3();

	    
		defeatFrieza = new Job(0, "Namek", 0, 1, 5,
				"07122015", "07122015", "ten@uw.edu", new ArrayList<List<String>>());
	}	
	
	
	/**
	 * Add a volunteer to a job's grade which has no positions.
	 */
	@Test
	public void testTestOnJobGradeWithNoPositions() {
		assertFalse(br3.test(defeatFrieza, "Light"));
	}
	
	
	/**
	 * Adding a volunteer to a job with an open medium portion.
	 */
	@Test
	public void testTestOnJobGradeWithOpenPositions() {
		assertTrue(br3.test(defeatFrieza, "Medium"));
	}
}
