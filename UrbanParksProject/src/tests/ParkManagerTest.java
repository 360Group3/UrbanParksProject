package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.DataPollster;
import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;

import org.junit.Before;
import org.junit.Test;

/**
 * A series of tests for ParkManager, with comprehensive coverage
 * over every implemented method.
 * @author Taylor Gorman
 * @version 29 May 2015
 *
 */
public class ParkManagerTest {

	//Class Variables that will be reused throughout several tests.
    static JobList myJobList;
    static UserList myUserList;
    static ParkManager testManager;
    static ArrayList<ArrayList<String>> volunteerList;

    /**
     * Called before every test. Here, we setup the JobList and User List, and then create test
     * parks, Jobs, Volunteers, and a test ParkManager. These are all added to the Job List
     * and User List to be used later.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	
    	//Create Lists and add them to Schedule and Pollster
        myJobList = new JobList();
        myUserList = new UserList();
        Schedule.getInstance().setJobList(myJobList);
        Schedule.getInstance().setUserList(myUserList);
        DataPollster.getInstance().setJobList(myJobList);
        DataPollster.getInstance().setUserList(myUserList);

        //Create Test Parks, along with a ParkManager assigned to those parks.
        List<String> parkList = new ArrayList<String>();
        parkList.add("Test Park 1");
        parkList.add("Test Park 2");
        parkList.add("Test Park 3");
        Schedule.getInstance().addUser("testmanager@gmail.com", "Test", "Manager",
                                        "ParkManager");
        Schedule.getInstance().updateParkList("testmanager@gmail.com", parkList);

        testManager = DataPollster.getInstance().getParkManager("testmanager@gmail.com");

        
        //Add Test Volunteers to the UserList
        Schedule.getInstance().addUser("testvolunteer1@gmail.com", "Test1", "Volunteer1",
                                        "Volunteer");
        Schedule.getInstance().addUser("testvolunteer2@gmail.com", "Test2", "Volunteer2",
                                        "Volunteer");
        Schedule.getInstance().addUser("testvolunteer3@gmail.com", "Test3", "Volunteer3",
                                        "Volunteer");
        Schedule.getInstance().addUser("testvolunteer4@gmail.com", "Test4", "Volunteer4",
                                        "Volunteer");
        Schedule.getInstance().addUser("testvolunteer5@gmail.com", "Test5", "Volunteer5",
                                        "Volunteer");

        //Create Volunteer Arrays to be added to Jobs
        ArrayList<String> volunteer1Array = new ArrayList<String>();
        volunteer1Array.add("testVolunteer1@gmail.com");
        volunteer1Array.add("Light");

        ArrayList<String> volunteer2Array = new ArrayList<String>();
        volunteer2Array.add("testVolunteer2@gmail.com");
        volunteer2Array.add("Medium");

        ArrayList<String> volunteer3Array = new ArrayList<String>();
        volunteer3Array.add("testVolunteer3@gmail.com");
        volunteer3Array.add("Heavy");

        ArrayList<String> volunteer4Array = new ArrayList<String>();
        volunteer4Array.add("testVolunteer4@gmail.com");
        volunteer4Array.add("Heavy");

        //Create Test Jobs
        Job job1 = new Job(0, "Test Park 1", 5, 5, 5, "06012015", "06012015",
                        "testmanager@gmail.com", new ArrayList<ArrayList<String>>());
        Job job2 = new Job(1, "Test Park 2", 5, 5, 5, "06032015", "06032015",
                        "testmanager@gmail.com", new ArrayList<ArrayList<String>>());
        Job job3 = new Job(2, "Test Park 3", 5, 5, 5, "06052015", "06052015",
                        "testmanager@gmail.com", new ArrayList<ArrayList<String>>());

        //Add the Test Jobs to the JobList, and then add the Volunteer arrays to those jobs.
        Schedule.getInstance().receiveJob(job1);
        Schedule.getInstance().receiveJob(job2);
        Schedule.getInstance().receiveJob(job3);
        Schedule.getInstance().addVolunteerToJob(volunteer4Array, 0);
        Schedule.getInstance().addVolunteerToJob(volunteer2Array, 0);
        Schedule.getInstance().addVolunteerToJob(volunteer1Array, 1);
        Schedule.getInstance().addVolunteerToJob(volunteer2Array, 1);
        Schedule.getInstance().addVolunteerToJob(volunteer3Array, 2);
    }
    
    
    
    /*
     * After setup(), we now have a UserList with a ParkManager (with 3 Parks) and 5 Volunteers.
     * We also have a JobList with 3 Jobs, all assigned to that ParkManager, with different
     * Volunteers signed up for them.
     */

    
    /**
     * Test to ensure that the construction of a new ParkManager is working as intended.
     */
    @Test
    public void testParkManager() {
        List<String> parkList = new ArrayList<String>();
        parkList.add("Constructor Test 1");
        parkList.add("Constructor Test 2");
        ParkManager constructorManager = new ParkManager("construct@gmail.com",
                                                         "Construct", "Manager",
                                                         parkList);

        assertEquals(constructorManager.getEmail(), "construct@gmail.com");
        assertEquals(constructorManager.getFirstName(), "Construct");
        assertEquals(constructorManager.getLastName(), "Manager");
        assertEquals(constructorManager.getManagedParks().get(0), "Constructor Test 1");
        assertEquals(constructorManager.getManagedParks().get(1), "Constructor Test 2");
    }

    /**
     * Test to ensure that a ParkManager can get all Jobs for Parks that they manage.
     */
    @Test
    public void testGetJobs() {
        List<Job> managerJobList = testManager.getJobs();

        assertEquals(managerJobList.size(), 3);
        assertEquals(managerJobList.get(0).getJobID(), 0);
        assertEquals(managerJobList.get(0).getVolunteerList().get(0).get(0),
                    "testVolunteer4@gmail.com");
    }

    
    /**
     * Test to ensure that a ParkManager can add a Job to the JobList, and that invalid
     * Jobs are rejected.
     */
    @Test
    public void testAddJob() {
        volunteerList = new ArrayList<ArrayList<String>>();
        Job testJob = new Job(3, "Test Park 4", 5, 5, 5, "06052015", "06052015",
                "testmanager@gmail.com", volunteerList);
        testManager.addJob(testJob);

        assertEquals(DataPollster.getInstance().getJobCopy(3).getPark(), "Test Park 4");
        assertEquals(DataPollster.getInstance().getJobCopy(3).getManager(),
                     "testmanager@gmail.com");
    }

    
    /**
     * Tests to ensure that a ParkManager can acquire the correct JobID for a new job.
     */
    @Test
    public void testGetNewJobID() {
        assertEquals(testManager.getNewJobID(), 3);

        volunteerList = new ArrayList<ArrayList<String>>();
        Job testJob = new Job(3, "Test Park 4", 5, 5, 5, "06052015", "06052015",
                "testmanager@gmail.com", volunteerList);
        testManager.addJob(testJob);

        assertEquals(testManager.getNewJobID(), 4);
    }

    
    /**
     * Test to ensure that a ParkManager can tell if it is the ParkManager of a Job.
     */
    @Test
    public void testIsManagerOfJob() {
        assertTrue(testManager.isManagerOfJob(0));
        assertTrue(testManager.isManagerOfJob(1));

        volunteerList = new ArrayList<ArrayList<String>>();
        Job testJob = new Job(3, "Test Park 4", 5, 5, 5, "06052015", "06052015",
                                "nottestmanager@gmail.com", volunteerList);
        testManager.addJob(testJob);

        assertFalse(testManager.isManagerOfJob(3));
    }

    
    /**
     * Test to ensure that the ParkManager can return a list of the Parks that it is attached
     * to.
     */
    @Test
    public void testGetManagedParks() {
        assertEquals(testManager.getManagedParks().get(0), "Test Park 1");
        assertEquals(testManager.getManagedParks().get(1), "Test Park 2");
        assertTrue(testManager.getManagedParks().contains("Test Park 3"));

        List<String> parkList = new ArrayList<String>();
        parkList.add("Not Our Park");
        Schedule.getInstance().addUser("nottestmanager@gmail.com", "Not", "Manager",
                                        "ParkManager");
        Schedule.getInstance().updateParkList("nottestmanager@gmail.com", parkList);

        assertTrue((DataPollster.getInstance().getParkManager("nottestmanager@gmail.com"))
                        .getManagedParks().contains("Not Our Park"));
        assertFalse(testManager.getManagedParks().contains("Not Our Park"));
    }

    
    /**
     * Test to ensure that the ParkManager can update the Parks that it is attached to.
     */
    @Test
    public void testSetManagedParks() {
        assertEquals(testManager.getManagedParks().size(), 3);

        List<String> parkList1 = new ArrayList<String>();
        parkList1.addAll(testManager.getManagedParks());
        parkList1.add("Our Park");
        testManager.setManagedParks(parkList1);

        assertEquals(testManager.getManagedParks().size(), 4);

        Schedule.getInstance().addUser("nottestmanager@gmail.com", "Not", "Manager",
                                        "ParkManager");

        List<String> parkList2 = new ArrayList<String>();
        parkList2.add("Not Our Park");
        DataPollster.getInstance().getParkManager("nottestmanager@gmail.com")
                        .setManagedParks(parkList2);

        assertTrue(DataPollster.getInstance().getParkManager("nottestmanager@gmail.com")
                        .getManagedParks().contains("Not Our Park"));
        assertFalse(DataPollster.getInstance().getParkManager("testmanager@gmail.com")
                        .getManagedParks().contains("Not Our Park"));
    }

    
    /**
     * Test to ensure that the ParkManager can get the List of Volunteers attached to a Job
     * with a Park that they manage.
     */
    @Test
    public void testGetJobVolunteerList() {
        assertEquals(testManager.getJobVolunteerList(0).size(), 2);
        assertEquals(testManager.getJobVolunteerList(2).size(), 1);
        assertEquals(testManager.getJobVolunteerList(3).size(), 0);
    }

}
