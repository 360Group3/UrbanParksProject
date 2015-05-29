package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create lists for all of the pending jobs.
 * 
 * @author Arshdeep Singh
 * @version 1 May 2015
 *
 */
public class JobList implements Serializable {

    private static final long serialVersionUID = 3L;

    private List<Job> myJobList;

    public JobList() {
        myJobList = new ArrayList<Job>(Job.MAX_NUM_JOBS);
    }

    /**
     * Return a copy of the list of jobs.
     */
    public List<Job> getCopyList() {
        return new ArrayList<Job>(myJobList);
    }

    /**
     * This method should be called by Schedule class.
     * 
     * It returns an actual reference to the jobs in myJobList.
     * 
     * @return the actual list of jobs.
     */
    public List<Job> getJobList() {
        return myJobList;
    }

    public void setJobList(List<Job> theJobList) {
        this.myJobList = theJobList;
    }

    /**
     * Return the number of jobs contained within JobList.
     */
    public int getNumberOfJobs() {
        return myJobList.size();
    }

    /**
     * Return a copy of a job given its Job ID.
     */
    public Job getJobCopy(int theJobID) {
        Job returnJob = null;

        for (Job job : myJobList) {
            if (job.getJobID() == theJobID)
                returnJob = job;
        }

        return returnJob;
    }
}
