package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.businessRules.BusinessRule8;

/**
 * ParkManager is a subclass of User.
 * 
 *  A ParkManager can add new jobs to the Job List, view all jobs for the parks that
 * they manage, and view all volunteers for a given job.
 * 
 * @author Taylor Gorman
 * @author Reid Thompson
 * @version 26 May 2015
 *
 */
public class ParkManager extends User implements Serializable {

    private static final long serialVersionUID = 4L;

    // Class Variables
    private List<String> myManagedParks;
    private String myEmail = super.getEmail();

    // Constructor
    public ParkManager(String theEmail, String theFirstName, String theLastName, List<String> theParkList) {    	
        super(theFirstName, theLastName, theEmail);

        //theParkList is an Unmodifiable List, so it is copied into myManagedParks.
        List<String> copiedParks = new ArrayList<String>();
        copiedParks.addAll(theParkList);
        this.myManagedParks = copiedParks;
    }
    
    

    /*======*
     * Jobs *
     *======*/

    /**
     * Return a List of all Jobs in the parks that the ParkManager manages.
     */
    public List<Job> getJobs() {
        return DataPollster.getInstance().getManagerJobs(myEmail);
    }

    /**
     * Add a Job to the JobList, with this ParkManager set as the manager for the job.
     * @return true if the Job was successfully added; false otherwise
     */
    public boolean addJob(Job theJob) {
        //  Check to find whether the manager manages the park that this job is at.
        
        if (!(new BusinessRule8().test(theJob, myManagedParks))) {
            throw new IllegalArgumentException("The park for this job is not in your"
                    + "list of managed parks.");
        }
        
        return Schedule.getInstance().receiveJob(theJob);
    }

    /**
     * Return the next available Job ID for a new job.
     */
    public int getNewJobID() {
        return DataPollster.getInstance().getNextJobID();
    }

    /**
     * Return true if this ParkManager is the manager of the job; false otherwise.
     */
    public boolean isManagerOfJob(int theJobID) {
        boolean containsJob = false;
        
        for (Job job : getJobs()) {
            if (job.getJobID() == theJobID)
                containsJob = true;
        }
        return containsJob;
    }

    
    
    /*=======*
     * Parks *
     *=======*/

    /**
     * Return a List of all parks that this ParkManager manages.
     */
    public List<String> getManagedParks() {
        return Collections.unmodifiableList(myManagedParks);
    }

    /**
     * Set the List of Parks that this ParkManager manages.
     */
    public void setManagedParks(List<String> theManagedParks) {
        this.myManagedParks = theManagedParks;
    }

    
    
    /*============*
     * Volunteers *
     *============*/

    /**
     * Return a list of all Volunteers for a Job that this ParkManager manages.
     * @return The list of Volunteers; null if the ParkManager does not manager this job.
     */
    public List<Volunteer> getJobVolunteerList(int theJobID) {
        List<Volunteer> volunteerList;

        if(isManagerOfJob(theJobID)) {
        	volunteerList = new ArrayList<Volunteer>();
            volunteerList.addAll(DataPollster.getInstance().getJobVolunteerList(theJobID));
        } else {
        	//The ParkManager does not manage this job.
        	volunteerList = null;
        }
        
        return volunteerList;
    }

}
