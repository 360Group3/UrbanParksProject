package model.businessRules;

import java.util.List;

import model.Job;

/**
 * A Park Manager can create jobs only for those parks that he/she manages
 * @author Arshdeep Singh
 *
 */
public class BusinessRule8 {

	/**
	 * This method tests whether or not the park manager runs 
	 * the park that he is creating a job for.
	 */
	public boolean test(Job theJob, List<String> theManagedParks){
	    return theManagedParks.contains(theJob.getPark());
	}
}
