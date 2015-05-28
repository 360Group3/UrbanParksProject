package model.businessRules;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Job;

/**
 * A job may not be added that is in the past or more than three months in the future.
 * @author Arshdeep Singh
 *
 */
public class BusinessRule5 {
	
	/**
	 * Test if the job is in the future.
	 * @return true if job is in the future, false otherwise.
	 */
	public boolean pastTest(Job theJob) {
	    Calendar now = new GregorianCalendar();
		if (theJob.getStartDate().before(now)) {
			return false;
		}
		
		return true;	
	}
	
	/**
	 * Test if the job is within the next 90 days.
	 * 
	 * @param theTestedObjects is theJob that the manager wants to create. 
	 * @return true if the job starts within 90 days, false otherwise.
	 */
	public boolean futureTest(Job theJob) {
        Calendar then = new GregorianCalendar();
        then.add(Calendar.DAY_OF_MONTH, 90);
        
		if (theJob.getStartDate().after(then)) {
			return false;
		}
		
		return true;
	}
}


















