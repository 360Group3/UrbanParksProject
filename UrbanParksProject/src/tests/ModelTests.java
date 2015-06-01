package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.model.AdministratorTest;
import tests.model.DataPollsterTest;
import tests.model.JobListTest;
import tests.model.JobTest;
import tests.model.LoginTest;
import tests.model.ParkManagerTest;
import tests.model.SaveLoadTest;
import tests.model.ScheduleTest;
import tests.model.UserListTest;
import tests.model.VolunteerTest;

@RunWith(Suite.class)
@SuiteClasses({ AdministratorTest.class, DataPollsterTest.class, JobListTest.class,
        JobTest.class, LoginTest.class, ParkManagerTest.class, SaveLoadTest.class,
        ScheduleTest.class, UserListTest.class, VolunteerTest.class })
public class ModelTests {

}
