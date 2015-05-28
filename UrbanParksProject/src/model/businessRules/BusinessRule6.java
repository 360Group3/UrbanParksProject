package model.businessRules;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Job;

/**
 * A Volunteer may not sign up for a job that has passed.
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule6 {

	
	/**
	 * This method is called to check whether or not the job is in the future.
	 * 
	 * 
	 * @param theJob the job.
	 * @return false if job is not in future, true otherwise.
	 */
	public boolean test(Job theJob){
	
		Calendar currentDate = new GregorianCalendar();
		
		if(currentDate.getTimeInMillis() + 2670040009l > theJob.getStartDate().getTimeInMillis()) {
			return false;
		}
		
		return true;
	}
}
