package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.Job;
import model.JobList;

import org.junit.Before;
import org.junit.Test;

public class JobListTest {

    JobList myJL;
    @Before
    public void setUp() throws Exception {
        myJL = new JobList();

    }

    @Test
    public void testGetJobCopyValidJob() {
        Job j0 = new Job(0, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        myJL.getJobList().add(j0);
        assertEquals(j0, myJL.getJobCopy(j0.getJobID()));
    }

    @Test
    public void testGetJobCopyValidJobMultipleJobs() {
        Job j0 = new Job(0, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        Job j1 = new Job(1, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        Job j2 = new Job(2, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        
        myJL.getJobList().add(j0);
        assertEquals(j0, myJL.getJobCopy(j0.getJobID()));
    
        myJL.getJobList().add(j1);
        assertEquals(j1, myJL.getJobCopy(j1.getJobID()));

        myJL.getJobList().add(j2);
        assertEquals(j2, myJL.getJobCopy(j2.getJobID()));
    }
    
    @Test
    public void testGetJobCopyInvalidJob() {
        Job j0 = new Job(0, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        Job j1 = new Job(1, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        Job j2 = new Job(2, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        
        myJL.getJobList().add(j0);
        myJL.getJobList().add(j1);
        myJL.getJobList().add(j2);
        
        Job invalidJ = new Job(3, "Test", 1, 1, 1, "12203000", "12203000", "manager@mail.com", new ArrayList<>());
        assertEquals(null, myJL.getJobCopy(invalidJ.getJobID()));
    }
}
