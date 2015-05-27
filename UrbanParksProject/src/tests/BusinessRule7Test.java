package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test for business rule 7.
 * @author Arshdeep Singh
 *
 */
public class BusinessRule7Test {
	
	Volunteer Yugi;
	
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
	
		Yugi = new Volunteer("Yugi@yahoo.com", "Yugi", "Muto");
		
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "06122015", "06122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		Job tradeForBlueEyesWhiteDragon = new Job(1, "Egypt", 0, 1, 0, "06112015", "06122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		Schedule.getInstance().receiveJob(defeatFrieza);
		Schedule.getInstance().receiveJob(tradeForBlueEyesWhiteDragon);
	}
	

	/**
	 * Adding a volunteer to a job on a day that he is already working.
	 */
	@Test
	public void signUpTEST8() {
		ArrayList<String> theVol4 = new ArrayList<String>();
		theVol4.add("Yugi@yahoo.com");
		theVol4.add("Medium");
		
		Yugi.signUp(theVol4, 0);
		assertEquals(Yugi.signUp(theVol4, 1), false);
	}
}
