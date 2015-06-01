package tests.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.DataPollster;
import model.User;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

public class AdministratorTest {

    Administrator myAdmin;
    UserList myUserList;

    @Before
    public void setUp() {
        myAdmin = new Administrator("americangirl@heartbreakers.net", "Tom", "Petty");

        myUserList = new UserList();
        myUserList.addNewUser(new Volunteer("thenews@ifthisis.it", "Huey", "Lewis"));
        myUserList.addNewUser(new Volunteer("awesome@goodwill.org", 
                                                 "Ryan", "Lewis"));
        myUserList.addNewUser(new Volunteer("cocoon@arrows.co.uk", 
                                                 "Polly", "Scattergood"));
        myUserList.addNewUser(new Volunteer("takealook@me.now", "Phil", "Collins"));
        myUserList.addNewUser(new Volunteer("pedestrian@best.net", 
                                                 "Courtney", "Barnett"));
        DataPollster.getInstance().setUserList(myUserList);
    }

    @Test
    public void testGetMatchingVolunteersOnSingleResult() {
        List<User> u = new ArrayList<>();
        u.add(myUserList.getVolunteerListCopy().get(2));

        assertEquals(u, myAdmin.getMatchingVolunteers("Scattergood"));

        u = new ArrayList<>();
        u.add(myUserList.getVolunteerListCopy().get(4));

        assertEquals(u, myAdmin.getMatchingVolunteers("Barnett"));
    }

    @Test
    public void testGetMatchingVolunteersOnMultipleResults() {
        List<User> u = new ArrayList<>();
        u.add(myUserList.getVolunteerListCopy().get(0));
        u.add(myUserList.getVolunteerListCopy().get(1));

        assertEquals(u, myAdmin.getMatchingVolunteers("Lewis"));
    }
    
    @Test
    public void testGetMatchingVolunteersOnEmptyMatchingVols() {
    	assertEquals("Given last name for which there are no Volunteers, the list of "
    			+ "matching Volunteers should be empty!", new ArrayList<>(),
    			myAdmin.getMatchingVolunteers("Smith"));
    }

    @Test
    public void testGetAllVolunteersByLNFNOnAlphabetizedUsers() {
        List<User> alphabetizedUsers = new ArrayList<>();
        alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(4));
        alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(3));
        alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(0));
        alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(1));
        alphabetizedUsers.add(myUserList.getVolunteerListCopy().get(2));

        assertEquals(alphabetizedUsers, myAdmin.getSortedVolunteers());
    }

}
