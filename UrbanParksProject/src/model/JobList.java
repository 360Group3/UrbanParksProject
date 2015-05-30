package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.businessRules.BusinessRule1;
import model.businessRules.BusinessRule2;
import model.businessRules.BusinessRule3;
import model.businessRules.BusinessRule4;
import model.businessRules.BusinessRule5;
import model.businessRules.BusinessRule6;
import model.businessRules.BusinessRule7;

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
     * @param theJobID the ID of the desired Job
     * @return a copy if the job or null if the job was not found.
     */
    public Job getJobCopy(int theJobID) {
        Job returnJob = null;

        for (Job job : myJobList) {
            if (job.getJobID() == theJobID)
                returnJob = job;
        }

        return returnJob;
    }
    
    /**
     * Adds a Volunteer to an existing Job if the given data is valid.
     * 
     * For User Story #3. Called by Volunteer.signup()
     * 
     * @param theVolunteer the Volunteer to add to a particular job as well as the workgrade.
     * @param theJobID the ID number for the Job to which the Volunteer will be added.
     * I'm assuming this value would be 1 = light, 2 = medium, or 3 = heavy.
     * @return true if the Volunteer was added to the Job and false otherwise.
     * @throws IllegalArgumentException if a parameter was not quite right.
     */ 
    public boolean addVolunteerToJob(ArrayList<String> theVolunteer, int theJobID) throws IllegalArgumentException {
        //CHECK 1
        boolean validID = checkJobValidity(theJobID); //Schedule will check to make sure the Job ID is valid
        if (!validID){
            throw new IllegalArgumentException("This job ID cannot exist"); 
        }

        //CHECK 2
        Job thisJob = getJob(theJobID);
        if (thisJob == null) {
            throw new IllegalArgumentException("Job does not exist");
        }

        //CHECK 3
        if (!(new BusinessRule6().test(thisJob))) {
            throw new IllegalArgumentException("Sorry, but this job has already completed.");
        }

        //CHECK 4
        if (new BusinessRule7().test(theVolunteer.get(0), thisJob, this)) { 
            throw new IllegalArgumentException("Sorry, but you are already signed up "
                    + "for a job that occurs the same date!");
        }

        //CHECK 5
        if (!(new BusinessRule3().test(thisJob,theVolunteer.get(1)))) {
            throw new IllegalArgumentException("Sorry, but that grade in this job "
                    + "is already full.");
        }

        // If all the checks pass, we add the Volunteer to the Job's Volunteer List,
        // Increment the grade slot, and return.
        thisJob.getVolunteerList().add(theVolunteer);
        return true;
    }
    
    /**
     * Schedule will check to make sure the Job ID is valid
     * @param theJobID is the id number of the job.
     * @return false if invalid
     */
    private boolean checkJobValidity(int theJobID) {
        if (theJobID < 0 || theJobID > Job.MAX_NUM_JOBS) {
            return false;
        }
        return true;
    }
    
    /**
     * Return a reference to a job given its Job ID.
     * @param theJobID the ID of the desired Job
     * @return a reference to the job or null if the job was not found.
     */
    private Job getJob(int theJobID) {
        for (Job job : myJobList) {
            if (job.getJobID() == theJobID)
                return job;
        }

        return null;
    }

    public boolean addJob(Job theJob) {
        
        // Checks the fields of the object to make sure they're valid.
        boolean okToAdd = true;
        
        if (theJob == null)
        {
            okToAdd = false;
        }
        //BIZ rule 1. A job may not be added if the total number of pending jobs is currently 30.       
        else if (!(new BusinessRule1().test(this))) { 
            throw new IllegalArgumentException("Sorry, but the limit of 30 pending jobs has already been reached.");
        }
        //BIZ rule 2. A job may not be added if the total number of pending jobs during that week 
            //(3 days on either side of the job days) is currently 5.
        else if (!(new BusinessRule2().test(theJob, this))) {
            throw new IllegalArgumentException("Sorry, but the limit of 5 jobs has already been reached for the week that "
                    + "this job was scheduled.");
        } 
        
        //BIZ rule 4. A job may not be scheduled that lasts more than two days.
        else if (!(new BusinessRule4().test(theJob))) {
            throw new IllegalArgumentException("Sorry, but a job cannot last any longer than two days.");
        }
        
        //BIZ rule 5. A job may not be added that is in the past or more than three months in the future. 
                //I am going to say that the manager can only create a job on a date after today.
        
        else if (!(new BusinessRule5().pastTest(theJob))) {
            throw new IllegalArgumentException("Sorry but the date you entered for this "
                    + "job has already passed.");
        }
        
        else if (!(new BusinessRule5().futureTest(theJob))) {
            throw new IllegalArgumentException("Sorry but the date you entered is too far "
                    + "into the future. \nAll jobs must be scheduled within the next 3 months.");
        }
        
        // checks if date range is valid
        else if (theJob.getStartDate().after(theJob.getEndDate())) {
            throw new IllegalArgumentException("Sorry, but the Start Date must come before or on the same date as the End Date.");
        }
        
        
        // checks that the job id is valid
        else if (theJob.getJobID() < 0 || theJob.getJobID() > Job.MAX_NUM_JOBS) {
            throw new IllegalArgumentException("Error: Invalid Job ID. Please logout and try again.");
        }
        
        // checks if volunteer list is null
        else if (theJob.getVolunteerList() == null) {
            throw new IllegalArgumentException("Error: Null Volunteer List. Please logout and try again.");
        }
        
        // checks if volunteer list is empty
        else if (!theJob.getVolunteerList().isEmpty()) {
            throw new IllegalArgumentException("Error: Non-empty Volunteer List. Please logout and try again.");
        }
        
        
        else if (!theJob.hasLightRoom() && !theJob.hasMediumRoom() && !theJob.hasHeavyRoom()) {
            throw new IllegalArgumentException("Sorry, but a slot in at least one Volunteer Grade must be available.");
        }
        
        else if (theJob.getLightCurrent() > theJob.getLightMax() || theJob.getMediumCurrent() > theJob.getMediumMax()
                || theJob.getHeavyCurrent() > theJob.getHeavyMax()) {
            throw new IllegalArgumentException("Sorry, but the number of slots for a Volunteer Grade cannot be negative.");
        }
        
        else if (theJob.getPark() == null) {
            throw new IllegalArgumentException("Error: Null Park. Please logout and try again.");
        }
        
        else if (theJob.getManager() == null) {
            throw new IllegalArgumentException("Error: Null ParkManager. Please logout and try again.");
        }
        
        if (okToAdd) {
            // To get the master job list which is editable
            myJobList.add(theJob);
        } 
        else {
            throw new IllegalArgumentException("Error: job data is invalid and can not be added.");
        }

        return okToAdd;
    }
}
