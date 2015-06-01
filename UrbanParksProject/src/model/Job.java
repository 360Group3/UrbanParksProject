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
    
    public int getJobID() {
        return myJobID;
    }

    public List<List<String>> getVolunteerList() {
        return myVolunteerList;
    }

    public int getLightMax() {
        return myLightMax;
    }

    public int getLightCurrent() {
        return getNumberOfSlots(0);
    }

    public int getMediumMax() {
        return myMediumMax;
    }

    public int getMediumCurrent() {
        return getNumberOfSlots(1);
    }

    public int getHeavyMax() {
        return myHeavyMax;
    }

    public int getHeavyCurrent() {
        return getNumberOfSlots(2);
    }

    public String getPark() {
        return myPark;
    }

    public String getManager() {
        return myManager;
    }
    
    
    
    
    /*=============*
     * Check Slots *
     *=============*/

    /**
     * This method is called when someone needs to know if there are any more
     * light positions available.
     * 
     * @return true if there are light positions available, false otherwise.
     */
    public boolean hasLightRoom() {
        return (myLightMax - getNumberOfSlots(0)) > 0;
    }

    /**
     * This method is called when someone needs to know if there are any more
     * medium positions available.
     * 
     * @return true if there are medium positions available, false otherwise.
     */
    public boolean hasMediumRoom() {
        return (myMediumMax - getNumberOfSlots(1)) > 0;
    }

    /**
     * This method is called when someone needs to know if there are any more
     * heavy positions available.
     * 
     * @return true if there are heavy positions available, false otherwise.
     */
    public boolean hasHeavyRoom() {
        return (myHeavyMax - getNumberOfSlots(2)) > 0;
    }

    /**
     * This method is called when someone wants to know if there is room for
     * another volunteer in the job.
     * 
     * @return true if there are positions available, false otherwise.
     */
    public boolean hasRoom() {
        return hasLightRoom() || hasMediumRoom() || hasHeavyRoom();
    }
    
    
    
    
    /*============*
     * Check Date *
     *============*/
    
    public boolean isInPast() {
        return isPast;
    }

    public void setInPast(boolean theIsPastFlag) {
        this.isPast = theIsPastFlag;
    }
    
    
    
    /*===============*
     * Add Volunteer *
     *===============*/
    
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

    /**
     * Return the number of available job slots for a given work grade.
     * 
     * @param theGradeType
     *            0 for Light, 1 for Medium, 2 for Heavy
     * @return
     */
    private int getNumberOfSlots(int theGradeType) {
        int numLight = 0;
        int numMedium = 0;
        int numHeavy = 0;

        // Iterate Volunteer List and count up how many are in each job grade
        for (List<String> volunteer : myVolunteerList) {
            if (volunteer.get(1).equals("Light")) {
                numLight++;
            }
            if (volunteer.get(1).equals("Medium")) {
                numMedium++;
            }
            if (volunteer.get(1).equals("Heavy")) {
                numHeavy++;
            }
        }

        // Return the appropriate count.
        switch (theGradeType) {
            case 0:
                return numLight;
            case 1:
                return numMedium;
            case 2:
                return numHeavy;
            default:
                return 0;
        }
    }

    
    /**
     * Convert a date string to a Gregorian Calendar object. 
     * 
     * @param stringDate
     *            A string representing a date, of format mmddyyyy
     * @return A Gregorian Calendar object representing that date.
     * 
     * @author Taylor Gorman
     */
    private GregorianCalendar stringToCalendar(String stringDate) {
        int myDay = Integer.parseInt(stringDate.substring(0, 2));
        int myMonth = Integer.parseInt(stringDate.substring(2, 4));
        int myYear = Integer.parseInt(stringDate.substring(4, 8));

        return new GregorianCalendar(myYear, myDay, myMonth);
    }
}
