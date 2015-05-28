package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobList;
import model.businessRules.BusinessRule1;

import org.junit.Before;
import org.junit.Test;

public class BusinessRule1Test {
    BusinessRule1 myRule;

    JobList myJobList;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        myRule = new BusinessRule1();
        myJobList = new JobList();
    }

    @Test
    public void testTestForEmptyJoblist() {
        assertTrue(myRule.test(myJobList));
    }

    @Test
    public void testTestForPartlyFilledJoblist() {
        List<Job> j = new ArrayList<Job>();
        j.add(new Job(0, "Foo Park", 4, 4, 4, "09082015", "09082015",
                "moverby@vivaldi.com", null));

        for (int i = 1; i < BusinessRule1.MAX_JOBS / 2; i++)
            j.add(new Job(i, "Foo Park", 4, 4, 4, "09082015", "09082015",
                    "moverby@vivaldi.com", null));

        myJobList.setJobList(j);

        assertTrue(myRule.test(myJobList));
    }

    @Test
    public void testTestForFilledJoblist() {
        List<Job> j = new ArrayList<Job>();
        j.add(new Job(0, "Foo Park", 4, 4, 4, "09082015", "09082015",
                "moverby@vivaldi.com", null));

        for (int i = 1; i < BusinessRule1.MAX_JOBS; i++)
            j.add(new Job(i, "Foo Park", 4, 4, 4, "09082015", "09082015",
                    "moverby@vivaldi.com", null));

        myJobList.setJobList(j);

        assertTrue(myRule.test(myJobList));
    }

    @Test
    public void testTestForOverfilledJoblist() {
        List<Job> j = new ArrayList<Job>();
        j.add(new Job(0, "Foo Park", 4, 4, 4, "09082015", "09082015",
                "moverby@vivaldi.com", null));

        for (int i = 1; i < BusinessRule1.MAX_JOBS + 1; i++)
            j.add(new Job(i, "Foo Park", 4, 4, 4, "09082015", "09082015",
                    "moverby@vivaldi.com", null));

        myJobList.setJobList(j);

        assertFalse(myRule.test(myJobList));
    }
}
