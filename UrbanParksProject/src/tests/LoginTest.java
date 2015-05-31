package tests;

import static org.junit.Assert.*;
import model.DataPollster;
import model.Login;
import model.Schedule;
import model.UserList;

import org.junit.Before;
import org.junit.Test;

public class LoginTest {

    Login myLogin;
    
    UserList myUL;
    
    @Before
    public void setUp() throws Exception {
        myLogin = new Login();
        
        myUL = new UserList();

        DataPollster.getInstance().setUserList(myUL);
        Schedule.getInstance().setUserList(myUL);
    }

    @Test
    public void testRegisterUserForInvalidUserInfo() {
        myLogin.setUserInfoRegister(null, "email@yo.com", "Mimiru", "Tsukasa", "Volunteer");
        assertFalse(myLogin.registerUser());
        
        myLogin.setUserInfoRegister("Foo", null, "Mimiru", "Tsukasa", "Volunteer");
        assertFalse(myLogin.registerUser());
        
        myLogin.setUserInfoRegister("Foo", "email@yo.com", null, "Tsukasa", "Volunteer");
        assertFalse(myLogin.registerUser());

        myLogin.setUserInfoRegister("Foo", "email@yo.com", "Mimiru", null, "Volunteer");
        assertFalse(myLogin.registerUser());
        
        myLogin.setUserInfoRegister("Foo", "email@yo.com", "Mimiru", "Tsukasa", null);
        assertFalse(myLogin.registerUser());
    }
    
    @Test
    public void testRegisterUserForValid() {
        myLogin.setUserInfoRegister("Foo", "email@yo.com", "Mimiru", "Tsukasa", "Volunteer");
        assertTrue(myLogin.registerUser());
    }

    @Test
    public void testLoginUser() {
        fail("Not yet implemented");
    }

}
