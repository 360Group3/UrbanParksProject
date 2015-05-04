package arsh_360;

/**
 * This class is an instance of a park.
 * @author Arsh
 * @version May 1
 *
 */
public class Park {
	
	/**
	 * This holds the name of the park.
	 */
	public String myName;

	/**
	 * This holds the value of the location (the city) of the park.
	 */
    public String myLocation;

    /**
     * This holds the value of the zip code that the park is located in.
     */
    public int myZipCode;

    
    
    /**
     * This is a constructor.
     * @param theName is the name of the park
     * @param theLoc is the city of the park
     * @param theZip is the zip code of the location
     */
    public Park(String theName, String theLoc, int theZip) {
    	myName = theName;
    	myLocation = theLoc;
    	myZipCode = theZip;
    }
    
    
    //TODO: Maybe we should create getters so that we can encapsulate
    		//our data?
}
