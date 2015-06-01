package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import model.businessRules.BusinessRule1;
import model.businessRules.BusinessRule2;
import model.businessRules.BusinessRule3;
import model.businessRules.BusinessRule4;
import model.businessRules.BusinessRule5;
import model.businessRules.BusinessRule6;
import model.businessRules.BusinessRule7;

/**
 * Schedule controls data going to the JobList and enforces business rules accordingly. 
 * @author Reid Thompson
 * @version May 31 2015
 */
public class Schedule implements Serializable {

	//Class Constant
    private static final long serialVersionUID = 5L;          
    
    //Class Variables
    private JobList myJobList;  
    private UserList myUserList;
    private static Schedule mySchedule = new Schedule();
    
    
    //Constructor
    private Schedule() {
        //Not to be instantiated; Singleton
    }
    
	// Singleton Instance
	/**
	 * Return the instance of the Schedule Singleton.
	 */
    public static Schedule getInstance() {
        return mySchedule;
    }

    
    
    /*=============*
     * Add New Job *
     *=============*/
    
    
    /*
     * receiveJob() and addVolunteerToJob() are both very length methods.
     * 
     * However, they consist of calling a long series of tests, and actually do not have very much
     * logic to them. That logic, instead, is broken up into various Business Rule classes.
     * 
     * As such, trying to break down these methods any further would be largely trivial.
     */    
    
    /**
     * Receive a job and add it to the master JobList if its data is valid.
     * @param theJob the Job to potentially add to Schedule's List of Jobs.
     * @return true if theJob was added; false otherwise.
     */
    public boolean receiveJob(Job theJob) throws IllegalArgumentException {     

    	/*
    	 * We subject both the Job and JobList to a wide variety of tests.
    	 * If all of the tests are passed, then we add the Job to the JobList.
    	 */
        boolean addFlag = true;
        
        if (theJob == null)
            addFlag = false;
        
        //Check to ensure that the total number of pending jobs is less than 30.    
        else if (!(new BusinessRule1().test(myJobList))) { 
            throw new IllegalArgumentException("Sorry, but the limit of 30 pending jobs has already been reached.");
        }        

        /*
         * Check to ensure that the total number of Jobs for the week that this Job is to occur is
         * less than 5.
         * A week is defined as 3 days before a Job's Start Date and 3 days after its End Date.
         */
        else if (!(new BusinessRule2().test(theJob, myJobList))) {
            throw new IllegalArgumentException("Sorry, but the limit of 5 jobs has already been reached for the week that "
            		+ "this job was scheduled.");
        } 
        
        //Check that the Job is not scheduled to last longer than two days.
        else if (!(new BusinessRule4().test(theJob))) {
            throw new IllegalArgumentException("Sorry, but a job cannot last any longer than two days.");
        }
        
        //Check that a Job is scheduled to begin after the current date.        
        else if (!(new BusinessRule5().pastTest(theJob))) {
            throw new IllegalArgumentException("Sorry but the date you entered for this "
                    + "job has already passed.");
        }
        
        //Check that a Job is scheduled to begin within the next three months.
        else if (!(new BusinessRule5().futureTest(theJob))) {
            throw new IllegalArgumentException("Sorry but the date you entered is too far "
                    + "into the future. \nAll jobs must be scheduled within the next 3 months.");
        }
        
        //Check that the Start Date and End Date are not swapped.
        else if (theJob.getStartDate().after(theJob.getEndDate())) {
            throw new IllegalArgumentException("Sorry, but the Start Date must come before or on the same date as the End Date.");
        }
        
        
        //Check that the Job ID is valid.
        else if (theJob.getJobID() < 0 || theJob.getJobID() > Job.MAX_NUM_JOBS) {
            throw new IllegalArgumentException("Error: Invalid Job ID. Please logout and try again.");
        }
        
        //Check that the Volunteer List is not null.
        else if (theJob.getVolunteerList() == null) {
            throw new IllegalArgumentException("Error: Null Volunteer List. Please logout and try again.");
        }
        
        //Check that the Volunteer List is empty.
        else if (!theJob.getVolunteerList().isEmpty()) {
            throw new IllegalArgumentException("Error: Non-empty Volunteer List. Please logout and try again.");
        }
        
        //Check that there is at least one slot available for a Volunteer to sign up for.
        else if (!theJob.hasLightRoom() && !theJob.hasMediumRoom() && !theJob.hasHeavyRoom()) {
            throw new IllegalArgumentException("Sorry, but a slot in at least one Volunteer Grade must be available.");
        }
        
        //Check that none of the slots are set to negative numbers.
        else if (theJob.getLightCurrent() > theJob.getLightMax() || theJob.getMediumCurrent() > theJob.getMediumMax()
                || theJob.getHeavyCurrent() > theJob.getHeavyMax()) {
            throw new IllegalArgumentException("Sorry, but the number of slots for a Volunteer Grade cannot be negative.");
        }
        
        //Check that the Park for the Job is not null.
        else if (theJob.getPark() == null) {
            throw new IllegalArgumentException("Error: Null Park. Please logout and try again.");
        }
        
        //Check that the ParkManager for the Job is not null.
        else if (theJob.getManager() == null) {
            throw new IllegalArgumentException("Error: Null ParkManager. Please logout and try again.");
        }
        
        //If all tests passed, then we add the Job to the Schedule.
        if (addFlag) {
            // To get the master job list which is editable
            List<Job> editableJobList = myJobList.getJobList();
            editableJobList.add(theJob); // add valid job to list
        } 
        else {
        	//If we somehow got here without throwing an exception, and the Job is not valid, then we throw a general exception.
            throw new IllegalArgumentException("Error: Job data is invalid for unknown reasons. Please logout and try again.");
        }

        return addFlag;
    }   
    
    
    
    
    /*======================*
     * Add Volunteer to Job *
     *======================*/
        
    /**
     * Add a Volunteer to an existing Job if the given data is valid.
     * 
     * @param theVolunteerInfo the email of the Volunteer and their desired work grade.
     * @param theJobID the ID number for the Job to which the Volunteer will be added.
     * @return true if the Volunteer was added to the Job; false otherwise.
     * @throws IllegalArgumentException thrown for various input errors.
     */ 
    public boolean addVolunteerToJob(List<String> theVolunteerInfo, int theJobID) throws IllegalArgumentException {
    	
        //Check that the Job ID is valid.
        boolean validID = checkJobValidity(theJobID);
        if (!validID){
            throw new IllegalArgumentException("Sorry, but the Job ID you entered was invalid."); 
        }

        //Check that the Job exists.
        Job thisJob = findJob(theJobID);
        if (thisJob == null) {
            throw new IllegalArgumentException("Sorry, but the Job you are trying to sign up for does not exist.");
        }

        //Check that the Job has not already been completed.
        if (!(new BusinessRule6().test(thisJob))) {
            throw new IllegalArgumentException("Sorry, but you cannot sign up for a job has already completed.");
        }

        //Check that the Volunteer is not signed up for a Job that occurs on the same date.
        if (new BusinessRule7().test(theVolunteerInfo.get(0), thisJob, myJobList)) { 
            throw new IllegalArgumentException("Sorry, but you are already signed up "
                    + "for a job that occurs the same date!");
        }

        //Check that the grade of work that the Volunteer is signing up for is not already full.
        if (!(new BusinessRule3().test(thisJob,theVolunteerInfo.get(1)))) {
            throw new IllegalArgumentException("Sorry, but that grade in this job "
                    + "is already full.");
        }

        // If all the checks pass, we add the Volunteer to the Job's Volunteer List.
        thisJob.getVolunteerList().add(theVolunteerInfo);
        return true;
    }
    
    
    public void updatePastJobs() {
    	GregorianCalendar currentDate = new GregorianCalendar();
		long currentTime = currentDate.getTimeInMillis() + 2670040009l;
		
		for (Job job : myJobList.getJobList()) { 
			if(currentTime > job.getStartDate().getTimeInMillis()) {
				job.setInPast(true);
			}
		}
    }
    
    
    /*===============*
     * Add Park/User *
     *===============*/
    
    /**
     * Update the list of managed parks of a Park Manager.
     * @author Taylor Gorman
     */
    public void updateParkList(String theEmail, List<String> theManagedParks) {
        List<User> myManagerList = myUserList.getParkManagerListCopy();
        
        for(User manager : myManagerList) {
            if(manager.getEmail().equals(theEmail)) {
                ((ParkManager) manager).setManagedParks(theManagedParks);
            }
        }
        
    }
    
    
    public void addUser(String theEmail, String theFirstName, String theLastName,
            String theUserType) {    
    	
		User user = null;
		
		switch (theUserType) {
		case "Volunteer":
		    user = new Volunteer(theEmail, theFirstName, theLastName);
		    break;		    
		case "Administrator":
		    user = new Administrator(theEmail, theFirstName, theLastName);
		    break;		
		case "ParkManager":
		    user = new ParkManager(theEmail, theFirstName, theLastName, new ArrayList<String>());
		    break;
		default:
		    throw new IllegalArgumentException("Error: Invalid user type. Please logout and try again.");
		}
		
		myUserList.addNewUser(user);
	}
    
    
    
    
    /*=================*
     * Getters/Setters *
     *=================*/
    
    /**
     * Update the JobList with a new List of Jobs.
     */
    public void setJobList(JobList theJobList) {
        this.myJobList = theJobList;
    }
    
    /**
     * Update the UserList with a new List of Users.
     */
    public void setUserList(UserList theUserList) {
        this.myUserList = theUserList;
    }
    
    /**
     * Return the JobList.
     */
    public List<Job> getJobList() {
        return myJobList.getJobList();
    }
    
    
    
    
    /*================*
     * Helper Methods *
     *================*/
    
    /**
     * Call Schedule to check that the Job ID is valid
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
     * Find the job associated with the jobID; null if one does not exist.
     * @param theJobID is the jobs ID
     */
    private Job findJob(int theJobID) {
    	//Get an actual reference to the JobList.
        List<Job> editableJobList = myJobList.getJobList();
        
        Job returnJob = null;

        //Search for the Job in the JobList.
        for (int i = 0; i < editableJobList.size(); i++) {
            if (editableJobList.get(i).getJobID() == theJobID) {
                returnJob = editableJobList.get(i);
            }
        }
        return returnJob;
    }
}