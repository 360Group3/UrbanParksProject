package model.businessRules;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Job;

/**
 * This class makes sure the fifth business rule is followed.
 * @author Arshdeep Singh
 *
 */
public class BusinessRule5  extends BusinessRule {
	Calendar now = new GregorianCalendar();
	Calendar then = new GregorianCalendar();
	
	
	/**
	 * This method tests whether or not the job is in the future.
	 */
	@Override
	public boolean test(Object... theTestedObjects){
		
		Job theJob = (Job) theTestedObjects[0];
		
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
	public boolean test2(Object... theTestedObjects) {
		then.add(Calendar.DAY_OF_MONTH, 90);
		
		Job theJob = (Job) theTestedObjects[0];
		
		if (theJob.getStartDate().after(then)) {
			return false;
		}
		
		return true;
	}
}


















