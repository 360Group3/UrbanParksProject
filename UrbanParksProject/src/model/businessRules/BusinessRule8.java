package model.businessRules;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.ParkManager;
import model.User;
import model.UserList;

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
	public boolean test(Job theJob, UserList theUserList){
		String email = theJob.getManager();
		
		List<String> listOfParks = new ArrayList<String>();
		List<User> usermanlist = theUserList.getParkManagerListCopy();
		
		for (User man: usermanlist) {
			if(man.getEmail().equals(email)) {
		        listOfParks.addAll(((ParkManager) man).getManagedParks());
		    }
		}
		
		if (listOfParks.contains(theJob.getPark())) {
			return true;
		}
		
		return false;	
	}
	
}
