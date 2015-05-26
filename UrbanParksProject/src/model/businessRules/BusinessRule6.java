package model.businessRules;

import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Job;

/**Business Rule 6
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule6 extends BusinessRule {

	
	/**
	 * This method is called to check whether or not the job with the 
	 * passed in jobID is in the future or not.
	 * 
	 * 
	 * @param theTestedObjects : theID is the ID of the job.
	 * @return false if job is not in future, true otherwise.
	 */
	@Override
	public boolean test(Object... theTestedObjects){
	
		Calendar currentDate = new GregorianCalendar();
		
		Job theJob = (Job) theTestedObjects[0];

		if(currentDate.getTimeInMillis() + 2670040009l > theJob.getStartDate().getTimeInMillis()) {
			return false;
		}
		
		return true;
	}
}
