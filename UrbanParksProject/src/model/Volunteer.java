package model;

import java.io.Serializable;
import java.util.List;

/**
 * This is a volunteer.
 * 
 * @author Reid Thompson
 * @author Arsh Singh
 * @version May 31 2015
 */
public class Volunteer extends User implements Serializable {

	//Class Constant
    private static final long serialVersionUID = 8L;

    /**
     * Volunteer Constructor
     * @param theEmail the email address of the Volunteer
     * @param theFirstName the first name of the Volunteer
     * @param theLastName the last name of the Volunteer
     */
    public Volunteer(String theEmail, String theFirstName, String theLastName) {
        super(theFirstName, theLastName, theEmail);
    }
    
    
    public Volunteer(String theEmail) {
    	super(DataPollster.getInstance().getVolunteer(theEmail).getFirstName(), 
    			DataPollster.getInstance().getVolunteer(theEmail).getLastName(),
    			theEmail);
    }
    
    
    
    /*============*
     * Job Signup *
     *============*/
	
	/**
	 * Sign the Volunteer up for a Job.
	 * @param theVolunteerInfo the volunteer's email and work grade.
	 * @param theJobID the ID Number of the Job that the Volunteer is signing up for.
	 * @return true if the volunteer successfully signed up for the Job; false otherwise.
	 * @throws IllegalArgumentException if the Volunteer was not able to be added to the Job.
	 */
	public boolean signUpForJob(List<String> theVolunteerInfo, int theJobID) throws IllegalArgumentException {
			return Schedule.getInstance().addVolunteerToJob(theVolunteerInfo, theJobID);
	}
	
	
	
	/*==============*
	 * Display Jobs *
	 *==============*/
	
	/**
	 * Return all pending Jobs from DataPollster that are visible to the Volunteer.
	 */
	public List<Job> getPendingJobs() {		
		//We first check to see if any Jobs have been completed since the time that the Volunteer logged in.
		Schedule.getInstance().updatePastJobs();
		
		List<Job> visibleJobs = Schedule.getInstance().getJobList();
		return visibleJobs;
	}
	
	/**
	 * Return a List of all Jobs that the Volunteer has signed up for.
	 */
	public List<Job> getMyJobs() {
		return DataPollster.getInstance().getVolunteerJobs(super.getEmail());		
	}
}
