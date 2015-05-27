package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;

/**
 * Handles both saving and loading from the jobList.ser and userList.ser files.
 * Used http://www.tutorialspoint.com/java/java_serialization.htm as a reference.
 * 
 * @author Taylor Gorman
 * @version 24 May 2015
 *
 */
public class SaveLoad {

    /*
     * ============*
     * Load Lists *
     * ============
     */

    /**
     * Read jobList.txt, and generate from it a JobList containing all Jobs and their
     * information.
     */
    public JobList loadJobList(String theFileName) {
        File inFile = getListFile(theFileName);
        JobList jobList = new JobList();

        try {
            if (inFile.createNewFile()) {
                return jobList;
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            FileInputStream fileIn = new FileInputStream(inFile.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            jobList = (JobList) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException e) {
            System.out
                    .println("Job List is currently empty. If this is not correct, please make sure that joblist.ser is "
                            + "in the correct location.");
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

        return jobList;
    }

    /**
     * Read userList.txt, and generate from it a UserList containing all Users and their
     * information.
     */
    public UserList loadUserList(String theFileName) {
        File inFile = getListFile(theFileName);
        UserList userList = new UserList();

        try {
            if (inFile.createNewFile()) {
                return userList;
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            FileInputStream fileIn = new FileInputStream(inFile.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userList = (UserList) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException e) {
            System.out
                    .println("User List is currently empty. If this is not correct, please make sure that userlist.ser is "
                            + "in the correct location.");
        }
        catch (ClassNotFoundException c) {
            c.printStackTrace();
        }

        return userList;
    }

    /*
     * Return the File to load/save job or user data to.
     * We make four different attempts, to account for iOS and Windows, on both Console
     * and Jar.
     * 
     * Return null in the case that the file could not be found.
     */
    private File getListFile(String fileName) {

        File dataFile;
        
        // Try Console
        dataFile = new File(fileName);
        if (dataFile.exists()) {
        	return dataFile;
        }
            

        File jarFile;

        // Generate path of the current file; used by the Jar file for detecting local
        // text files.
        try {
            jarFile = new File(SaveLoad.class.getProtectionDomain().getCodeSource()
                    .getLocation().toURI().getPath());
        }
        catch (URISyntaxException e1) {
            System.out
                    .println("Sorry, but this program could not detect its location. Please close the program and try again.");
            return null;
        }

        // Try Jar (iOS)
        jarFile = jarFile.getParentFile();
        dataFile = new File(jarFile.toString() + "/" + fileName);
        if (dataFile.exists())
            return dataFile;

        // Try Jar (Windows)
        dataFile = new File(jarFile.toString() + "\\" + fileName);

        return dataFile;
    }

    /*
     * ============*
     * Save Lists *
     * ============
     */

    /**
     * Parse a JobList and write its contents to a text file.
     */
    public boolean saveJobList(JobList theJobList, String theFileName) {
        File outFile = getListFile(theFileName);

        try {
            FileOutputStream fileOut = new FileOutputStream(outFile.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theJobList);
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
     * Parse a UserList and write its contents to a text file.
     */
    public boolean saveUserList(UserList theUserList, String theFileName) {
        File outFile = getListFile(theFileName);

        try {
            FileOutputStream fileOut = new FileOutputStream(outFile.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theUserList);
            out.close();
            fileOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
