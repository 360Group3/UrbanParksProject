package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
	
	/**
	 * Sign the Volunteer up for a Job.
	 * @param theVolunteerInfo the volunteer's email and work grade.
	 * @param theJobID the ID Number of the Job that the Volunteer is signing up for.
	 * @return true if the volunteer successfully signed up for the Job; false otherwise.
	 * @throws IllegalArgumentException if the Volunteer was not able to be added to the Job.
	 */
	public boolean signUp(ArrayList<String> theVolunteerInfo, int theJobID) throws IllegalArgumentException {
			return Schedule.getInstance().addVolunteerToJob(theVolunteerInfo, theJobID);
	}
	
	
	/**
	 * Return all pending Jobs from DataPollster that are visible to the Volunteer.<br>
	 * Also goes through to find any Jobs that are now considered to be in the past, and updates them accordingly.
	 */
	public List<Job> getTheJobs() {		
		/*
		 * We update Jobs at this moment, to handle cases where the Volunteer is, for example,
		 * signing up for a Job near midnight.
		 */		
		List<Job> visibleJobs = Schedule.getInstance().getJobList();
		
		GregorianCalendar currentDate = new GregorianCalendar();
		long currentTime = currentDate.getTimeInMillis() + 2670040009l;
		
		for (Job job: visibleJobs) { 
			if(currentTime > job.getStartDate().getTimeInMillis()) {
				job.setInPast(true);
			}
		}

		return visibleJobs;
	}
	
	/**
	 * Return a List of all Jobs that the Volunteer has signed up for.
	 */
	public List<Job> getMyJobs() {
		return DataPollster.getInstance().getVolunteerJobs(super.getEmail());		
	}
}
