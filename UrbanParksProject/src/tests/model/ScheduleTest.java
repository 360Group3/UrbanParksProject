package tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.Schedule;
import model.UserList;

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

        myJob = new Job(55, "Foo Park", 10, 10, 10, "07172015", "07172015",
                        "tjsg1992@gmail.com", new ArrayList<>());


    }

    /**
     * Testing if a valid job can be received.
     */
    @Test
    public void testReceiveJobOnValidJob() {
        mySchedule.receiveJob(myJob);
        assertFalse("No job was added.", mySchedule.getJobList().isEmpty());
        assertEquals("The incorrect job was added.", "[Foo Park]", 
                    mySchedule.getJobList().toString());
    }

    /**
     * Testing with a Job having invalid dates, specifically an end date before the start date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobOnInvalidDates() {
        Job badDates = new Job(0, "Foo Park", 10, 10, 10, "07172015", "07152015",
                                "tjsg1992@gmail.com", new ArrayList<>());
        mySchedule.receiveJob(badDates);
    }

    /**
     * Testing with a Job that doesn't have an empty Volunteer list.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobOnEmptyVolList() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add(myVolEmail);
        temp.add("Light");
        List<List<String>> temp2 = new ArrayList<>();
        temp2.add(temp);

        myJob.getVolunteerList().add(temp);
        mySchedule.receiveJob(myJob);
    }

    /**
     * Testing with a Job with all zeroes for light, medium, and heavy
     * work grade amounts.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobOnNoWorkers() {
        Job noSpace = new Job(55, "Foo Park", 0, 0, 0, "07172015", "07172015",
                                "tjsg1992@gmail.com", new ArrayList<>());
        mySchedule.receiveJob(noSpace);
    }

    /**
     * Testing with a Job with a null Park.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobOnNullPark() {
        Job nullPark = new Job(55, null, 2, 2, 2, "07172015", "07172015",
                                "tjsg1992@gmail.com", new ArrayList<>());
        mySchedule.receiveJob(nullPark);
    }

    /**
     * Testing with a Job with a null ParkManager.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReceiveJobOnNullParkManager() {
        Job nullMngr = new Job(55, "Foo Park", 2, 2, 2, "07172015", "07172015", null,
                                new ArrayList<>());
        mySchedule.receiveJob(nullMngr);
    }

    /**
     * Testing if a valid user can be added.
     */
    @Test
    public void testAddVolunteerToJobOnValidUser() {
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
    public void testAddVolunteerToJobOnNegativeJobIDNumber() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, -10);
    }
    
    /**
     * Testing with a nonexistent job.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnNonexistentJob() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, 100);
    }
    
    /**
     * Testing with a volunteer signed up for job on same date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnSameDateVolunteer() {
        Job someJob = new Job(55, "Foo Park", 2, 2, 2, "07172015", "07172015", "manager@job.net",
                new ArrayList<>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, someJob.getJobID());
        
        Job sameDate = new Job(100, "Foo Park", 3,3,3, "07172015", "07172015", "manager2@job.net",
                                new ArrayList<>());
        mySchedule.addVolunteerToJob(temp, sameDate.getJobID());
    }

    /**
     * Testing with a job that has already completed.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnPastJob() {
        Job pastJob = new Job(55, "Foo Park", 2, 2, 2, "08061994", "08061994", "manager@job.net",
                new ArrayList<>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, pastJob.getJobID());
    }
    
    /**
     * Testing with a job that has a full light work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnFullLightGrade() {
        Job jobWithNoLight = new Job(55, "Foo Park", 0, 1, 0, "07172015", "07172015", "manager@job.net",
                new ArrayList<>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Light");
        mySchedule.addVolunteerToJob(temp, jobWithNoLight.getJobID());
    }

    /**
     * Testing with a job that has a full Medium work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnFullMediumGrade() {
        Job jobWithNoMedium = new Job(55, "Foo Park", 1, 0, 0, "07172015", "07172015", "manager@job.net",
                new ArrayList<>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Medium");
        mySchedule.addVolunteerToJob(temp, jobWithNoMedium.getJobID());
    }

    /**
     * Testing with a job that has a full Heavy work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnFullHeavyGrade() {
        Job jobWithNoHeavy = new Job(55, "Foo Park", 1, 0, 0, "07172015", "07172015", "manager@job.net",
                new ArrayList<>());
        
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Heavy");
        mySchedule.addVolunteerToJob(temp, jobWithNoHeavy.getJobID());
    }
    
    /**
     * Testing with an invalid work grade.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddVolunteerToJobOnInvalidWorkGrade() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("moverby@gmail.com");
        temp.add("Quasi-Light");
        mySchedule.addVolunteerToJob(temp, myJob.getJobID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserOnInvalidUserType() {
        mySchedule.addUser("turtlehermit@kame.house", "Master", "Roshi", "Hermit");
    }
    
    @Test
    public void testAddUserOnAdministrator() {
        mySchedule.addUser("ceo@capsulecorp.net", "Bulma", "Brief", "Administrator");
    }
    
    @Test
    public void testAddUserOnVolunteer() {
        mySchedule.addUser("SpiritBomb@sm4sh.net", "Goku", "Son", "Volunteer");
    }
    
    @Test
    public void testAddUserOnParkManager() {
        mySchedule.addUser("android17@future.net", "Android", "17", "ParkManager");
    }
}
