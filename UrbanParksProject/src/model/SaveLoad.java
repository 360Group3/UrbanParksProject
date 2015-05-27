package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;

/*
 * Used http://www.tutorialspoint.com/java/java_serialization.htm as a reference
 * for how to handle serialization in Java.
 */

/**
 * Handles both saving and loading from the jobList.ser and userList.ser files.
 * 
 * @author Taylor Gorman
 * @version 26 May 2015
 */
public class SaveLoad {

    /*
     * ============*
     * Load Lists *
     * ============
     */
	
	
	/*
	 * loadJobList() and loadUserList() are very similar, but are kept separated into two methods.
	 * 
	 * This is because not a lot of work is really being done in them, and much of it involves directly
	 * handling a JobList or UserList object. If we were to use one method, it would be bloated with if statements.
	 * 
	 * Furthermore, much of the work is done in try/catch blocks, which combined with if statements, would
	 * become much more complicated than they need to be.
	 */
	
	

    /**
     * Read the Job List file, and generate from it a JobList containing all Jobs and their
     * information.
     * 
     * @param theFileName The full file name of the Job List, i.e. "jobList.ser"
     */
    public JobList loadJobList(String theFileName) {
    	
    	//Get the path for the File.
        File inFile = getListFile(theFileName);
        JobList jobList = new JobList();

      //Check if the file exists. If it does not, then return an empty Job List.
        try {
            if (inFile.createNewFile()) {
            	//Job List File does not exist.
                return jobList;
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

      //If the Job List File exists, then we read its contents into the Job List with ObjectInputStream.
        try {
            FileInputStream fileIn = new FileInputStream(inFile.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            jobList = (JobList) in.readObject(); //Read the contents.
            in.close();
            fileIn.close();
        }
        catch (IOException e) {
        	//The Job List File exists, but is empty. This is a normal occurrence.
            System.out.println("Job List is currently empty. If this is not correct,"
            		+ " please make sure that joblist.ser is in the correct location.");
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

        return jobList;
    }
    

    /**
     * Read the User List file, and generate from it a UserList containing all Users and their
     * information.
     * 
     * @param theFileName The full file name of the User List, i.e. "userList.ser"
     */
    public UserList loadUserList(String theFileName) {
    	
    	//Get the path for the file.
        File inFile = getListFile(theFileName);
        UserList userList = new UserList();

        //Check if the file exists. If it does not, then return an empty User List.
        try {
            if (inFile.createNewFile()) {
            	//User List File does not exist.
                return userList;
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        //If the User List File exists, then we read its contents into the User List with ObjectInputStream.
        try {
            FileInputStream fileIn = new FileInputStream(inFile.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userList = (UserList) in.readObject(); //Read the contents.
            in.close();
            fileIn.close();
        }
        catch (IOException e) {
        	//The User List File exists, but is empty. This is a normal occurrence.
            System.out.println("User List is currently empty. If this is not correct,"
            		+ " please make sure that userlist.ser is in the correct location.");
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

        return userList;
    }

    
    
    
    /*============*
     * Save Lists *
     *============*/
    
    
    /*
     * saveJobList() and saveUserList() are kept separated for the same reasons that loadJobList() and
     * loadUserList() are: 
     * They are simple methods on their own, and would be made unnecessarily complicated
     * if they were to be merged together.
     */    
    

    /**
     * Save the serialization of the JobList into a .ser file
     * 
     * @return true if the JobList was successfully saved; false otherwise.
     * @param theFileName The full file name to be saved to, i.e. "jobList.ser"
     */
    public boolean saveJobList(JobList theJobList, String theFileName) {
    	
    	//Get the path of the file to be saved to.
        File outFile = getListFile(theFileName);

        //Attempt to write to the file with ObjectOutputStream.
        try {
            FileOutputStream fileOut = new FileOutputStream(outFile.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theJobList); //Write the contents.
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Save the serialization of the UserList into a .ser file
     * 
     * @return true if the UserList was successfully saved; false otherwise.
     * @param theFileName The full file name to be saved to, i.e. "userList.ser"
     */
    public boolean saveUserList(UserList theUserList, String theFileName) {
    	
    	//Get the path of the file to be saved to.
        File outFile = getListFile(theFileName);

        //Attempt to write to the file with ObjectOutputStream.
        try {
            FileOutputStream fileOut = new FileOutputStream(outFile.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theUserList); //Write the contents.
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    
    
    
    /*================*
     * Helper Classes *
     *================*/
    
    /*
     * Return the File to load/save job or user data to.
     * We make three different attempts. The first is for Console, the second is for a Jar on iOS, and
     * the last is for a Jar on Windows.
     * 
     * Return null in the case that the file could not be found.
     */
    private File getListFile(String fileName) {

        // Try Console
    	File dataFile = new File(fileName);
        if (dataFile.exists()) {
        	return dataFile;
        }
        
        //If Console did not work, then we assume that they are using a Jar file.
        File jarFile = getJarFile();        
        if(jarFile != null) {
        	
            //Try IoS
            jarFile = jarFile.getParentFile();
            dataFile = new File(jarFile.toString() + "/" + fileName);
            if (dataFile.exists()) {
            	return dataFile;
            }

            //Try Windows
            dataFile = new File(jarFile.toString() + "\\" + fileName);
            return dataFile;
        }
        
        return null;
    }
    
    
    /*
     * Find the path of the Jar file, which is then used to access local files.
     */
    private File getJarFile() {
        File jarFile = null;
        
        try {
        	//Get the path.        	
            jarFile = new File(SaveLoad.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath());
        }
        catch (URISyntaxException e1) {
        	//toURI() failed, meaning the Jar file location could not be found.
            System.out.println("Sorry, but this program could not detect its location. "
            		+ "Please close the program and try again.");
        }
        
        return jarFile;
    }    
}
