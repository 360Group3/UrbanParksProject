package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.DataPollster;
import model.Login;
import model.Schedule;
import model.UserList;
import model.Volunteer;

import org.junit.Before;
import org.junit.Test;

public class LoginTest {

    Login myLogin;
    
    UserList myUL;
    
    @Before
    public void setUp() {
        myLogin = new Login();
        
        myUL = new UserList();

        DataPollster.getInstance().setUserList(myUL);
        Schedule.getInstance().setUserList(myUL);
    }

    @Test
    public void testRegisterUserOnInvalidUserInfo() {
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
    public void testRegisterUserOnValidUserInfo() {
        myLogin.setUserInfoRegister("Foo", "email@yo.com", "Mimiru", "Tsukasa", "Volunteer");
        assertTrue(myLogin.registerUser());
    }

    @Test
    public void testLoginUserOnUserNotInUserList() {
        myLogin.setUserInfoRegister("Foo", "email@yo.com", "Mimiru", "Tsukasa", "Volunteer");
        assertFalse(myLogin.loginUser());
    }
    
    @Test
    public void testLoginUserOnUserThatExistsInUserList() {
        myUL.addNewUser(new Volunteer("email@yo.com", "Mimiru", "Tsukasa"));

        DataPollster.getInstance().setUserList(myUL);
        Schedule.getInstance().setUserList(myUL);
        
        myLogin.setUserInfoRegister("Foo", "email@yo.com", "Mimiru", "Tsukasa", "Volunteer");
        assertTrue(myLogin.loginUser());
    }
    
    /**
     * These are all emails that conform to the standard - Yes, really 
	 * - but are unsupported by our application.
     */
    @Test
    public void testCheckEmailFormatOnValidButUnsupportedEmails() {
        assertFalse(myLogin.checkEmailFormat("Abc\\@def@example.com"));
        assertFalse(myLogin.checkEmailFormat("Fred\\ Bloggs@example.com"));
        assertFalse(myLogin.checkEmailFormat("Joe.\\\\Blow@example.com"));
        assertFalse(myLogin.checkEmailFormat("\"Abc@def\"@example.com"));
        assertFalse(myLogin.checkEmailFormat("\"Fred Bloggs\"@example.com"));
        assertFalse(myLogin.checkEmailFormat("\"@LarryPage!\"@[8.8.8.8]"));
    }
    
    /**
     * These are all valid email addresses. Really.
     */
    @Test
    public void testCheckEmailFormatOnValidButWeirdEmails() {
        assertTrue(myLogin.checkEmailFormat("l@d"));
        assertTrue(myLogin.checkEmailFormat("user+mailbox@example.com"));
        assertTrue(myLogin.checkEmailFormat("customer/department=shipping@example.com"));
        assertTrue(myLogin.checkEmailFormat("$A12345@example.com"));
        assertTrue(myLogin.checkEmailFormat("!def!xyz%abc@example.com"));
        assertTrue(myLogin.checkEmailFormat("_somename@example.com"));
        assertTrue(myLogin.checkEmailFormat("this|that@boolean.engineering"));
        assertTrue(myLogin.checkEmailFormat("!areRights@email.domains"));
        assertTrue(myLogin.checkEmailFormat("!#$%&'*+-/=?^_`{|}~@d"));
        assertTrue(myLogin.checkEmailFormat("ThisIsATerriblyBigEmailAddressAndYou'reMakingLifeReallyDifficult"
                + "@YourWork.WhyAreYouSuckingTheLifeOutOfEveryoneYouWorkWith."
                + "ICannotBelieveHowInconsiderateYouAreWithYourExcessiveExtravagance."
                + "PleaseReportToHumanResourcesForSomeReeducationInTheArtOfNotBeingACompleteJerkToEveryone."
                + "ITakeThisVeryPersonallyAndIWantYouToFeelPain"));
    }
    
    /**
     * These are all valid email addresses.
     */
    @Test
    public void testCheckEmailFormatOnValidAndOrdinaryEmails() {
        assertTrue(myLogin.checkEmailFormat("moverby@gmail.com"));
        assertTrue(myLogin.checkEmailFormat("pattern@overstock.co.uk"));
        assertTrue(myLogin.checkEmailFormat("m.overby@overstock.co.uk"));
        assertTrue(myLogin.checkEmailFormat("dmonucco@buccaneer.technology"));
        assertTrue(myLogin.checkEmailFormat("mrclaus@northpole.christmas"));
        assertTrue(myLogin.checkEmailFormat("abc@uvw.xyz"));
        assertTrue(myLogin.checkEmailFormat("ArcherLoggins@the.danger.zone"));
    }

    /**
     * These are invalid email addresses.
     */
    @Test
    public void testCheckEmailFormatOnInvalidEmails() {
        assertFalse(myLogin.checkEmailFormat("NoAtSign"));
        assertFalse(myLogin.checkEmailFormat("moverby@gmail..com"));
        assertFalse(myLogin.checkEmailFormat("pattern@.overstock.co.uk"));
        assertFalse(myLogin.checkEmailFormat("m.overby@-overstock.co.uk"));
        assertFalse(myLogin.checkEmailFormat(".dmonucco@buccaneer.technology"));
        //Too long local
        assertFalse(myLogin.checkEmailFormat("TooLongLocal.ThisIsATerriblyBigEmailAddressAndYou'reMakingLifeReallyDifficult"
                + "@YourWork.WhyAreYouSuckingTheLifeOutOfEveryoneYouWorkWith."
                + "ICannotBelieveHowInconsiderateYouAreWithYourExcessiveExtravagance."
                + "PleaseReportToHumanResourcesForSomeReeducationInTheArtOfNotBeingACompleteJerkToEveryone."
                + "ITakeThisVeryPersonallyAndIWantYouToFeelPain"));
        //Too long domain
        assertFalse(myLogin.checkEmailFormat("ThisIsATerriblyBigEmailAddressAndYou'reMakingLifeReallyDifficult"
                + "@YourWork.WhyAreYouSuckingTheLifeOutOfEveryoneYouWorkWith."
                + "ICannotBelieveHowInconsiderateYouAreWithYourExcessiveExtravagance."
                + "PleaseReportToHumanResourcesForSomeReeducationInTheArtOfNotBeingACompleteJerkToEveryone."
                + "ITakeThisVeryPersonallyAndIWantYouToFeelPain.TooLongDomain"));
    }
    
    @Test
    public void testCheckEmailFormatOnNull() {
        assertFalse(myLogin.checkEmailFormat(null));
    }
    
    @Test
    public void testCheckEmailFormatOnEmptyString() {
        assertFalse(myLogin.checkEmailFormat(""));
    }
}
