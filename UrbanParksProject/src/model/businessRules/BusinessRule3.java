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
public class BusinessRule3 extends BusinessRule {
	
		
	/**
	 * Checks to make sure that the job grade chosen has an available slot.
	 * 
	 * @param j is the job
	 * @param theGrade is the grade in that job
	 * @return true if open slot, false otherwise.
	 */
	@Override
	public boolean test(Object... theTestedObjects) {
		
		Job j = (Job) theTestedObjects[0];
		String theGrade = (String) theTestedObjects[1];
		
		switch (theGrade) {
		case "Light":
			if (j.hasLightRoom()) {
				return true;
			}
			break;
		case "Medium":
			if (j.hasMediumRoom()) {
				return true;
			}
			break;
		case "Heavy":
			if (j.hasHeavyRoom()) {
				return true;
			}
			break;
		default:
			throw new IllegalStateException(theGrade
					+ " for job " + j.getJobID() + " is full");	
		}
		return false;
	}

}
