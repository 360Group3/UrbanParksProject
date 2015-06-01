package model.businessRules;

import java.util.GregorianCalendar;
import java.util.List;

import model.Job;
import model.JobList;

/**
 * checks to make sure that the volunteer has not signed up for a job
 * on that same day.
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule7 {

	 
	/**
	 * @param theEmail the volunteers email.
	 * @param theJob the job that s/he wants to sign up for.
	 * @param theJobList a list of all the jobs.
	 * 
	 * @return true if the volunteer has already signed up for a job on this day. false otherwise.
	 */
	public boolean test(String theEmail, Job theJob, JobList theJobList) {

	    GregorianCalendar startDate = theJob.getStartDate();
		GregorianCalendar endDate = theJob.getEndDate();

		for(Job job : theJobList.getCopyList()) {
			for(List<String> volunteer : job.getVolunteerList()) {
				if(volunteer.get(0).equals(theEmail)) {
					//Found a job with the volunteer in it!
					if(startDate.equals(job.getStartDate())) return true;
					if(startDate.equals(job.getEndDate())) return true;
					if(endDate.equals(job.getStartDate())) return true;
					if(endDate.equals(job.getEndDate())) return true;
				}
			}
		}
		return false;
	}

}