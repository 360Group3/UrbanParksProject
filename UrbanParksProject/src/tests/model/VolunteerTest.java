package tests.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

/** This is the test class for the volunteer.
 * @author Arshdeep Singh
 *
 */
public class VolunteerTest {

	Volunteer Arsh;
	Volunteer Goku;
	Volunteer Yugi;
	Volunteer Naruto;
	Volunteer Ash;
	
	ParkManager Tenenberg;
	
	UserList myUserList;
	JobList myJobList;
	
	@Before
	public void setUp() {
		myUserList = new UserList();
		myJobList = new JobList();
		Schedule.getInstance().setJobList(myJobList);
		Schedule.getInstance().setUserList(myUserList);
		DataPollster.getInstance().setJobList(myJobList);
		DataPollster.getInstance().setUserList(myUserList);
		
		
		Arsh = new Volunteer("Arsh@yahoo.com", "Arsh", "Singh");
		Goku = new Volunteer("Goku@yahoo.com", "Goku", "Son");
		Yugi = new Volunteer("Yugi@yahoo.com", "Yugi", "Muto");
		Naruto = new Volunteer("Naruto@yahoo.com", "Naruto", "Uzamaki");
		Ash = new Volunteer("Ash@yahoo.com", "Ash", "Ketchum");
		myUserList.addNewUser(Arsh);
		myUserList.addNewUser(Goku);
		myUserList.addNewUser(Yugi);
		myUserList.addNewUser(Naruto);
		myUserList.addNewUser(Ash);		
		
		
		List<String> parkList = new ArrayList<String>();
		parkList.add("Namek");
		parkList.add("Konoha");
		parkList.add("Kento");
		Tenenberg = new ParkManager("ten@uw.edu", "Mr", "Teacher", parkList);
		myUserList.addNewUser(Tenenberg);
		Job defeatFrieza = new Job(0, "Namek", 0, 1, 5, "06122015", "06122015", "ten@uw.edu", 
				new ArrayList<List<String>>());
		Job findSasuke = new Job(1, "Konoha", 2, 2, 0, "06032015", "06032015", "ten@uw.edu", 
				new ArrayList<List<String>>());
		
		Job tradeForBlueEyesWhiteDragon = new Job(2, "Egypt", 0, 1, 0, "06112015", "06122015", 
				"ten@uw.edu", new ArrayList<List<String>>());
		
		
		//This part is necessary to create a job for todays date.
		Calendar now = new GregorianCalendar();
		//now.get(5) is the day
		//now.get(4) is the month
		//now.get(1) is the year
		String todaysDate0 = now.get(4) + "" + now.get(5) + "" + now.get(1);
		
		String todaysDate1 = now.get(4) + "0" + now.get(5) + "" + now.get(1); //bad day
		
		String todaysDate2 = "0" + now.get(4) + "" + now.get(5) + "" + now.get(1); //bad month
		
		String todaysDate3 = "0" + now.get(4) + "0" + now.get(5) + "" + now.get(1); //bad both
		
		Job catchMew;
		if (now.get(4) >= 10 && now.get(5) >= 10) { //everything good
			//System.out.println(todaysDate0);
			catchMew = new Job(3, "Kento", 0, 5, 0, todaysDate0, todaysDate0, "ten@uw.edu", new ArrayList<List<String>>());
		} else if (now.get(4) >= 10 && now.get(5) < 10) { //bad day
			//System.out.println(todaysDate1);
			catchMew = new Job(3, "Kento", 0, 5, 0, todaysDate1, todaysDate1, "ten@uw.edu", new ArrayList<List<String>>());
		} else if (now.get(4) < 10 && now.get(5) >= 10) { //bad month
			//System.out.println(todaysDate2);
			catchMew = new Job(3, "Kento", 0, 5, 0, todaysDate2, todaysDate2, "ten@uw.edu", new ArrayList<List<String>>());
		} else { //bad both
			//System.out.println(todaysDate3);
			catchMew = new Job(3, "Kento", 0, 5, 0, todaysDate3, todaysDate3, "ten@uw.edu", new ArrayList<List<String>>());
		}
		
		
		
		Schedule.getInstance().receiveJob(defeatFrieza);
		Schedule.getInstance().receiveJob(findSasuke);
		Schedule.getInstance().receiveJob(catchMew);
		Schedule.getInstance().receiveJob(tradeForBlueEyesWhiteDragon);
		
		
		//System.out.println("There are " + Schedule.getInstance().getJobList().getNumberOfJobs() + " jobs.");
		
	}
	
	/**
	 * Add a volunteer to a job's grade which has no positions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSignUpOnJobGradeWithNoOpenPositions() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Light");
		
		Naruto.signUpForJob(theVol, 0);
	}
	
	/**
	 * Adding a volunteer to a job with an open medium portion.
	 */
	@Test
	public void testSignUpOnJobGradeWithOpenPositions() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Medium");
		
		assertEquals(Naruto.signUpForJob(theVol, 0), true);
	}
	
	/**
	 * Adding 1 volunteer to the medium portion (max 1) of a job.
	 * And then adding 1 volunteer to that same medium portion even though
	 * the max for that portion is 1.
	 * This should assert false b/c you cannot add to a full portion.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSignUpOnVolSignUpSuccessThenVolSignUpFailure() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Medium");
		Naruto.signUpForJob(theVol, 0);
		
		ArrayList<String> theVol2 = new ArrayList<String>();
		theVol2.add("Ash@yahoo.com");
		theVol2.add("Medium");	
		Ash.signUpForJob(theVol2, 0);
	}
	
	/**
	 * Adding 1 volunteer to the medium portion (max 1) of a job.
	 * And then adding 1 volunteer to the heavy portion (max 5) of that job.
	 */
	@Test
	public void testSignUpOnTwoVolsSignedUpForDifferentGradesButBothSuccessful() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Medium");
		Naruto.signUpForJob(theVol, 0);
		
		ArrayList<String> theVol3 = new ArrayList<String>();
		theVol3.add("Goku@yahoo.com");
		theVol3.add("Heavy");
		assertEquals(Goku.signUpForJob(theVol3, 0), true);
	}
	
	/**
	 * Adding 2 volunteers to the medium portion (max 2) of a job.
	 * And then adding 1 volunteer to the light portion (max 2) of that job.
	 */
	@Test
	public void testSignUpOnAddingVolsToMaxMedGradeThenAddingOneVolToLight() {
		ArrayList<String> theVol = new ArrayList<String>();
		theVol.add("Naruto@yahoo.com");
		theVol.add("Medium");
		Naruto.signUpForJob(theVol, 1);
		
		ArrayList<String> theVol2 = new ArrayList<String>();
		theVol2.add("Ash@yahoo.com");
		theVol2.add("Medium");
		Ash.signUpForJob(theVol2, 1);
		
		
		ArrayList<String> theVol3 = new ArrayList<String>();
		theVol3.add("Goku@yahoo.com");
		theVol3.add("Light");
		assertEquals(Goku.signUpForJob(theVol3, 1), true);
	}
		
	/**
	 * Adding volunteer to a job that does not exist.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSignUpOnNonexistentJob() {
		ArrayList<String> theVol3 = new ArrayList<String>();
		theVol3.add("Goku@yahoo.com");
		theVol3.add("Light");
		Goku.signUpForJob(theVol3, 279);
	}
	
	/**
	 * Adding volunteer to a job in the past.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSignUpOnPastJob() {
		JobList testJobList = new JobList();

		Job job1 = new Job(5, "dud", 0, 0, 1, "04122015", "04122015", "ten@uw.edu", new ArrayList<List<String>>());

		List<Job> myList = new ArrayList<Job>();
		myList.add(job1);

		testJobList.setJobList(myList);
		Schedule.getInstance().setJobList(testJobList);
		DataPollster.getInstance().setJobList(testJobList) ;
		
		
		ArrayList<String> theVol5 = new ArrayList<String>();
		theVol5.add("Arsh@yahoo.com");
		theVol5.add("Heavy");
		Arsh.signUpForJob(theVol5, 5);
	}
	
	/**
	 * Adding a volunteer to a job on a day that he is already working.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSignUpOnNewJobOnSameDayAsJobAlreadyWorking() {
		ArrayList<String> theVol4 = new ArrayList<String>();
		theVol4.add("Yugi@yahoo.com");
		theVol4.add("Medium");
		
		Yugi.signUpForJob(theVol4, 0);
		Yugi.signUpForJob(theVol4, 2);
	}
	
	/**
	 * Get an empty list of the jobs a volunteer has signed up for.
	 * (volunteer has so far signed up for no jobs).
	 */
	@Test
	public void testGetMyJobsOnEmptyList() {
		List<Job> emptylist = new ArrayList<Job>();
		assertEquals(Arsh.getMyJobs(), emptylist);
	}
	
	/**
	 * Get a list of the jobs a volunteer has signed up for.
	 * (volunteer will sign up for 1 job so the 
	 * size of the list should be 1).
	 */
	@Test
	public void testGetMyJobsOnListWithOneJob() {
		ArrayList<String> theVol5 = new ArrayList<String>();
		theVol5.add("Arsh@yahoo.com");
		theVol5.add("Medium");
		Arsh.signUpForJob(theVol5, 1);
		assertEquals(Arsh.getMyJobs().size(), 1);
	}
	
	/**
	 * Get a list of the jobs a volunteer has signed up for.
	 * (volunteer will sign up for 2 jobs so the 
	 * size of the list should be 2).
	 */
	@Test
	public void testGetMyJobsOnListWithManyJobs() {
		ArrayList<String> theVol5 = new ArrayList<String>();
		theVol5.add("Arsh@yahoo.com");
		theVol5.add("Medium");
		
		Arsh.signUpForJob(theVol5, 0);
		Arsh.signUpForJob(theVol5, 1);
		assertEquals(Arsh.getMyJobs().size(), 2);
	}
}