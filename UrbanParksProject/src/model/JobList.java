package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class is used to create lists for all of the pending jobs.
 * @author Arshdeep Singh
 * @version 31 May 2015
 *
 */
public class JobList implements Serializable {

	//Class Constants
    private static final long serialVersionUID = 3L;
    private List<Job> myJobList;

    /**
     * Constructor for JobList.
     */
    public JobList() {
        myJobList = new ArrayList<Job>(Job.MAX_NUM_JOBS);
    }
    
    
    /*================*
     * Getters/Setters *
     *================*/
    
    /**
     * Return an actual reference to the jobs in myJobList.
     */
    public List<Job> getJobList() {
        return myJobList;
    }

    /**
     * Update JobList to the passed List of Jobs.
     */
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
     * This method counts the number of pending jobs.
     * @return the number of pending jobs.
     */
    public int getNumberOfPendingJobs() {
    	int count = 0;
    	GregorianCalendar currentDate = new GregorianCalendar();
    	long currentTime = currentDate.getTimeInMillis();
    	
    	for (Job j: myJobList) {
    		if(currentTime > j.getStartDate().getTimeInMillis()) {
				j.setInPast(true);
    		} else {
    			count++;
    		}
    	}
    	
    	return count;	
    }
    
    
    /*==============*
     * Copy Getters *
     *==============*/

    /**
     * Return a copy of the list of jobs.
     */
    public List<Job> getCopyList() {
        return new ArrayList<Job>(myJobList);
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
