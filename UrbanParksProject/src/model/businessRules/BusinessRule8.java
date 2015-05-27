package model.businessRules;

import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.ParkManager;
import model.User;
import model.UserList;


/**
 * This is the eighth business rule.
 * @author Arshdeep Singh
 *
 */
public class BusinessRule8 extends BusinessRule {

	/**
	 * This method tests whether or not the park manager runs 
	 * the park that he is creating a job for.
	 */
	@Override
	public boolean test(Object... theTestedObjects){
		Job theJob = (Job) theTestedObjects[0];
		UserList myUserList = (UserList) theTestedObjects[1];
		
		String email = theJob.getManager();
		
		List<String> listOfParks = new ArrayList<String>();
		List<User> usermanlist = myUserList.getParkManagerListCopy();
		
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
