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
 * Defines the Schedule object for an application.
 * This entity contains the master job list and enforces
 * business rules associated with its role in the program.
 * 
 * @author Reid Thompson
 * @version 5.3.2015
 */
public class Schedule implements Serializable {

    private static final long serialVersionUID = 5L;
    
    private static Schedule schedule = new Schedule();  
    private static final int MAX_TOTAL_NUM_JOBS = Integer.MAX_VALUE;    
    private JobList myJobList;  
    private UserList myUserList;
    
    /**
     * Constructs a Schedule object.
     */
    private Schedule() {
        //Not to be instantiated; Singleton
    }
    
    public static Schedule getInstance() {
        return schedule;
    }

    /**
     * Receives a job and adds it to the master job list if its data is valid.
     * 
     * For user story #1. Called by createJob() method of ParkManager class.
     * 
     * @param theJob is the Job to potentially add to Schedule's list of Jobs.
     * @return true if theJob was added, and false otherwise.
     */
    public boolean receiveJob(Job theJob) throws IllegalArgumentException {     
        
        // Checks the fields of the object to make sure they're valid.
        boolean okToAdd = true;
        
        if (theJob == null)
        {
            okToAdd = false;
        }
        //BIZ rule 1. A job may not be added if the total number of pending jobs is currently 30.       
        else if (!(new BusinessRule1().test(myJobList))) { 
            okToAdd = false;
        }
        //BIZ rule 2. A job may not be added if the total number of pending jobs during that week 
            //(3 days on either side of the job days) is currently 5.
        else if (!(new BusinessRule2().test(theJob, myJobList))) {
            okToAdd = false; //this is entered if checkThisWeek() returns false;
        } 
        
        //BIZ rule 4. A job may not be scheduled that lasts more than two days.
        else if (!(new BusinessRule4().test(theJob))) {
            okToAdd = false;
        }
        
        //BIZ rule 5. A job may not be added that is in the past or more than three months in the future. 
                //I am going to say that the manager can only create a job on a date after today.
        
        else if (!(new BusinessRule5().pastTest(theJob))) {
            throw new IllegalArgumentException("Sorry but the date you entered for this "
                    + "job has already passed.");
        }
        
        else if (!(new BusinessRule5().futureTest(theJob))) {
            throw new IllegalArgumentException("Sorry but the date you entered is too far"
                    + "into the future. \n We only accept jobs that are within three months"
                    + "from now. ");
        }
        
        // checks if date range is valid
        else if (theJob.getStartDate().after(theJob.getEndDate())) {
            okToAdd = false;
        }
        
        
        // checks that the job id is valid
        else if (theJob.getJobID() < Integer.MIN_VALUE || theJob.getJobID() > Integer.MAX_VALUE) {
            okToAdd = false;
        }
        
        // checks if volunteer list is null
        else if (theJob.getVolunteerList() == null) {
            okToAdd = false;
        }
        
        // checks if volunteer list is empty
        else if (!theJob.getVolunteerList().isEmpty()) {
            okToAdd = false;
        }
        
        
        else if (!theJob.hasLightRoom() && !theJob.hasMediumRoom() && !theJob.hasHeavyRoom()) {
            okToAdd = false;
        }
        
        else if (theJob.getLightCurrent() > theJob.getLightMax() || theJob.getMediumCurrent() > theJob.getMediumMax()
                || theJob.getHeavyCurrent() > theJob.getHeavyMax()) {
            okToAdd = false;
        }
        
        else if (theJob.getPark() == null) {
            okToAdd = false;
        }
        
        else if (theJob.getManager() == null) {
            okToAdd = false;
        }
        
        if (okToAdd) {
            // To get the master job list which is editable
            List<Job> editableJobList = myJobList.getJobList();
            editableJobList.add(theJob); // add valid job to list
        } 
        else {
            throw new IllegalArgumentException("Error: job data is invalid and therefore was not added.");
        }

        return okToAdd;
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
        Job thisJob = findJob(theJobID);
        if (thisJob == null) {
            throw new IllegalArgumentException("Job does not exist");
        }

        //CHECK 3
        if (!(new BusinessRule6().test(thisJob))) {
            throw new IllegalArgumentException("Sorry, but this job has already completed.");
        }

        //CHECK 4
        if (new BusinessRule7().test(theVolunteer.get(0), thisJob, myJobList)) { 
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
        if (theJobID < 0 || theJobID > MAX_TOTAL_NUM_JOBS) {
            return false;
        }
        return true;
    }
    

    /**
     * Finds the job associated with this jobID.
     * @param theJobID is the jobs ID
     * @return the Job that has this Job ID, null otherwise.
     */
    private Job findJob(int theJobID) {
        //Calls JobList.getJobList() to get the master job list which is editable
        List<Job> editableJobList = myJobList.getJobList();


        for (int i = 0; i < editableJobList.size(); i++) {
            if (editableJobList.get(i).getJobID() == theJobID) {
                return editableJobList.get(i);
            }
        }
        return null;
    }
    
    public List<Job> getJobList() {
        return myJobList.getJobList();
    }

    public void addUser(String theEmail, String theFirstName, String theLastName,
                        String theUserType) {
        User u = null;
        switch (theUserType) {
            
            case "Volunteer":
                u = new Volunteer(theEmail, theFirstName, theLastName);
                break;
                
            case "Administrator":
                u = new Administrator(theFirstName, theLastName, theEmail);
                break;

            case "ParkManager":
                u = new ParkManager(theEmail, theFirstName, theLastName,
                                    new ArrayList<String>());
                break;
            default:
                throw new IllegalArgumentException("Not a valid user type.");
        }
        myUserList.addNewUser(u);
    }
    
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
    
    public void setJobList(JobList theJobList) {
        this.myJobList = theJobList;
    }
    
    public void setUserList(UserList theUserList) {
        this.myUserList = theUserList;
    }

}

