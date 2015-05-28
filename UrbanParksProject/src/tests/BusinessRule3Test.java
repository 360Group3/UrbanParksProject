package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.Schedule;
import model.UserList;
import model.Volunteer;
import model.businessRules.BusinessRule3;
import model.businessRules.BusinessRule5;

import org.junit.Before;
import org.junit.Test;

/**Test for business rule 3
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule3Test {
	
	Volunteer Naruto;
	Job defeatFrieza;
	
	JobList myJobList;
	UserList myUserList;
	
	@Before
	public void setUp() throws Exception {
		myUserList = new UserList();
		myJobList = new JobList();
		
		Schedule.getInstance().setJobList(myJobList);
		Schedule.getInstance().setUserList(myUserList);
		DataPollster.getInstance().setJobList(myJobList);
		DataPollster.getInstance().setUserList(myUserList);
		
		Naruto = new Volunteer("Naruto@yahoo.com", "Naruto", "Uzamaki");
		myUserList.addNewVolunteer(Naruto);
		
		defeatFrieza = new Job(0, "Namek", 0, 1, 5,
				"06122015", "06122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());
		
		
	}
	
	
	
	/**Add a volunteer to a job's grade which has no positions.
	 *
	 */
	@Test
	public void signUpTEST1() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Light");
		
		assertEquals(new BusinessRule3().test(defeatFrieza, "Light"), false);
	}
	
	
	/**
	 * Adding a volunteer to a job with an open medium portion.
	 */
	@Test
	public void signUpTEST2() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Medium");
		
		
		assertEquals(new BusinessRule3().test(defeatFrieza, "Medium"), true);
		
	}
}
