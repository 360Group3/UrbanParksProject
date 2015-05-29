package tests;

import static org.junit.Assert.*;
import model.User;
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
