
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This is a volunteer.
 * 
 * @author Reid Thompson
 * @author Arsh Singh
 * @version 5.10.2015
 */
public class Volunteer extends User implements Serializable {

	private static final long serialVersionUID = 8L;
	
	public Volunteer(String theEmail, String theFirstName, String theLastName) {
		super(theFirstName, theLastName, theEmail);
	}

	
	/**
	 * This signs the volunteer up for a job.
	 * @param theVol is the volunteer's email and work grade.
	 * @param theID is the jobs id number
	 * @return true if the volunteer was signed up, false otherwise.
	 */
	public boolean signUp(ArrayList<String> theVol, int theID) {
		try {
			return Schedule.getInstance().addVolunteerToJob(theVol, theID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return false;
	}
	
	
	/**
	 * This returns a copy of the list of jobs.
	 * @return the list of jobs.
	 */
	public List<Job> getJobs() {
		return Schedule.getInstance().getJobList().getCopyList();
	}
	
	
	
	
	
	@Override
	public boolean equals(Object theO)
	{
		if (!(theO instanceof Volunteer))
			return false;

		Volunteer theOther = (Volunteer) theO;

		return (super.getFirstName().equals(theOther.getFirstName())
				&& super.getLastName().equals(theOther.getLastName())) 
				|| super.getEmail().equals(theOther.getEmail());
	}

	@Override
	public String toString()
	{
		return super.getFirstName() + " " + super.getLastName();
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.getFirstName(), super.getLastName(), super.getEmail());
	}

}