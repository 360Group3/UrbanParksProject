package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class is used to create an instance of a Job.
 * @author Arsh
 * @version May 1
 */
public class Job {
	
	protected static int nextJobID = 0;
	
	/**
	 * This value is the identification number of a job.
	 */
	private int myJobID;

	/**
	 * This is the list which holds the e-mail addresses of all volunteers that have signed up to participate in this job.
	 */
    private ArrayList<ArrayList<String>> myVolunteerList;

    
    /**
     * This is the start date of the job.
     */
    private GregorianCalendar myStartDate;

    /**
     * This is the ending date of the job.
     */
    private GregorianCalendar myEndDate;

    /**
     * This value holds the maximum number of people who can sign up for the light portion
     * of this job.
     */
    private int myLightMax;

    /**
     * This value holds the current number of people who have signed for the light portion
     * of this job.
     */
    private int myLightCurrent;

    /**
     * This value holds the maximum number of people who can sign up for the medium portion
     * of this job.
     */
    private int myMediumMax;

    /**
     * This value holds the current number of people who have signed for the medium portion
     * of this job.
     */
    private int myMediumCurrent;

    /**
     * This value holds the maximum number of people who can sign up for the heavy portion
     * of this job.
     */
    private int myHeavyMax;

    /**
     * This value holds the current number of people who have signed for the heavy portion
     * of this job.
     */
    private int myHeavyCurrent;

    /**
     * This value holds the park that the job will be located at.
     */
    private Park myPark;

    /**
     * The email address of the Park Manager in charge of the job.
     */
    private String myManager;
    
    /**
     * Constructor for job, taking several arguments of integers, Calendar Strings, and e-mails.
     * @author Taylor Gorman
     */
    public Job(int theJobID, Park thePark, int theLightMax, int theMediumMax, int theHeavyMax, String theStartDate,
    		String theEndDate, String theManagerEmail, ArrayList<ArrayList<String>> theVolunteerList) {
    	
    	this.myJobID = theJobID;
    	this.myPark = thePark;
    	
    	this.myLightMax = theLightMax;
    	this.myMediumMax = theMediumMax;
       	this.myHeavyMax = theHeavyMax;
       	
    	this.myManager = theManagerEmail;
    	this.myVolunteerList = theVolunteerList;
    	
    	this.myLightCurrent = getNumberOfSlots(0);
    	this.myMediumCurrent = getNumberOfSlots(1);
    	this.myHeavyCurrent = getNumberOfSlots(2);
    	
    	this.myStartDate = stringToCalendar(theStartDate);
    	this.myEndDate = stringToCalendar(theEndDate);
    	
    }
    
    public Job(Park thePark, int theLightCurrent, int theLightMax, int theMediumCurrent, int theMediumMax,
    		int theHeavyCurrent, int theHeavyMax, String theStartDate, String theEndDate, String theManagerEmail, 
    		ArrayList<ArrayList<String>> theVolunteerList) {
    	
    	this.myJobID = nextJobID++;
    	this.myPark = thePark;
    	
    	this.myLightCurrent = theLightCurrent;
    	this.myLightMax = theLightMax;
    	this.myMediumCurrent = theMediumCurrent;
    	this.myMediumMax = theMediumMax;
    	this.myHeavyCurrent = theHeavyCurrent;
    	this.myHeavyMax = theHeavyMax;
    	
    	this.myStartDate = stringToCalendar(theStartDate);
    	this.myEndDate = stringToCalendar(theEndDate);
    	
    	this.myManager = theManagerEmail;
    	this.myVolunteerList = theVolunteerList;
    }
    
    /**
     * Convert a date string to a Gregorian Calendar object.
	 * @param stringDate A string representing a date, of format mmddyyyy
	 * @return A Gregorian Calendar object representing that date.
	 * 
	 * @author Taylor Gorman
	 * @version 9 May 2015
     */
    private GregorianCalendar stringToCalendar(String stringDate) {
		int myDay = Integer.parseInt(stringDate.substring(0, 2));
		int myMonth = Integer.parseInt(stringDate.substring(2, 4));
		int myYear = Integer.parseInt(stringDate.substring(4, 8));		
		
		return new GregorianCalendar(myYear, myDay, myMonth);
    }
    
    /**
     * This is used to return the starting date of the job.
     * @return myStartDate
     */
    public GregorianCalendar getStartDate() {
    	return myStartDate;
    }
    
    /**
     * This is used to return the ending date of the job.
     * @return myEndDate
     */
    public GregorianCalendar getEndDate() {
    	return myEndDate;
    }
    
    /**
     * This method is called when someone has signed up for a light portion of this job.
     * 
     */
    public void incrementLight() {
    	 if (myLightCurrent < myLightMax) {
    		 myLightCurrent ++;
    	 } else {
    		 System.out.println("Max has already been reached. Cannot increment.");
    	 }
    }

    /**
     * This method is called when someone has signed up for a medium portion of this job.
     * 
     */
    public void incrementMedium() {
    	if (myMediumCurrent < myMediumMax) {
    		myMediumCurrent ++;
    	} else {
    		System.out.println("Max has already been reached. Cannot increment.");
    	}
    }

    /**
     * This method is called when someone has signed up for a heavy portion of this job.
     * 
     */
    public void incrementHeavy() {
    	if (myHeavyCurrent < myHeavyMax) {
    		myHeavyCurrent ++;
    	} else {
    		System.out.println("Max has already been reached. Cannot increment.");
    	}
    }

    /**
     * This method is called when someone needs to know if there are any
     * more light positions available.
     * 
     * @return true if there are light positions available, false otherwise.
     */
    public boolean hasLightRoom()
    {
    	return (myLightMax - myLightCurrent) > 0;
    }
    
    /**
     * This method is called when someone needs to know if there are any
     * more medium positions available.
     * 
     * @return true if there are medium positions available, false otherwise.
     */
    public boolean hasMediumRoom()
    {
    	return (myMediumMax - myMediumCurrent) > 0;
    }
    

    /**
     * This method is called when someone needs to know if there are any
     * more heavy positions available.
     * 
     * @return true if there are heavy positions available, false otherwise.
     */
    public boolean hasHeavyRoom()
    {
    	return (myHeavyMax - myHeavyCurrent) > 0;
    }

    /**
     * This method is called when someone wants to know if there is room for another volunteer in the job.
     * 
     * @return true if there are positions available, false otherwise.
     */
    public boolean hasRoom()
    {
    	return (myLightMax - myLightCurrent) == 0
    			&& (myMediumMax + myMediumCurrent) == 0 
    			&& (myHeavyMax - myHeavyCurrent) == 0;
    }
    
    /**
     * Return the number of available job slots for a given work grade.
     * @param theGradeType 0 for Light, 1 for Medium, 2 for Heavy
     * @return
     */
    public int getNumberOfSlots(int theGradeType) {
    	int numLight = 0;
    	int numMedium = 0;
    	int numHeavy = 0;
    	
    	//Iterate Volunteer List and count up how many are in each job grade
    	for(ArrayList<String> volunteer : myVolunteerList) {
    		if(volunteer.get(1).equals("Light")) {
    			numLight++;
    		}
    		if(volunteer.get(1).equals("Medium")) {
    			numMedium++;
    		}
    		if(volunteer.get(1).equals("Heavy")) {
    			numHeavy++;
    		}
    	}
    	
    	//Return the appropriate count.
    	switch(theGradeType) {
    		case 0: return numLight;
    		case 1: return numMedium;
    		case 2: return numHeavy;
    		default: return 0;
    	}
    }

    public static void setNextJobID(int theID){
    	nextJobID = theID;
    }
    
	public int getJobID() {
		return myJobID;
	}

	public ArrayList<ArrayList<String>> getVolunteerList() {
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

	public Park getPark() {
		return myPark;
	}
	
	public String getManager() {
		return myManager;
	}
	
	@Override
	public String toString()
	{
		return myPark.toString();
	}
}
