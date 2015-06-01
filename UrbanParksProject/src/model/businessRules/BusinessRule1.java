package model.businessRules;

import model.JobList;

/**
 * A job may not be added if the total number of pending jobs is currently 30.
 */
public class BusinessRule1 {

    /**
     * The maximum number of pending jobs.
     */
    public static final int MAX_JOBS = 30;

    //FIXME: receivejob is showing the 5 jobs in a week exception when this fails.
    public boolean test(JobList theJobList) {
        return theJobList.getNumberOfPendingJobs() < MAX_JOBS;
    }
}
