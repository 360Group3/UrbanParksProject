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
	
	private static int nextJobID = 0;
	
	/**
	 * This value is the identification number of a job.
	 */
	private int myJobID;

	/**
	 * This is the list which holds the volunteers that have signed up to participate in this job.
	 */
    public List<Volunteer> myVolunteerList; 
    
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
     * The Park Manager in charge of the job.
     */
    private ParkManager myManager;
    
    /**
     * This is a constructor.
     * @param thePark is the park 
     * @param theLightSlots is the max number of light positions
     * @param theMediumSlots is the max number of medium positions
     * @param theHeavySlots is the max number of heavy positions
     * @param theStartDate is the starting date of the job
     * @param theEndDate is the ending date of the job.
     */
    public Job(Park thePark, int theLightSlots, int theMediumSlots, int theHeavySlots,
    		GregorianCalendar theStartDate, GregorianCalendar theEndDate, ParkManager theManager) {
    	myJobID = nextJobID++;
    	myVolunteerList = new ArrayList<>();
    	myStartDate = theStartDate;
    	myEndDate = theEndDate;
    	myPark = thePark;
    	myManager = theManager;
    	
    	myLightMax = theLightSlots;
    	myLightCurrent = 0;
    	myMediumMax = theMediumSlots;
    	myMediumCurrent = 0;
    	myHeavyMax = theHeavySlots;
    	myHeavyCurrent = 0;
    }
    
	public static void setNextJobID(int theID)
	{
		nextJobID = 0;
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

	public int getJobID() {
		return myJobID;
	}

	public List<Volunteer> getVolunteerList() {
		return myVolunteerList;
	}

	public int getLightMax() {
		return myLightMax;
	}

	public int getLightCurrent() {
		return myLightCurrent;
	}

	public int getMediumMax() {
		return myMediumMax;
	}

	public int getMediumCurrent() {
		return myMediumCurrent;
	}

	public int getHeavyMax() {
		return myHeavyMax;
	}

	public int getHeavyCurrent() {
		return myHeavyCurrent;
	}

	public Park getPark() {
		return myPark;
	}
	
	public ParkManager getManager() {
		return myManager;
	}
	
	@Override
	public String toString()
	{
		return myPark.toString();
	}
}