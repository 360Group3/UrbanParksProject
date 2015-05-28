/**
 * 
 */
package model.businessRules;

import model.Job;

/**Business Rule 3
 * 
 * @author Arshdeep Singh
 *
 */
public class BusinessRule3 {
	
		
	/**
	 * Checks to make sure that the job grade chosen has an available slot.
	 * 
	 * @param theJob is the job
	 * @param theGrade is the grade in that job
	 * @return true if open slot, false otherwise.
	 */
	public boolean test(Job theJob, String theGrade) {
		switch (theGrade) {
    		case "Light":
    			if (theJob.hasLightRoom()) {
    				return true;
    			}
    			break;
    		case "Medium":
    			if (theJob.hasMediumRoom()) {
    				return true;
    			}
    			break;
    		case "Heavy":
    			if (theJob.hasHeavyRoom()) {
    				return true;
    			}
    			break;
    		default:
    			throw new IllegalStateException(theGrade
    					        + " for job " + theJob.getJobID() + " is full");	
		}
		return false;
	}

}
