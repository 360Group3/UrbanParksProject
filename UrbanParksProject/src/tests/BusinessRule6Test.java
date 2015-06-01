package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.Job;
import model.businessRules.BusinessRule6;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test for the sixth business rule
 * @author Arshdeep Singh
 *
 */
public class BusinessRule6Test {
	BusinessRule6 br6;
	
	@Before
	public void setUp() throws Exception {
	    br6 = new BusinessRule6();
	}

    /**
     * Adding volunteer to a job in the future.
     */
    @Test
    public void testSignUpForFutureJob() {
        Job job1 = new Job(5, "dud", 0, 0, 1, "04123015", "04123015", "ten@uw.edu", new ArrayList<>());
        
        assertTrue(br6.test(job1));
    }	
	
	/**
	 * Adding volunteer to a job in the past.
	 */
	@Test
	public void testSignUpForPastJob() {
		Job job1 = new Job(5, "dud", 0, 0, 1, "04122015", "04122015", "ten@uw.edu", new ArrayList<>());

		assertFalse(br6.test(job1));
	}

}
