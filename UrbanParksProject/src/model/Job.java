package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class is used to create an instance of a Job.
 * 
 * @author Arsh
 * @version May 1
 */
public class Job implements Serializable {

	//Class Constants
    private static final long serialVersionUID = 2L;
    public static final int MAX_NUM_JOBS = 10000;
    
    //Class Variables
    private int myJobID;
    private List<List<String>> myVolunteerList;
    
    private GregorianCalendar myStartDate;
    private GregorianCalendar myEndDate;
    
    private int myLightMax;
    private int myMediumMax;
    private int myHeavyMax;
    
    private String myPark;
    private String myManager;
    
    private boolean isPast;

    /**
     * Constructor for Job, taking several arguments of integers, Calendar
     * Strings, and e-mails. 
     * @author Taylor Gorman
     */
    public Job(int theJobID, String thePark, int theLightMax, int theMediumMax,
            int theHeavyMax, String theStartDate, String theEndDate,
            String theManagerEmail, List<List<String>> theVolunteerList) {

        this.myJobID = theJobID;
        this.myPark = thePark;

        this.myLightMax = theLightMax;
        this.myMediumMax = theMediumMax;
        this.myHeavyMax = theHeavyMax;

        this.myManager = theManagerEmail;
        this.myVolunteerList = theVolunteerList;

        this.myStartDate = stringToCalendar(theStartDate);
        this.myEndDate = stringToCalendar(theEndDate);
        
        this.isPast = false;
    }


    
    /*=========*
     * Getters *
     *=========*/

    /**
     * Return the starting date of the Job as a GregorianCalendar.
     */
    public GregorianCalendar getStartDate() {
        return myStartDate;
    }

    /**
     * Return the ending date of the Job as a Greogriant Calendar.
     */
    public GregorianCalendar getEndDate() {
        return myEndDate;
    }
    
    /**
     * Return the unique Job ID of the Job.
     */
    public int getJobID() {
        return myJobID;
    }

    /**
     * Return the list of Volunteers for the Job.
     */
    public List<List<String>> getVolunteerList() {
        return myVolunteerList;
    }

    /**
     * Return the maximum amount of Volunteers that can sign up for Light Grade work.
     */
    public int getLightMax() {
        return myLightMax;
    }

    /**
     * Return the amount of Volunteers that have signed up for Light Grade work.
     */
    public int getLightCurrent() {
        return getNumberOfSlots("Light");
    }

    /**
     * Return the maximum amount of Volunteers that can sign up for Medium Grade work.
     */
    public int getMediumMax() {
        return myMediumMax;
    }

    /**
     * Return the amount of Volunteers that have signed up for Medium Grade work.
     */
    public int getMediumCurrent() {
        return getNumberOfSlots("Medium");
    }

    /**
     * Return the maximum amount of Volunteers that can sign up for Heavy Grade work.
     */
    public int getHeavyMax() {
        return myHeavyMax;
    }

    /**
     * Return the amount of Volunteers that have signed up for Heavy Grade work.
     */
    public int getHeavyCurrent() {
        return getNumberOfSlots("Heavy");
    }

    /**
     * Return the Park that the Job is located at.
     */
    public String getPark() {
        return myPark;
    }

    /**
     * Return the ParkManager for the Job.
     */
    public String getManager() {
        return myManager;
    }
    
    
    
    
    /*=============*
     * Check Slots *
     *=============*/

    /**
     * Return true if there are slots for Light Grade work available; false otherwise 
     */
    public boolean hasLightRoom() {
        return (myLightMax - getNumberOfSlots("Light")) > 0;
    }

    /**
     * Return true if there are slots for Medium Grade work available; false otherwise 
     */
    public boolean hasMediumRoom() {
        return (myMediumMax - getNumberOfSlots("Medium")) > 0;
    }

    /**
     * Return true if there are slots for Heavy Grade work available; false otherwise 
     */
    public boolean hasHeavyRoom() {
        return (myHeavyMax - getNumberOfSlots("Heavy")) > 0;
    }

    /**
     * Return true if there are slots for any grade of work available; false otherwise 
     */
    public boolean hasRoom() {
        return hasLightRoom() || hasMediumRoom() || hasHeavyRoom();
    }
    
    
    
    
    /*============*
     * Check Date *
     *============*/
    
    /**
     * Return true if the Start Date of the Job is in the past.
     */
    public boolean isInPast() {
        return isPast;
    }

    /**
     * Set whether or not the Start Date of the Job is in the past.
     */
    public void setInPast(boolean theIsPastFlag) {
        this.isPast = theIsPastFlag;
    }
    
    
    
    /*===============*
     * Add Volunteer *
     *===============*/
    
    /**
     * Add a List<String> to the Volunteer List for a job.<br>
     * The first String of the List is the e-mail of the Volunteer; the second String is the grade of work that
     * the Volunteer wants to work.
     */
    public void addVolunteer(List<String> theVolunteer) {
        myVolunteerList.add(theVolunteer);
    }
    
    
    
    
    /*================*
     * Helper Classes *
     *================*/
    
    @Override
    public String toString() {
        return myPark.toString();
    }

    /*
     * Return the number of available job slots for a given work grade.
     */
    private int getNumberOfSlots(String theGradeType) {
        int numberInSlot = 0;

        //For each Volunteer in the Volunteer List matching the Grade, we increment the return value.
        for (List<String> volunteer : myVolunteerList) {
            if (volunteer.get(1).equals(theGradeType)) {
            	numberInSlot++;
            }
        }
        
        return numberInSlot;
    }

    
    /*
     * Convert a date string of format mmddyyyy to a GregorianCalendar object, which is returned.
     */
    private GregorianCalendar stringToCalendar(String stringDate) {
        int myDay = Integer.parseInt(stringDate.substring(0, 2));
        int myMonth = Integer.parseInt(stringDate.substring(2, 4));
        int myYear = Integer.parseInt(stringDate.substring(4, 8));

        return new GregorianCalendar(myYear, myDay, myMonth);
    }
}
