
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;


/**
 * This is a volunteer.
 * 
 * @author Reid Thompson
 * @author Arsh Singh
 * @version 5.10.2015
 */
public class Volunteer extends User implements Serializable {

	private static final long serialVersionUID = 8L;
	
	public Volunteer(String theEmail, String theFirstName, String theLastName) {
		super(theFirstName, theLastName, theEmail);
	}

	
	/**
	 * This signs the volunteer up for a job.
	 * @param theVol is the volunteer's email and work grade.
	 * @param theID is the jobs id number
	 * @return true if the volunteer was signed up, false otherwise.
	 */
	public boolean signUp(ArrayList<String> theVol, int theID) {
		try {
			return Schedule.getInstance().addVolunteerToJob(theVol, theID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return false;
	}
	
	
	/**
	 * This returns a copy of the list of jobs.
	 * @return the list of jobs.
	 */
	public List<Job> getJobs() {
		return Schedule.getInstance().getJobList().getCopyList();
	}
	
	
	/**
	 * This method gets the list of jobs from DataPollster and sends it on to
	 * wherever its needed.
	 * It also sets each jobs 'myPast' field which indicates whether or not 
	 * the job is in the past.
	 */
	public List<Job> getTheJobs() {
		List<Job> daJobs = Schedule.getInstance().getJobList().getCopyList();
		Calendar currentDate = new GregorianCalendar();
		
		for (Job j: daJobs) { //go through each job and find out what job is in the past.
								//then change that job's JobID to -1 so that it can be
								//checked for and ignored when displaying the jobs.
			if(currentDate.getTimeInMillis() + 2670040009l > j.getStartDate().getTimeInMillis()) {
				j.setIfPast(true);
			}
		}

		return daJobs;
	}
	
	
	
	
	/**
	 * This method returns a list of all the jobs this volunteer 
	 * has signed up for.
	 * @return a list of jobs.
	 */
	public List<Job> getMyJobs() {
		List<Job> jobList = Schedule.getInstance().getJobList().getCopyList();
		
		List<Job> mines = new ArrayList<Job>();
		
		//go through each job in the list and see if the volunteer has signed up for that job.
		for(Job job : jobList) {
			ArrayList<ArrayList<String>> volunteerList = job.getVolunteerList();
			
			for(ArrayList<String> volunteer : volunteerList) {
				if(volunteer.get(0).equals(getEmail())) {
					mines.add(job);
				}
			}
		}
		return mines;
		
	}
	
	
	
	
	@Override
	public String toString()
	{
		return super.getFirstName() + " " + super.getLastName();
	}


	@Override
	public boolean equals(Object theO)
	{
		if (!(theO instanceof Volunteer))
			return false;
	
		Volunteer theOther = (Volunteer) theO;
	
		return (super.getFirstName().equals(theOther.getFirstName())
				&& super.getLastName().equals(theOther.getLastName())) 
				|| super.getEmail().equals(theOther.getEmail());
	}


	@Override
	public int hashCode() {
		return Objects.hash(super.getFirstName(), super.getLastName(), super.getEmail());
	}

}