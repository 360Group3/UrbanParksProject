package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

/**
 * A unit testing class for the DataPollster model class.
 * 
 * @author Mike Overby
 * @author Reid Thompson - most recent implementation
 */
public class DataPollsterTest {

	private JobList jl;
	private UserList ul;
	private DataPollster dp;
	private Schedule s;
	private ArrayList<ArrayList<String>> volALofALs;
	private Volunteer vol1;
	private Volunteer vol2;
	private Volunteer vol3;
	private Volunteer vol4;
	private Volunteer vol5;
	private Volunteer vol6;
	private ArrayList<String> vol2AL;
	private Job job1;
	private Job job2;
	private Job job3;
	private Job job4;
	private ParkManager pm;
	private List<String> parks;
		
	/**
	 * Initialize all necessary fields for the following unit tests.
	 */
	@Before
	public void setUp() {		
		ul = new UserList();
		
		vol1 = new Volunteer("moverby@gmail.com", "Mike", "Overby");
		vol2 = new Volunteer("senorreido@gmail.com", "Senor", "Reido");	
		vol3 = new Volunteer("classmate1@gmail.com", "Class", "Mate1");
		vol4 = new Volunteer("refactoreverything@gmail.com", "Refactor", "Everything");
		vol5 = new Volunteer("classmate2@gmail.com", "Class", "Mate2");
		vol6 = new Volunteer("internetfail@gmail.com", "Internet", "Fail");
		
		vol2AL = new ArrayList<>();
		vol2AL.add(vol2.getEmail());
		vol2AL.add(vol2.getFirstName());
		vol2AL.add(vol2.getLastName());
		
		ul.addNewUser(vol1);
		ul.addNewUser(vol2);
		ul.addNewUser(vol3);
		ul.addNewUser(vol4);
		ul.addNewUser(vol5);
		ul.addNewUser(vol6);
		
		volALofALs = new ArrayList<>();
		ArrayList<String> temp = new ArrayList<>();
		
		temp.add(vol1.getEmail());
		temp.add("Light");
		volALofALs.add(temp);
		
		temp = new ArrayList<>();
		temp.add(vol2.getEmail());
		temp.add("Medium");
		volALofALs.add(temp);

		temp = new ArrayList<>();
		temp.add(vol3.getEmail());
		temp.add("Heavy");
		volALofALs.add(temp);

		temp = new ArrayList<>();
		temp.add(vol4.getEmail());
		temp.add("Light");
		volALofALs.add(temp);

		temp = new ArrayList<>();
		temp.add(vol5.getEmail());
		temp.add("Medium");
		volALofALs.add(temp);

		temp = new ArrayList<>();
		temp.add(vol6.getEmail());
		temp.add("Heavy");
		volALofALs.add(temp);
		
		String park = "Wright Park";
		
		jl = new JobList();
		List<Job> jBank = jl.getJobList();
		job1 = new Job(0, park, 5, 5, 5, "06152015", "06152015", "foo@gmail.com", 
				new ArrayList<>());
		job2 = new Job(1, park, 5, 5, 5,
		  		  "06162015", 
		  		  "06162015", 
		  		  "bar@gmail.com", new ArrayList<>());
		job3 = new Job(2, park, 5, 5, 5,
		  		  "06172015", 
		  		  "06172015", 
		  		  "buzz@gmail.com", new ArrayList<>());
		job4 = new Job(3, park, 5, 5, 5,
				  "06182015", 
				  "06182015", 
				  "bazz@gmail.com", new ArrayList<>());
		
		jBank.add(job1);
		jBank.add(job2);
		jBank.add(job3);
		jBank.add(job4);
		
		parks = new ArrayList<String>();
		parks.add("Tacoma Park");
		parks.add("Point Defiance Park");
		parks.add("New Park");
		parks.add("Wright Park");
		
		pm = new ParkManager("validpmemail@gmail.com", "Young", "Money", parks);
		ul.addNewUser(pm);
		
		jl.setJobList(jBank);
		dp = DataPollster.getInstance();
		dp.setJobList(jl);
		dp.setUserList(ul);
		s = Schedule.getInstance();
		s.setJobList(jl);
		s.setUserList(ul);
}

	/**
	 * Test method for {@link model.DataPollster#getPendingJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetPendingJobs() {
		List<Job> localJL = jl.getJobList();
		
		assertEquals("Invalid email shouldn't see any jobs.", new ArrayList<Job>(),
				dp.getPendingJobs("invalidemail@gmail.com"));
		
		// Mike should see all jobs
		assertEquals("Volunteer should see all jobs.", localJL, 
				dp.getPendingJobs(vol1.getEmail()));
		
		// Reid should see less jobs than Mike when adding him to more and more jobs
		for (int i = 0; i < jl.getNumberOfJobs(); i++) {
			localJL.get(i).addVolunteer(vol2AL);
			assertEquals("Reid should see size - 1 - i less jobs when added to i more jobs",
					localJL.size() - 1 - i, dp.getPendingJobs(vol2.getEmail()).size());
		}
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerJobs(model.Volunteer)}.
	 */
	@Test
	public void testGetVolunteerJobs() {
		List<Job> localJL = new ArrayList<Job>();
		
		assertEquals("Invalid email shouldn't get any jobs.", new ArrayList<Job>(),
				dp.getVolunteerJobs("invalidemail@gmail.com"));
		
		assertEquals("Mike's not signed up for any jobs.", new ArrayList<Job>(), 
				dp.getVolunteerJobs(vol1.getEmail()));
		
		assertEquals("Mike's not signed up for any jobs.", 0, 
				dp.getVolunteerJobs(vol1.getEmail()).size());
		
		job1.addVolunteer(vol2AL);
		localJL.add(job1);
		assertEquals("Reid's signed up for 1 job.", 1, dp.getVolunteerJobs(vol2.getEmail()).size());
		
		job2.addVolunteer(vol2AL);
		localJL.add(job2);
		assertEquals("Reid's signed up for 2 jobs.", 2, dp.getVolunteerJobs(vol2.getEmail()).size());
		
		job3.addVolunteer(vol2AL);
		localJL.add(job3);
		assertEquals("Reid's signed up for 3 jobs.", 3, dp.getVolunteerJobs(vol2.getEmail()).size());
		
		job4.addVolunteer(vol2AL);
		localJL.add(job4);
		assertEquals("Reid's signed up for 4 jobs.", 4, dp.getVolunteerJobs(vol2.getEmail()).size());
	}

	/**
	 * Test method for {@link model.DataPollster#getManagerJobs(model.ParkManager)}.
	 */
	@Test
	public void testGetManagerJobs() {
		ul.addNewUser(pm);
		
		dp.setUserList(ul);
		s.setUserList(ul);
		
		assertEquals("Not all jobs in pm's park were accounted for.", jl.getJobList(), 
				dp.getManagerJobs(pm.getEmail()));
	}

	/**
	 * Test method for {@link model.DataPollster#getJobVolunteerList(int)}.
	 */
	@Test
	public void testGetJobVolunteerList() {
		assertEquals("Problem given job with no Volunteers.", 0, 
				dp.getJobVolunteerList(job4.getJobID()).size());
		
		assertEquals("Invalid job ID has >0 sized Volunteer list.", 0, 
				dp.getJobVolunteerList(50).size());
				
		for (int i = 0; i < volALofALs.size(); i++) {
			job4.addVolunteer(volALofALs.get(i));
			assertEquals("Volunteer not correctly recognized as added to a Job.",
					i + 1, dp.getJobVolunteerList(job4.getJobID()).size());
		}		
	}

	/**
	 * Test method for {@link model.DataPollster#getNextJobID()}.
	 */
	@Test
	public void testGetNextJobID() {
		String park = "Woohoo Park";
		Job j4 = new Job(4, park, 5, 5, 5, "06012015", "06012015", "emailJ4@gmail.com",
				new ArrayList<>());
		Job j5 = new Job(5, park, 5, 5, 5, "06022015", "06022015", "emailJ5@gmail.com",
				new ArrayList<>());
		Job j6 = new Job(6, park, 5, 5, 5, "06032015", "06032015", "emailJ6@gmail.com",
				new ArrayList<>());
		Job j7 = new Job(7, park, 5, 5, 5, "06042015", "06042015", "emailJ7@gmail.com",
				new ArrayList<>());
		Job j8 = new Job(8, park, 5, 5, 5, "06052015", "06052015", "emailJ8@gmail.com",
				new ArrayList<>());
		Job j9 = new Job(9, park, 5, 5, 5, "06062015", "06062015", "emailJ9@gmail.com",
				new ArrayList<>());
		Job[] jobs = {j4, j5, j6, j7, j8, j9};
		List<Job> localJL = jl.getJobList();
		for (int i = 0; i < 6; i++) {
			localJL.add(jobs[i]);
			jl.setJobList(localJL);
			dp.setJobList(jl);
			assertEquals("Next job ID didn't increment properly.", i + 4, dp.getNextJobID() - 1);
		}
	}

	/**
	 * Test method for {@link model.DataPollster#checkEmail(String)}.
	 */
	@Test
	public void testCheckEmail() {
		assertTrue(dp.checkEmail(vol1.getEmail()));
		assertFalse(dp.checkEmail("randomvoidemail@gmail.com"));
	}

	/**
	 * Test method for {@link model.DataPollster#getVolunteerGrade(int, String)}.
	 */
	@Test
	public void testGetVolunteerGrade() {
		Job j = new Job(15, "Starbucks Park", 10, 10, 10, "07152015", "07152015", 
				"cookiemonster@sesame.com", volALofALs);
		
		List<Job> jobs = jl.getJobList();
		jobs.add(j);
		jl.setJobList(jobs);
		dp.setJobList(jl);
		
		assertEquals("Vol1 grade incorrect.", "Light", dp.getVolunteerGrade(j.getJobID(), 
				vol1.getEmail()));
		assertEquals("Vol2 grade incorrect.", "Medium", dp.getVolunteerGrade(j.getJobID(), 
				vol2.getEmail()));
		assertEquals("Vol3 grade incorrect.", "Heavy", dp.getVolunteerGrade(j.getJobID(), 
				vol3.getEmail()));
		assertEquals("Vol4 grade incorrect.", "Light", dp.getVolunteerGrade(j.getJobID(), 
				vol4.getEmail()));
		assertEquals("Vol5 grade incorrect.", "Medium", dp.getVolunteerGrade(j.getJobID(), 
				vol5.getEmail()));
		assertEquals("Vol6 grade incorrect.", "Heavy", dp.getVolunteerGrade(j.getJobID(), 
				vol6.getEmail()));
		
		// test that null is returned too
		assertEquals("Null wasn't returned.", null, dp.getVolunteerGrade(20, "browncow@farm.com"));
	}

	/**
	 * Test method for {@link model.DataPollster#getParkList(String)}.
	 */
	@Test
	public void testGetParkList() {
		ParkManager noParkPM = new ParkManager("jeffbezos@amazon.com", "Jeff", "Bezos",
				new ArrayList<>());
		assertEquals("PM shouldn't be associated with any parks.", 0, 
				dp.getManagerJobs(noParkPM.getEmail()).size());
		assertEquals("PM shouldn't be associated with any parks.", new ArrayList<>(), 
				dp.getManagerJobs(noParkPM.getEmail()));
		assertEquals("This PM should have 4 parks associated with him.", 4, 
				dp.getManagerJobs(pm.getEmail()).size());
	}
}
