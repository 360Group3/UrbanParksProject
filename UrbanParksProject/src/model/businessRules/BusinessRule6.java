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
	 * Test if the job is in the future.
	 * 
	 * 
	 * @param theJob the job.
	 * @return false if job is not in future, true otherwise.
	 */
	public boolean test(Job theJob){
	
		Calendar currentDate = new GregorianCalendar();
		
		if(currentDate.after(theJob.getStartDate())) {
			return false;
		}
		
		return true;
	}
}
