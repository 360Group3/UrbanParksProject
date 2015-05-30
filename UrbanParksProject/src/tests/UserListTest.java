package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import model.Administrator;
import model.ParkManager;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;


public class UserListTest {

    UserList myUL;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        myUL = new UserList();
    }

    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserForValidUser() {
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        assertTrue(myUL.getUserListCopy().contains(v));
    }

    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserForMultipleValidUsers() {
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        assertTrue(myUL.getUserListCopy().contains(v));
        
        Administrator a = new Administrator("email2@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(a);
        assertTrue(myUL.getUserListCopy().contains(a));
        
        ParkManager p = new ParkManager("email3@yahoo.com", "Lazy", "Naming", new ArrayList<>());
        myUL.addNewUser(p);
        assertTrue(myUL.getUserListCopy().contains(p));
        
        assertEquals(3, myUL.getUserListCopy().size());
    }

    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserForTwoUsersSameEmail() {
        Volunteer v = new Volunteer("email@yahoo.com", "Lazy", "Naming");
        myUL.addNewUser(v);
        assertTrue(myUL.getUserListCopy().contains(v));
        
        Administrator a = new Administrator("email@yahoo.com", "Lazy", "Naming");
        assertFalse(myUL.addNewUser(a));
//        assertFalse(myUL.getUserListCopy().contains(a));
        
        assertEquals(1, myUL.getUserListCopy().size());
    }
    
    /**
     * Test method for {@link model.UserList#addNewUser(model.User)}.
     */
    @Test
    public void testAddNewUserForInvalidUser() {
        Volunteer v = null;
        myUL.addNewUser(v);
        assertFalse(myUL.getUserListCopy().contains(v));
    }

    
    /**
     * Test method for {@link model.UserList#getUserTypeListCopy(java.lang.Class)}.
     */
    @Test
    public void testGetUserTypeListCopy() {
        fail("Not yet implemented");
    }

}
