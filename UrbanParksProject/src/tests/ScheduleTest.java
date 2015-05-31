package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.ParkManager;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

/**
 * A test class for Schedule in the model package.
 * 
 * @author Reid Thompson
 * @date 5.3.2015
 */
public class ScheduleTest {
    
    /**
     * Schedule object for testing.
     */
    private Schedule mySchedule;

    /**
     * Job object for testing.
     */
    private Job myJob;

    private String myVolEmail;

    /**
     * Instantiates necessary data for testing methods.
     */
    @Before
    public void setUp() {
        mySchedule = Schedule.getInstance();
        JobList myJobList = new JobList();
        UserList myUserList = new UserList();
        mySchedule.setJobList(myJobList);
        mySchedule.setUserList(myUserList);
        
        List<String> pList = new ArrayList<>();
        pList.add("Foo Park");

        myJob = new Job(55, "Foo Park", 10, 10, 10, "06172015", "06172015",
                        "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());


    }

    /**
     * Testing if a valid job can be received.
     */
    @Test
    public void testReceiveJobForValid() {
        mySchedule.receiveJob(myJob);
        assertFalse("No job was added.", mySchedule.getJobList().isEmpty());
        assertEquals("The incorrect job was added.", "[Foo Park]", 
                    mySchedule.getJobList().toString());
    }

    /**
     * Testing with a Job having invalid dates, specifically an end date before the start date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobForInvalidDates() {
        Job badDates = new Job(0, "Foo Park", 10, 10, 10, "06172015", "06152015",
                                "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(badDates);
    }

    /**
     * Testing with a Job that doesn't have an empty Volunteer list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobForEmptyVolList() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add(myVolEmail);
        temp.add("Light");
        ArrayList<ArrayList<String>> temp2 = new ArrayList<>();
        temp2.add(temp);

        myJob.getVolunteerList().add(temp);
        mySchedule.receiveJob(myJob);
    }

    /**
     * Testing with a Job with all zeroes for light, medium, and heavy
     * work grade amounts.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobForNoWorkers() {
        Job noSpace = new Job(55, "Foo Park", 0, 0, 0, "06172015", "06172015",
                                "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(noSpace);
    }

    /**
     * Testing with a Job with a null Park.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobForNullPark() {
        Job nullPark = new Job(55, null, 2, 2, 2, "06172015", "06172015",
                                "tjsg1992@gmail.com", new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(nullPark);
    }

    /**
     * Testing with a Job with a null ParkManager.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobForNullParkManager() {
        Job nullMngr = new Job(55, "Foo Park", 2, 2, 2, "06172015", "06172015", null,
                                new ArrayList<ArrayList<String>>());
        mySchedule.receiveJob(nullMngr);
    }

    /**
     * Testing if a valid user can be added.
     */
    @Test
    public void testAddVolunteerToJobForValid() {
        mySchedule.receiveJob(myJob);
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, myJob.getJobID());
    }
    
    /**
     * Testing with a negative job id number.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForAddNeg() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, -10);
    }
    
    /**
     * Testing with a nonexistant job.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForNonexistantJob() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, 100);
    }
    
    /**
     * Testing with a volunteer signed up for job on same date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForSameDateVolunteer() {
        Job someJob = new Job(55, "Foo Park", 2, 2, 2, "06172015", "06172015", "manager@job.net",
                new ArrayList<ArrayList<String>>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, someJob.getJobID());
        
        Job sameDate = new Job(100, "Foo Park", 3,3,3, "06172015", "06172015", "manager2@job.net",
                                new ArrayList<ArrayList<String>>());
        mySchedule.addVolunteerToJob(temp, sameDate.getJobID());
    }

    /**
     * Testing with a job that has already completed.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForPastJob() {
        Job pastJob = new Job(55, "Foo Park", 2, 2, 2, "08061994", "08061994", "manager@job.net",
                new ArrayList<ArrayList<String>>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, pastJob.getJobID());
    }
    
    /**
     * Testing with a job that has a full light work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForClosedLightGrade() {
        Job jobWithNoLight = new Job(55, "Foo Park", 0, 1, 0, "06172015", "06172015", "manager@job.net",
                new ArrayList<ArrayList<String>>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, jobWithNoLight.getJobID());
    }

    /**
     * Testing with a job that has a full Medium work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForClosedMediumGrade() {
        Job jobWithNoMedium = new Job(55, "Foo Park", 1, 0, 0, "06172015", "06172015", "manager@job.net",
                new ArrayList<ArrayList<String>>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Medium");
        mySchedule.addVolunteerToJob(temp, jobWithNoMedium.getJobID());
    }

    /**
     * Testing with a job that has a full Heavy work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForClosedHeavyGrade() {
        Job jobWithNoHeavy = new Job(55, "Foo Park", 1, 0, 0, "06172015", "06172015", "manager@job.net",
                new ArrayList<ArrayList<String>>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Heavy");
        mySchedule.addVolunteerToJob(temp, jobWithNoHeavy.getJobID());
    }
    
    /**
     * Testing with an invalid work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobForInvalidWorkGrade() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserForInvalidUserType() {
        mySchedule.addUser("turtlehermit@kame.house", "Master", "Roshi", "Hermit");
    }
    
    @Test
    public void testAddUserForAdministrator() {
        mySchedule.addUser("ceo@capsulecorp.net", "Bulma", "Brief", "Administrator");
    }
    
    @Test
    public void testAddUserForVolunteer() {
        mySchedule.addUser("SpiritBomb@sm4sh.net", "Goku", "Son", "Volunteer");
    }
    
    @Test
    public void testAddUserForParkManager() {
        mySchedule.addUser("android17@future.net", "Android", "17", "ParkManager");
    }
}
