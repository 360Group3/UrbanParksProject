package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to poll our data-containing classes on behalf of our users.
 * 
 * @author Mike Overby - initial implementation
 * @author Reid Thompson - added User functionality.
 * @author Taylor Gorman - Several new method and rewrites
 * @version 5.21.2015
 */
public class DataPollster implements Serializable {

	private static final long serialVersionUID = 1L;

	// Class Variables
	private static DataPollster dataPollster = new DataPollster();
	private JobList myJobList;
	private UserList myUserList;

	// Constructor
	private DataPollster() {
		// Not to be instantiated more than once; Singleton
	}

	// Singleton Instance
	public static DataPollster getInstance() {
		return dataPollster;
	}

	/*
	 * =================* JobList Subsets *=================
	 */

	/**
	 * Return a list of all Jobs that are visible to the Volunteer. This
	 * excludes:<br>
	 * 1. Jobs that have no room.<br>
	 * 2. Jobs that occur on the same date as a job the Volunteer is already
	 * signed up for.
	 * 
	 * @author Mike Overby - initial implementation.
	 * @author Taylor Gorman - refactor
	 */
	public List<Job> getPendingJobs(String theEmail) {
		List<Job> visibleJobs = new ArrayList<>();
		List<Job> allJobs = myJobList.getCopyList();

		// Check through myJobList and select all Jobs that the Volunteer can
		// sign up ("visible").
		for (Job job : allJobs) {
			if (visibleToVolunteer(theEmail, job)) {
				visibleJobs.add(job);
			}
		}

		return visibleJobs; // Return the list of visible Jobs.
	}

	/**
	 * Return a list of all jobs that the Volunteer is signed up for.
	 */
	public List<Job> getVolunteerJobs(String theEmail) {
		Volunteer volunteer = getVolunteer(theEmail);
		List<Job> volunteerJobs = new ArrayList<Job>();

		if (volunteer != null) {
			// Check every Job's Volunteer list for Volunteers that match the
			// given email address.
			for (Job job : myJobList.getCopyList()) {
				for (List<String> jobVolunteer : job.getVolunteerList()) {
					if (jobVolunteer.get(0).equals(theEmail)) {
						volunteerJobs.add(job);
					}
				}
			}
		}
		return volunteerJobs;
	}

	/**
	 * Return a list of all jobs associated with the ParkManager.
	 * 
	 * @author Taylor Gorman
	 */
	public List<Job> getManagerJobs(String theEmail) {
		ParkManager manager = getParkManager(theEmail);
		List<Job> managerJobs = new ArrayList<Job>();

		if (manager != null) {
			// Select all Jobs in JobList with the same name as a Park that
			// ParkManager manages.
			for (Job job : getJobListCopy()) {

				String jobParkName = job.getPark();
				if (manager.getManagedParks().contains(jobParkName)) {
					managerJobs.add(job);
				}
			}
		}
		return managerJobs;
	}

	/*
	 * ==============* Job Handling *==============
	 */

	/**
	 * Return a copy of the Job matching theJobID
	 * 
	 * @author Reid Thompson
	 */
	public Job getJobCopy(int theJobID) {
		Job jobToReturn = null;

		for (Job job : getJobListCopy()) {
			if (job.getJobID() == theJobID) {
				jobToReturn = job;
			}
		}
		return jobToReturn;
	}

	/**
	 * Return the next available Job ID to be used during the creation of a new
	 * job.
	 * 
	 * @author Taylor Gorman
	 */
	public int getNextJobID() {
		int nextID = 0;

		for (Job job : getJobListCopy()) {
			if (job.getJobID() >= nextID) {
				nextID = job.getJobID() + 1;
			}
		}
		return nextID;
	}

	/**
	 * Return a list of all Volunteers for a given job.
	 */
	public List<Volunteer> getJobVolunteerList(int theJobID) {

		List<Volunteer> volunteerList = new ArrayList<Volunteer>();
		Job job = myJobList.getJobCopy(theJobID);

		if (job != null) {
			for (ArrayList<String> volunteer : job.getVolunteerList()) {
				String volunteerEmail = volunteer.get(0);
				volunteerList.add(getVolunteer(volunteerEmail));
			}
		}

		return volunteerList;
	}

	/*
	 * ===============* User Handling *===============
	 */

	/**
	 * Returns true if a user with the given email already exists in the system,
	 * and false otherwise.
	 * 
	 * @author Reid Thompson - initial implementation and changed for User
	 *         functionality.
	 * @return true if a user with the given email already exists in the system,
	 * and false otherwise.
	 */
	public boolean checkEmail(String theEmail) {
		boolean result = false;
		List<User> allUsers = myUserList.getUserListCopy();

		for (int i = 0; i < allUsers.size(); i++) {
			final User u = allUsers.get(i);
			if (u.getEmail().equals(theEmail)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Return the work grade of a Volunteer for a given job.
	 * 
	 * @author Taylor Gorman - initial implementation
	 * @author Reid Thompson - single exit point of method.
	 */
	public String getVolunteerGrade(int theJobID, String theVolunteerEmail) {
		String gradeToReturn = null;

		for (ArrayList<String> volunteer : myJobList.getJobCopy(theJobID)
				.getVolunteerList()) {
			if (volunteer.get(0).equals(theVolunteerEmail)) {
				gradeToReturn = volunteer.get(1);
			}
		}

		return gradeToReturn;
	}

	/**
	 * Return the Park List associated with a ParkManager's e-mail.
	 * 
	 * @author Taylor Gorman - initial implementation
	 * @author Reid Thompson - added User functionality.
	 */
	public List<String> getParkList(String theEmail) {

		List<User> managerList = myUserList.getParkManagerListCopy();

		for (User manager : managerList) {
			if (manager.getEmail().equals(theEmail)) {
				return ((ParkManager) manager).getManagedParks();
			}
		}
		return new ArrayList<String>();
	}

	/*
	 * ==============* User Getters *==============
	 */

	/**
	 * Return the User that matches the given email address, and null if one
	 * does not exist.
	 */
	public User getUser(String theEmail) {
		List<User> userList = myUserList.getUserListCopy();

		for (User user : userList) {
			if (user.getEmail().equals(theEmail)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * A protected variation of getUser that ensures that the returned User is a
	 * Volunteer.
	 * 
	 * @author Taylor Gorman
	 * @param theEmail
	 *            The email address of the Volunteer.
	 * @return The Volunteer that matches the email, or null if such a Volunteer
	 *         does not exist.
	 */
	public Volunteer getVolunteer(String theEmail) {
		User volunteer = getUser(theEmail);
		
		if (volunteer != null && volunteer instanceof Volunteer) {
			return (Volunteer) volunteer;
		}
		return null;
	}

	/**
	 * A protected variation of getUser that ensures that the returned User is a
	 * ParkManager.
	 * 
	 * @author Taylor Gorman
	 * @param theEmail
	 *            The email address of the ParkManager.
	 * @return The ParkManager that matches the email, or null if such a
	 *         ParkManager does not exist.
	 */
	public ParkManager getParkManager(String theEmail) {
		User parkManager = getUser(theEmail);

		if (parkManager != null && parkManager instanceof ParkManager) {
			return (ParkManager) parkManager;
		}
		return null;
	}

	/**
	 * A protected variation of getUser that ensures that the returned User is a
	 * Administrator.
	 * 
	 * @author Taylor Gorman
	 * @param theEmail
	 *            The email address of the Administrator.
	 * @return The Administrator that matches the email, or null if such a
	 *         Administrator does not exist.
	 */
	public Administrator getAdministrator(String theEmail) {
		User administrator = getUser(theEmail);

		if (administrator != null && administrator instanceof Administrator) {
			return (Administrator) administrator;
		}
		return null;
	}

	/*
	 * ==============* List Getters *==============
	 */

	public List<Job> getJobListCopy() {
		return myJobList.getCopyList();
	}

	public List<User> getAllUserList() {
		return myUserList.getUserListCopy();
	}

	public List<User> getVolunteerListCopy() {
		return myUserList.getVolunteerListCopy();
	}

	public List<User> getManagerListCopy() {
		return myUserList.getParkManagerListCopy();
	}

	public List<User> getAdministratorListCopy() {
		return myUserList.getAdministratorListCopy();
	}

	public void setJobList(JobList theJobList) {
		this.myJobList = theJobList;
	}

	public void setUserList(UserList theUserList) {
		this.myUserList = theUserList;
	}

	/*
	 * ================* Helper Methods *================
	 */

	/*
	 * Return true if the job is visible to the volunteer; false otherwise.
	 */
	private boolean visibleToVolunteer(String theEmail, Job theJob) {
		List<Job> volunteerJobs = getVolunteerJobs(theEmail);
		boolean isVisible = true;

		// Volunteer is already signed up for the job.
		if (volunteerJobs.indexOf(theJob) != -1)
			isVisible = false;

		// The job has no room.
		if (!theJob.hasRoom())
			isVisible = false;

		// Volunteer has already signed up for a job on the same date.
		for (Job volunteerJob : volunteerJobs) {
			if (shareSameDate(volunteerJob, theJob))
				isVisible = false;
		}

		return isVisible;
	}

	/*
	 * Return true if the two jobs share a day; false otherwise.
	 */
	private boolean shareSameDate(Job job1, Job job2) {
		boolean hasSameDay = false;

		if (job1.getStartDate().equals(job2.getStartDate()))
			hasSameDay = true;
		if (job1.getStartDate().equals(job2.getEndDate()))
			hasSameDay = true;
		if (job1.getEndDate().equals(job2.getStartDate()))
			hasSameDay = true;
		if (job1.getEndDate().equals(job2.getEndDate()))
			hasSameDay = true;

		return hasSameDay;
	}
}
