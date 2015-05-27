package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.businessRules.BusinessRule1;
import model.businessRules.BusinessRule2;
import model.businessRules.BusinessRule4;

/**
 * Defines the Schedule object for an application.
 * This entity contains the master job list and enforces
 * business rules associated with its role in the program.
 * 
 * @author Reid Thompson
 * @version 5.3.2015
 */
public class Schedule implements Serializable {

    private static final long serialVersionUID = 5L;

    private static Schedule schedule = new Schedule();
    private static final int MAX_TOTAL_NUM_JOBS = Integer.MAX_VALUE;
    private JobList myJobList;
    private UserList myUserList;

    /**
     * Constructs a Schedule object.
     */
    private Schedule() {
        // Not to be instantiated; Singleton
    }

    public static Schedule getInstance() {
        return schedule;
    }

    /**
     * Receives a job and adds it to the master job list if its data is valid.
     * 
     * For user story #1. Called by createJob() method of ParkManager class.
     * 
     * @param theJob
     *            is the Job to potentially add to Schedule's list of Jobs.
     * @return true if theJob was added, and false otherwise.
     */
    public boolean receiveJob(Job theJob) {
        Calendar now = new GregorianCalendar();
        Calendar then = new GregorianCalendar();
        then.add(Calendar.DAY_OF_MONTH, 90);

        // Checks the fields of the object to make sure they're valid.
        boolean okToAdd = true;

        // BIZ rule 1. A job may not be added if the total number of pending jobs is
        // currently 30.
        if (!(new BusinessRule1().test(myJobList))) {
            okToAdd = false;
        }
        // BIZ rule 2. A job may not be added if the total number of pending jobs during
        // that week
        // (3 days on either side of the job days) is currently 5.
        else if (!(new BusinessRule2().test(theJob, myJobList))) {
            okToAdd = false; // this is entered if checkThisWeek() returns false;
        }

        // BIZ rule 4. A job may not be scheduled that lasts more than two days.
        else if (!(new BusinessRule4().test(theJob))) {
            okToAdd = false;
        }

        // BIZ rule 5. A job may not be added that is in the past or more than three
        // months in the future.
        // I am going to say that the manager can only create a job on a date after today.
        else if (!theJob.getStartDate().after(now)) {
            okToAdd = false;
        }
        else if (theJob.getStartDate().after(then)) {
            okToAdd = false;
        }

        // checks if date range is valid
        else if (theJob.getStartDate().after(theJob.getEndDate())) {
            okToAdd = false;
        }

        // checks that the job id is valid
        else if (theJob.getJobID() < Integer.MIN_VALUE
                || theJob.getJobID() > Integer.MAX_VALUE) {
            okToAdd = false;
        }

        // checks if volunteer list is null
        else if (theJob.getVolunteerList() == null) {
            okToAdd = false;
        }

        // checks if volunteer list is empty
        else if (!theJob.getVolunteerList().isEmpty()) {
            okToAdd = false;
        }

        // removed myIsRequest if-statement from here.

        // NOTE: The problem with this if-statement is that it will be entered if any
        // of Light, Medium, or Heavy has 0 spaces.
        // Some jobs will have zero space for one or two of these levels which means when
        // that job is passed in, it will always enter this if-statement and return
        // false....not good.

        // I made changed here! I changed all the or (||) to and (&&). Now the
        // if-statement wont be entered
        // unless there is zero room in each job level. Arsh.
        else if (!theJob.hasLightRoom() && !theJob.hasMediumRoom()
                && !theJob.hasHeavyRoom()) {
            okToAdd = false;
        }

        else if (theJob.getLightCurrent() > theJob.getLightMax()
                || theJob.getMediumCurrent() > theJob.getMediumMax()
                || theJob.getHeavyCurrent() > theJob.getHeavyMax()) {
            okToAdd = false;
        }

        else if (theJob.getPark() == null) {
            okToAdd = false;
        }

        else if (theJob.getManager() == null) {
            okToAdd = false;
        }

        if (okToAdd) {
            // To get the master job list which is editable
            List<Job> editableJobList = myJobList.getJobList();
            editableJobList.add(theJob); // add valid job to list
        }
        else {
            throw new IllegalArgumentException("Error: job data is invalid and therefore was not added.");
        }

        return okToAdd;
    }

    /**
     * Adds a Volunteer to an existing Job if the given data is valid.
     * 
     * For User Story #3. Called by Volunteer.signup()
     * 
     * @param theVolunteer
     *            the Volunteer to add to a particular job as well as the workgrade.
     * @param theJobID
     *            the ID number for the Job to which the Volunteer will be added.
     *            I'm assuming this value would be 1 = light, 2 = medium, or 3 = heavy.
     * @return true if the Volunteer was added to the Job and false otherwise.
     * @throws IllegalArgumentException if one of the arguments has an illegal value
     */
    public boolean addVolunteerToJob(ArrayList<String> theVolunteer, int theJobID)
            throws IllegalArgumentException {
        // CHECK 1
        boolean validID = checkJobValidity(theJobID); // Schedule will check to make sure
                                                      // the Job ID is valid
        if (!validID) {
            throw new IllegalArgumentException("This job ID does not exist");
        }

        // CHECK 2
        Job thisJob = findJob(theJobID);
        if (thisJob == null) {
            throw new IllegalArgumentException("Job does not exist");
        }
        // CHECK 3
        boolean sameDate = checkDifDay(theVolunteer, thisJob);
        if (sameDate) {
            throw new IllegalArgumentException("Sorry, but you are already signed up "
                    + "for a job that occurs the same date!");
        }
        // CHECK 4
        boolean inFuture = checkFutureValid(thisJob);
        if (!inFuture) {
            throw new IllegalArgumentException(
                    "Sorry, but this job has already completed.");
        }

        // CHECK 5
        boolean openGrade = checkGradeOpen(thisJob, theVolunteer.get(1));
        if (!openGrade) {
            throw new IllegalArgumentException("Sorry, but that grade in this job "
                    + "is already full.");
        }

        // If all the checks pass, we add the Volunteer to the Job's Volunteer List,
        // Increment the grade slot, and return.
        thisJob.getVolunteerList().add(theVolunteer);
        return true;
    }

    /**
     * Schedule will check to make sure the Job ID is valid
     * 
     * @param theJobID
     *            is the id number of the job.
     * @return false if invalid
     */
    private boolean checkJobValidity(int theJobID) {
        if (theJobID < 0 || theJobID > MAX_TOTAL_NUM_JOBS) {
            return false;
        }
        return true;
    }

    /**
     * Business rule #7
     * 
     * 
     * This method checks to make sure that the volunteer has not signed up for a job
     * on that same day.
     * 
     * @param theVolunteer
     *            is the volunteer with their work grade
     * @param theJobID
     *            is the jobs ID number
     * @return false if the user already has another job on that day.
     */
    private boolean checkDifDay(ArrayList<String> theVolunteer, Job theJob) {

        GregorianCalendar startDate = theJob.getStartDate();
        GregorianCalendar endDate = theJob.getEndDate();

        for (Job job : myJobList.getCopyList()) {
            for (ArrayList<String> volunteer : job.getVolunteerList()) {
                if (volunteer.get(0).equals(theVolunteer.get(0))) {
                    // Found a job with the volunteer in it!
                    if (startDate.equals(job.getStartDate()))
                        return true;
                    if (startDate.equals(job.getEndDate()))
                        return true;
                    if (endDate.equals(job.getStartDate()))
                        return true;
                    if (endDate.equals(job.getEndDate()))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Business rule #6.
     * 
     * This method is called to check whether or not the job with the
     * passed in jobID is in the future or not.
     * 
     * 
     * @param theID
     *            is the ID of the job.
     * @return false if job is not in future, true otherwise.
     */
    private boolean checkFutureValid(Job theJob) {

        Calendar currentDate = new GregorianCalendar();

        if (currentDate.getTimeInMillis() + 2670040009l > theJob.getStartDate()
                .getTimeInMillis()) {
            return false;
        }

        return true;
    }

    /**
     * Finds the job associated with this jobID.
     * 
     * @param theJobID
     *            is the jobs ID
     * @return the Job that has this Job ID, null otherwise.
     */
    private Job findJob(int theJobID) {
        // Calls JobList.getJobList() to get the master job list which is editable
        List<Job> editableJobList = myJobList.getJobList();

        for (int i = 0; i < editableJobList.size(); i++) {
            if (editableJobList.get(i).getJobID() == theJobID) {
                return editableJobList.get(i);
            }
        }
        return null;
    }

    /**
     * Business rule #3
     * 
     * Checks to make sure that the job grade chosen has an available slot.
     * 
     * @param j
     *            is the job
     * @param theGrade
     *            is the grade in that job
     * @return true if open slot, false otherwise.
     */
    private boolean checkGradeOpen(Job j, String theGrade) {
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
                throw new IllegalStateException(theGrade + " for job " + j.getJobID()
                        + " is full");
        }
        return false;
    }

    /**
     * Get a copy of the list of jobs.
     * @return a copy of the list in myJobList
     */
    public List<Job> getJobList() {
        return myJobList.getCopyList();
    }

    /**
     * Create a User and add it to the UserList
     * @param theEmail the email
     * @param theFirstName the first name
     * @param theLastName the last name
     * @param theUserType the user type
     */
    public void addUser(String theEmail, String theFirstName, String theLastName,
                        String theUserType) {

        User u = null;
        switch (theUserType) {
            
            case "Volunteer":
                u = new Volunteer(theEmail, theFirstName, theLastName);
                break;
                
            case "Administrator":
                u = new Administrator(theFirstName, theLastName, theEmail);
                break;

            case "ParkManager":
                u = new ParkManager(theEmail, theFirstName, theLastName,
                        new ArrayList<String>());
                break;
            default:
                throw new IllegalArgumentException("Not a valid user type.");
        }
        myUserList.addNewUser(u);
    }

    /**
     * Update the list of managed parks of a Park Manager.
     * 
     * @author Taylor Gorman
     */
    public void updateParkList(String theEmail, List<String> theManagedParks) {
        List<User> myManagerList = myUserList.getParkManagerListCopy();

        for (User manager : myManagerList) {
            if (manager.getEmail().equals(theEmail)) {
                ((ParkManager) manager).setManagedParks(theManagedParks);
                return; //end when a manager is found
            }
        }

    }

    public void setJobList(JobList theJobList) {
        this.myJobList = theJobList;
    }

    public void setUserList(UserList theUserList) {
        this.myUserList = theUserList;
    }

}
