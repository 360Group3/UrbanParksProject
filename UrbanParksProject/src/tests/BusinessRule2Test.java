package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.businessRules.BusinessRule2;

import org.junit.Before;
import org.junit.Test;

public class BusinessRule2Test {
    BusinessRule2 myRule;

    JobList myJobList;

    Job myJob;

    List<String> myWeek;
    
    @Before
    public void setUp() throws Exception {
        myRule = new BusinessRule2();
        myJobList = new JobList();
        myWeek = new ArrayList<>();

        for (int i = 1; i <= BusinessRule2.LIMITING_DURATION; i++)
            myWeek.add("090" + i + "2015");

        myJob = new Job(0, "Foo Park", 4, 4, 4,
                        myWeek.get(BusinessRule2.LIMITING_DURATION / 2),
                        myWeek.get(BusinessRule2.LIMITING_DURATION / 2), 
                        "moverby@vivaldi.com", null);
    }

    @Test
    public void testTestForEmptyWeek() {
        assertTrue(myRule.test(myJob, myJobList));
    }

    /**
     * First half of the week has all the week's jobs.
     */
    @Test
    public void testTestForFullWeekEarly() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, myWeek.get(0), myWeek.get(0),
                             "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertFalse(myRule.test(myJob, myJobList));
    }

    /**
     * Second half of the week has all the week's jobs.
     */
    @Test
    public void testTestForFullWeekLate() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, 
                             myWeek.get(BusinessRule2.LIMITING_DURATION - 1), 
                             myWeek.get(BusinessRule2.LIMITING_DURATION - 1), 
                             "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertFalse(myRule.test(myJob, myJobList));
    }

    /**
     * The job's day has all the week's jobs.
     */
    @Test
    public void testTestForFullWeekOnDay() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, 
                            myWeek.get(BusinessRule2.LIMITING_DURATION / 2), 
                            myWeek.get(BusinessRule2.LIMITING_DURATION / 2), 
                            "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertFalse(myRule.test(myJob, myJobList));
    }

    @Test
    public void testTestForFullWeekEarlyTwoDayJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, myWeek.get(0), myWeek.get(1),
                            "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertFalse(myRule.test(myJob, myJobList));
    }

    @Test
    public void testTestForFullWeekLateTwoDayJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, 
                            myWeek.get(BusinessRule2.LIMITING_DURATION / 2 - 2),
                            myWeek.get(BusinessRule2.LIMITING_DURATION / 2 - 1), 
                            "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertFalse(myRule.test(myJob, myJobList));
    }

    /**
     * Relies on 2 day job counting twice.
     */
    @Test
    public void testTestForFullWeekEarlySingleTwoDayJobs() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(0, "Foo Park", 4, 4, 4, myWeek.get(0), myWeek.get(1),
                "moverby@vivaldi.com", null));

        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK - 2; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, myWeek.get(0), myWeek.get(0),
                    "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertEquals(BusinessRule2.MAX_JOBS_IN_WEEK - 1, myJobList.getNumberOfJobs());
        assertFalse(myRule.test(myJob, myJobList));
    }

    @Test
    public void testTestForFullWeekLateSingleTwoDayJobs() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(0, "Foo Park", 4, 4, 4, 
                        myWeek.get(BusinessRule2.LIMITING_DURATION / 2 - 2), 
                        myWeek.get(BusinessRule2.LIMITING_DURATION / 2 - 1), 
                        "moverby@vivaldi.com", null));

        for (int i = 0; i < BusinessRule2.MAX_JOBS_IN_WEEK - 2; i++)
            jobs.add(new Job(0, "Foo Park", 4, 4, 4, 
                            myWeek.get(BusinessRule2.LIMITING_DURATION / 2 - 1), 
                            myWeek.get(BusinessRule2.LIMITING_DURATION / 2 - 1), 
                            "moverby@vivaldi.com", null));
        myJobList.setJobList(jobs);

        assertEquals(BusinessRule2.MAX_JOBS_IN_WEEK - 1, myJobList.getNumberOfJobs());
        assertFalse(myRule.test(myJob, myJobList));
    }
}
