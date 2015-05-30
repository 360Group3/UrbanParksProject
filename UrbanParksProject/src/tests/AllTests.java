package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdministratorTest.class, BusinessRule1Test.class,
		BusinessRule2Test.class, BusinessRule3Test.class,
		BusinessRule4Test.class, BusinessRule5Test.class,
		BusinessRule6Test.class, BusinessRule7Test.class,
		BusinessRule8Test.class, /*DataPollsterTest.class, */JobTest.class,
		ParkManagerTest.class, SaveLoadTest.class, ScheduleTest.class,
		UserListTest.class, VolunteerTest.class })
public class AllTests {

}
