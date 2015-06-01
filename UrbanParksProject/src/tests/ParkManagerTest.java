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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * A series of tests for ParkManager, with comprehensive coverage
 * over every implemented method.
 * @author Taylor Gorman
 * @version 29 May 2015
 *
 */
public class ParkManagerTest {

	//Class Variables that will be reused throughout several tests.
    JobList myJobList;
    UserList myUserList;
    ParkManager testManager;
    ArrayList<ArrayList<String>> volunteerList;

    /**
     * Called before every test. Here, we setup the JobList and User List, and then create test
     * parks, Jobs, Volunteers, and a test ParkManager. These are all added to the Job List
     * and User List to be used later.
     */
    @Before
    public void setUp() {
    	
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
        volunteer1Array.add("testvolunteer1@gmail.com");
        volunteer1Array.add("Light");

        ArrayList<String> volunteer2Array = new ArrayList<String>();
        volunteer2Array.add("testvolunteer2@gmail.com");
        volunteer2Array.add("Medium");

        ArrayList<String> volunteer3Array = new ArrayList<String>();
        volunteer3Array.add("testvolunteer3@gmail.com");
        volunteer3Array.add("Heavy");

        ArrayList<String> volunteer4Array = new ArrayList<String>();
        volunteer4Array.add("testvolunteer4@gmail.com");
        volunteer4Array.add("Heavy");

        //Create Test Jobs
        Job job1 = new Job(0, "Test Park 1", 5, 5, 5, "06102015", "06102015",
                        "testmanager@gmail.com", new ArrayList<ArrayList<String>>());
        Job job2 = new Job(1, "Test Park 2", 5, 5, 5, "06112015", "06112015",
                        "testmanager@gmail.com", new ArrayList<ArrayList<String>>());
        Job job3 = new Job(2, "Test Park 3", 5, 5, 5, "06122015", "06122015",
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
    	//Create an entirely new ParkManager from scratch.
        List<String> parkList = new ArrayList<String>();
        parkList.add("Constructor Test 1");
        parkList.add("Constructor Test 2");
        ParkManager constructorManager = new ParkManager("construct@gmail.com",
                                                         "Construct", "Manager",
                                                         parkList);

        //Show that we can get back all of the fields that we just set.
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
    public void testGetJobsOnDataFromSetup() {
    	//Show that we can recover the data from a Job we created in setup.
        List<Job> managerJobList = testManager.getJobs();
        assertEquals(managerJobList.size(), 3);
        assertEquals(managerJobList.get(0).getJobID(), 0);
        assertEquals(managerJobList.get(0).getVolunteerList().get(0).get(0),
                    "testvolunteer4@gmail.com");
    }
    
    /**
     * Test to ensure that a ParkManager can get all Jobs for Parks that they manage.
     */
    @Test
    public void testGetJobsOnNewJobAdded() {
    	List<Job> managerJobList = testManager.getJobs();
    	
    	//Show that we can create a new Job, and then get the details on that Job.
        Job newJob = new Job(3, "Test Park 1", 10, 10, 10, "06152015", "06152015",
                "testmanager@gmail.com", new ArrayList<ArrayList<String>>());
        Schedule.getInstance().receiveJob(newJob);
        
        ArrayList<String> volunteer4Array = new ArrayList<String>();
        volunteer4Array.add("testvolunteer4@gmail.com");
        volunteer4Array.add("Heavy");
        
        Schedule.getInstance().addVolunteerToJob(volunteer4Array, 3);
        
        managerJobList = testManager.getJobs();
        assertEquals(managerJobList.size(), 4);
        assertEquals(managerJobList.get(3).getJobID(), 3);
        assertEquals(managerJobList.get(3).getHeavyMax(), 10);
        assertEquals(managerJobList.get(3).getPark(), "Test Park 1");
    }

    /**
     * Test to ensure that a ParkManager can get all Jobs for Parks that they manage.
     */
    @Test
    public void testGetJobsOnNotGettingDetailsOfJobManagedByDifferentPM() {
    	List<Job> managerJobList = testManager.getJobs();
    	
    	ArrayList<String> volunteer4Array = new ArrayList<String>();
        volunteer4Array.add("testvolunteer4@gmail.com");
        volunteer4Array.add("Heavy");
        
        //Show that we can't get the details on a Job created in a Park that the ParkManager
        //doesn't manage.
        Job foreignJob = new Job(4, "Other Park", 15, 15, 15, "06202015", "06212015",
        		"othermanager@gmail.com", new ArrayList<ArrayList<String>>());
        Schedule.getInstance().receiveJob(foreignJob);        
        Schedule.getInstance().addVolunteerToJob(volunteer4Array, 4);
        
        managerJobList = testManager.getJobs();
        assertFalse(managerJobList.size() == 5);
        
        boolean jobFound = false;
        for(Job job : managerJobList) {
        	if(job.getJobID() == 4) jobFound = true;
        }
        assertFalse(jobFound);
    }
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    /**
     * Test to ensure that a ParkManager can add a Job to the JobList, and that invalid
     * Jobs are rejected.
     */
    public void testAddJobOnValidJob() {
    	volunteerList = new ArrayList<ArrayList<String>>();
        Job testJob = new Job(3, "Test Park 1", 5, 5, 5, "06252015", "06252015",
                "testmanager@gmail.com", volunteerList);
        testManager.addJob(testJob);

        //Show that we can recover various details from this newly added Job.
        assertEquals(DataPollster.getInstance().getJobCopy(3).getPark(), "Test Park 1");
        assertEquals(DataPollster.getInstance().getJobCopy(3).getManager(),
                     "testmanager@gmail.com");
    }
    
    /**
     * Test to ensure that a ParkManager can add a Job to the JobList, and that invalid
     * Jobs are rejected.
     */
    @Test
    public void testAddJobOnJobAtParkNotManagedByThisPM() {
        //Show that we cannot add a Job for a Park we do not manage.
        Job testJob2 = new Job(4, "Not My Park", 10, 10, 10, "06202015", "06202015",
        		"nottestmanager@gmail.com", volunteerList);
        
        exception.expect(IllegalArgumentException.class);
        testManager.addJob(testJob2);
    }

    /**
     * Test to ensure that a ParkManager can add a Job to the JobList, and that invalid
     * Jobs are rejected.
     */
    @Test
    public void testAddJobOnJobThatLastsMoreThanTwoDays() {
    	//Show that we cannot add a Job that lasts longer than two days.
        Job testJob3 = new Job(4, "Test Park 1", 10, 10, 10, "06202015", "06222015",
        		"testmanager@gmail.com", volunteerList);
        
        exception.expect(IllegalArgumentException.class);
        testManager.addJob(testJob3);
    }
    
    /**
     * Test to ensure that a ParkManager can add a Job to the JobList, and that invalid
     * Jobs are rejected.
     */
    @Test
    public void testAddJobOnJobInPast() {
    	//Show that we cannot add a Job that is in the past.
        Job testJob4 = new Job(4, "Test Park 1", 10, 10, 10, "06202014", "06202014",
        		"testmanager@gmail.com", volunteerList);
        
        exception.expect(IllegalArgumentException.class);
        testManager.addJob(testJob4);
    }
    
    /**
     * Tests to ensure that a ParkManager can acquire the correct JobID for a new job.
     */
    @Test
    public void testGetNewJobIDOnDataFromSetup() {
        assertEquals(testManager.getNewJobID(), 3);
    }
    
    /**
     * Tests to ensure that a ParkManager can acquire the correct JobID for a new job.
     */
    @Test
    public void testGetNewJobIDOnAddingNewJob() {
    	volunteerList = new ArrayList<ArrayList<String>>();
        Job testJob = new Job(3, "Test Park 1", 5, 5, 5, "06252015", "06252015",
                "testmanager@gmail.com", volunteerList);
        
      //Show that the Job ID increments only after the new job is added.
        testManager.addJob(testJob);
        assertEquals(testManager.getNewJobID(), 4);
    }
    
    /**
     * Test to ensure that a ParkManager can tell if it is the ParkManager of a Job.
     */
    @Test
    public void testIsManagerOfJobOnDataFromSetup() {
    	//Show that testManager is the ParkManager of previously added jobs.
        assertTrue(testManager.isManagerOfJob(0));
        assertTrue(testManager.isManagerOfJob(1));
    }
    
    /**
     * Test to ensure that a ParkManager can tell if it is the ParkManager of a Job.
     */
    @Test
    public void testIsManagerOfJobOnForeignPM() {
        //Show that isManagerOfJob() returns false when testManager isn't the ParkManager.
        List<String> parkArray = new ArrayList<String>();
        parkArray.add("Foreign Park");

        volunteerList = new ArrayList<ArrayList<String>>();
        Job testJob = new Job(4, "Other Test Park", 5, 5, 5, "06252015", "06252015",
                                "nottestmanager@gmail.com", volunteerList);
        Schedule.getInstance().receiveJob(testJob);

        assertFalse(testManager.isManagerOfJob(4));
    }

    /**
     * Test to ensure that a ParkManager can tell if it is the ParkManager of a Job.
     */
    @Test
    public void testIsManagerOfJobOnJobThatDoesNotExist() {
    	//Show that isManagerOfJob() still returns false for Jobs that do not exist.
        assertFalse(testManager.isManagerOfJob(5));
    }
    
    /**
     * Test to ensure that the ParkManager can return a list of the Parks that it is attached
     * to.
     */
    @Test
    public void testGetManagedParksOnDataFromSetup() {
    	//Shows that getManagedParks() works for all Parks that we have previously added
    	//for this ParkManager.
        assertEquals(testManager.getManagedParks().get(0), "Test Park 1");
        assertEquals(testManager.getManagedParks().get(1), "Test Park 2");
        assertTrue(testManager.getManagedParks().contains("Test Park 3"));
    }
    
    /**
     * Test to ensure that the ParkManager can return a list of the Parks that it is attached
     * to.
     */
    @Test
    public void testGetManagedParksOnParkForForeignPM() {
    	//Shows that getManagedParks() will not retrieve a Park that exists, but another
        //ParkManager manages.
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
    public void testSetManagedParksOnAddingAParkToThisPMsListOfManagedParks() {
    	//Shows that we still have a Park List size of 3.
        assertEquals(testManager.getManagedParks().size(), 3);

        //Add a new Park, call setManagedParks(), and shows that the Park List has been updated.
        List<String> parkList1 = new ArrayList<String>();
        parkList1.addAll(testManager.getManagedParks());
        parkList1.add("Our Park");
        testManager.setManagedParks(parkList1);
   
        assertEquals(testManager.getManagedParks().size(), 4);
        assertEquals(testManager.getManagedParks().get(3), "Our Park");
    }

    /**
     * Test to ensure that the ParkManager can update the Parks that it is attached to.
     */
    @Test
    public void testSetManagedParksOnAddingAParkToForeignPMsListOfManagedParks() {
    	//Show that if we call setManagedParks() on a ParkManager, it adds it to the Park
        //List of that ParkManager only.
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
    public void testGetJobVolunteerListOnAccessingVolDetailsForJobsAtThisPMsParks() {
    	//Show that the ParkManager can access various details of the Volunteers
    	//assigned to Jobs in Parks that they manage.
        assertEquals(testManager.getJobVolunteerList(0).size(), 2);
        assertEquals(testManager.getJobVolunteerList(0).get(0).getEmail(), "testvolunteer4@gmail.com");
        assertEquals(testManager.getJobVolunteerList(0).get(1).getFirstName(), "Test2");        
        assertEquals(testManager.getJobVolunteerList(2).size(), 1);
    }
    
    /**
     * Test to ensure that the ParkManager can get the List of Volunteers attached to a Job
     * with a Park that they manage.
     */
    @Test
    public void testGetJobVolunteerListOnAccessingVolDetailsForJobsAtForeignParks() {
    	//Show that the ParkManager cannot access any details about Volunteers assigned to
        //Jobs in Parks that they do not manage.
        Job foreignJob = new Job(3, "Other Park", 15, 15, 15, "06202015", "06212015",
        		"othermanager@gmail.com", new ArrayList<ArrayList<String>>());
        Schedule.getInstance().receiveJob(foreignJob);
        
        ArrayList<String> volunteer4Array = new ArrayList<String>();
        volunteer4Array.add("testvolunteer4@gmail.com");
        volunteer4Array.add("Heavy");        
        Schedule.getInstance().addVolunteerToJob(volunteer4Array, 3);
        
        assertTrue(testManager.getJobVolunteerList(3) == null); //Can't even see the list!
    }
}
