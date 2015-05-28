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
    
    Calendar now;
    Calendar then;
    
    public BusinessRule5()
    {
        Calendar now = new GregorianCalendar();
        Calendar then = new GregorianCalendar();
    }
	
	//FIXME: this method does not belong in br5.
	/**
	 * This method tests whether or not the job is in the future.
	 */
	public boolean pastTest(Job theJob){
		if (!theJob.getStartDate().after(now)) {
			return false;
		}
		
		return true;	
	}
	
	/**
	 * This method tests whether or not the job is within the next 90 days.
	 * 
	 * @param theTestedObjects is theJob that the manager wants to create. 
	 * @return true if the job starts within 90 days, false otherwise.
	 */
	public boolean futureTest(Job theJob) {
		then.add(Calendar.DAY_OF_MONTH, 90);
		
		if (theJob.getStartDate().after(then)) {
			return false;
		}
		
		return true;
	}
}


















