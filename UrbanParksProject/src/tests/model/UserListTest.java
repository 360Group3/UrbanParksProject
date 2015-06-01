package tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.ParkManager;
import model.User;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;


public class UserListTest {

    UserList myUL;
    
    /**
     * Initializes test data for the following test methods.
     */
    @Before
    public void setUp() {
        myUL = new UserList();
    }

    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserOnValidUser() {
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        assertTrue(myUL.getUserListCopy().contains(v));
    }

    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserOnMultipleValidUsers() {
        UserList testList = new UserList();
        
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        testList.addNewUser(v);
        assertTrue(myUL.getUserListCopy().contains(v));
        
        Administrator a = new Administrator("email2@yahoo.com", "Second", "Naming");
        myUL.addNewUser(a);
        testList.addNewUser(a);
        assertTrue(myUL.getUserListCopy().contains(a));
        
        ParkManager p = new ParkManager("email3@yahoo.com", "Third", "Naming", new ArrayList<>());
        myUL.addNewUser(p);
        testList.addNewUser(p);
        assertTrue(myUL.getUserListCopy().contains(p)); 
        
        assertEquals(testList, myUL);
        assertEquals("Size of list should be 3", 3, myUL.getUserListCopy().size());
    }

    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserOnTwoUsersSameEmail() {
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        assertTrue(myUL.getUserListCopy().contains(v));
        
        Administrator a = new Administrator("email@yahoo.com", "Lazy", "Naming");
        assertFalse(myUL.addNewUser(a));
        
        assertEquals(1, myUL.getUserListCopy().size());
    }
    
    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserOnInvalidUser() {
        Volunteer v = null;
        myUL.addNewUser(v);
        assertFalse(myUL.getUserListCopy().contains(v));
    }

    @Test
    public void testGetAdministratorListCopyOnEmpty() {
        assertEquals(new ArrayList<>(), myUL.getAdministratorListCopy());
    }
    
    @Test
    public void testGetAdministratorListCopyOnFilled() {
        List<User> testList = new ArrayList<>();
        
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        
        Administrator a = new Administrator("email2@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(a);
        testList.add(a);
        
        ParkManager p = new ParkManager("email3@yahoo.com", "Lazy", "Naming", new ArrayList<>());
        myUL.addNewUser(p);
        
        assertEquals(testList, myUL.getAdministratorListCopy());
    }

    @Test
    public void testGetVolunteerListCopyOnEmpty() {
        assertEquals(new ArrayList<>(), myUL.getVolunteerListCopy());
    }
    
    @Test
    public void testGetVolunteerListCopyOnFilled() {
        List<User> testList = new ArrayList<>();
        
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        testList.add(v);
        
        Administrator a = new Administrator("email2@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(a);
        
        ParkManager p = new ParkManager("email3@yahoo.com", "Lazy", "Naming", new ArrayList<>());
        myUL.addNewUser(p);
        
        assertEquals(testList, myUL.getVolunteerListCopy());
    }
    
    @Test
    public void testGetParkManagerListCopyOnEmpty() {
        assertEquals(new ArrayList<>(), myUL.getParkManagerListCopy());
    }
    
    @Test
    public void testGetParkManagerListCopyOnFilled() {
        List<User> testList = new ArrayList<>();
        
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        
        Administrator a = new Administrator("email2@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(a);
        
        ParkManager p = new ParkManager("email3@yahoo.com", "Lazy", "Naming", new ArrayList<>());
        myUL.addNewUser(p);
        testList.add(p);
        
        assertEquals(testList, myUL.getParkManagerListCopy());
    }
}
