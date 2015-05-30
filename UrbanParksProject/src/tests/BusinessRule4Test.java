/**
 * 
 */
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.Job;
import model.businessRules.BusinessRule4;

import org.junit.Before;
import org.junit.Test;

public class BusinessRule4Test {

    BusinessRule4 myRule;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        myRule = new BusinessRule4();
    }

    /**
     * Test method for {@link model.businessRules.BusinessRule4#test(java.lang.Object[])}.
     */
    @Test
    public void testTestForSingleDayJob() {
        Job myJob = new Job(0, "Foo Park", 4, 4, 4, "09012015", "09012015",
                            "moverby@vivaldi.com", null);
        assertTrue(myRule.test(myJob));
    }

    /**
     * Test method for {@link model.businessRules.BusinessRule4#test(java.lang.Object[])}.
     */
    @Test
    public void testTestForDuoDayJob() {
        int day = BusinessRule4.MAX_DURATION;
        String dayStr = day < 10 ? "0" + day : "" + day;

        Job myJob = new Job(0, "Foo Park", 4, 4, 4, "09012015", "09" + dayStr + "2015",
                            "moverby@vivaldi.com", null);
        assertTrue(myRule.test(myJob));
    }

    /**
     * Test method for {@link model.businessRules.BusinessRule4#test(java.lang.Object[])}.
     */
    @Test
    public void testTestForTooLongJob() {
        int day = BusinessRule4.MAX_DURATION + 1;
        String dayStr = day < 10 ? "0" + day : "" + day;

        Job myJob = new Job(0, "Foo Park", 4, 4, 4, "09012015", "09" + dayStr + "2015",
                            "moverby@vivaldi.com", null);
        assertFalse(myRule.test(myJob));
    }
}
