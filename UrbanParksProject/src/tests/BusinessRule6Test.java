package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test for the sixth business rule
 * @author Arshdeep Singh
 *
 */
public class BusinessRule6Test {
	Volunteer Arsh;
	
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
		
		
		Arsh = new Volunteer("Arsh@yahoo.com", "Arsh", "Singh");
	}

	
	/**
	 * Adding volunteer to a job in the past
	 */
	@Test
	public void signUpTEST() {
		JobList testJobList = new JobList();

		Job job1 = new Job(5, "dud", 0, 0, 1, "04122015", "04122015", "ten@uw.edu", new ArrayList<ArrayList<String>>());

		List<Job> myList = new ArrayList<Job>();
		myList.add(job1);

		testJobList.setJobList(myList);
		Schedule.getInstance().setJobList(testJobList);
		DataPollster.getInstance().setJobList(testJobList) ;
		
		ArrayList<String> theVol5 = new ArrayList<String>();
		theVol5.add("Arsh@yahoo.com");
		theVol5.add("Heavy");
		
		String ex = "Sorry, but this job has already completed.";
		String e2 = "";
		try {
			Schedule.getInstance().addVolunteerToJob(theVol5, 5);
		} catch (IllegalArgumentException e) {
			e2 += e.getMessage();
			
		}
		
		assertEquals(ex, e2);
	}

}
