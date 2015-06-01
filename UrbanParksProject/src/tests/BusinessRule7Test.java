package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.Schedule;
import model.UserList;
import model.Volunteer;
import model.businessRules.BusinessRule7;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test for business rule 7.
 * @author Arshdeep Singh
 *
 */
public class BusinessRule7Test {
    BusinessRule7 br7;
	Volunteer Yugi;
	
	UserList myUserList;
	JobList myJobList;
	
	Job defeatFrieza;
	Job tradeForBlueEyesWhiteDragon;
	Job beatJadenYuki;
	
	@Before
	public void setUp() throws Exception {
	    br7 = new BusinessRule7();
	    
		myUserList = new UserList();
		myJobList = new JobList();
		Schedule.getInstance().setJobList(myJobList);
		Schedule.getInstance().setUserList(myUserList);
		DataPollster.getInstance().setJobList(myJobList);
		DataPollster.getInstance().setUserList(myUserList);
	
		Yugi = new Volunteer("Yugi@yahoo.com", "Yugi", "Muto");
		
		defeatFrieza = new Job(0, "Namek", 0, 1, 5, "06122015", "06122015", "ten@uw.edu", new ArrayList<>());
		tradeForBlueEyesWhiteDragon = new Job(1, "Egypt", 0, 1, 0, "06112015", "06122015", "ten@uw.edu", new ArrayList<>());
		beatJadenYuki = new Job(2, "Egypt", 0, 1, 0, "06152015", "06152015", "ten@uw.edu", new ArrayList<>());
        
		
		Schedule.getInstance().receiveJob(defeatFrieza);
		Schedule.getInstance().receiveJob(tradeForBlueEyesWhiteDragon);
        Schedule.getInstance().receiveJob(beatJadenYuki);
	}

	/**
     * Adding a volunteer to a day he is not working.
     */
    @Test
    public void testSignUpForValid() {
        ArrayList<String> theVol4 = new ArrayList<String>();
        theVol4.add("Yugi@yahoo.com");
        theVol4.add("Medium");
        
        Yugi.signUp(theVol4, 0);
        
        assertFalse(br7.test("Yugi@yahoo.com", beatJadenYuki, myJobList));
    }

	/**
	 * Adding a volunteer to a job on a day that he is already working.
	 */
	@Test
	public void testSignUpForAlreadyWorking() {
		ArrayList<String> theVol4 = new ArrayList<String>();
		theVol4.add("Yugi@yahoo.com");
		theVol4.add("Medium");
		
		Yugi.signUp(theVol4, 0);
		

        assertTrue(br7.test("Yugi@yahoo.com", tradeForBlueEyesWhiteDragon, myJobList));
	}
}
