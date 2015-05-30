package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        return myJobList.addJob(theJob);
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
        return myJobList.addVolunteerToJob(theVolunteer, theJobID);
    }
    
    public List<Job> getJobList() {
        return myJobList.getCopyList();
    }

    public void addUser(String theEmail, String theFirstName, String theLastName,
                        String theUserType) {
        User u = null;
        switch (theUserType) {
            
            case "Volunteer":
                u = new Volunteer(theEmail, theFirstName, theLastName);
                break;
                
            case "Administrator":
                u = new Administrator(theEmail, theFirstName, theLastName);
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

