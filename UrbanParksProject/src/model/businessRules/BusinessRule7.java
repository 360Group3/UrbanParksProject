/**
 * 
 */
package model.businessRules;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.Job;
import model.JobList;

/**Business rule #7
 * checks to make sure that the volunteer has not signed up for a job
 * on that same day.
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule7 extends BusinessRule {

	 
	/**
	 * @param theTestedObjects is and object array of three things. 
	 * First is a String of the volunteers email.
	 * Second is the job that s/he wants to sign up for.
	 * Third is a list of all the jobs.
	 * 
	 * @return true if the volunteer has already signed up for a job on this day. false otherwise.
	 */
	@Override
	public boolean test(Object... theTestedObjects) {

		String email = (String) theTestedObjects[0];
		Job theJob = (Job) theTestedObjects[1];
		JobList myJobList = (JobList) theTestedObjects[2];

		GregorianCalendar startDate = theJob.getStartDate();
		GregorianCalendar endDate = theJob.getEndDate();

		for(Job job : myJobList.getCopyList()) {
			for(ArrayList<String> volunteer : job.getVolunteerList()) {
				if(volunteer.get(0).equals(email)) {
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