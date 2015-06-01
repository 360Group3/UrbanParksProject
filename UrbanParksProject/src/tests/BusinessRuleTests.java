package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.businessRules.BusinessRule1Test;
import tests.businessRules.BusinessRule2Test;
import tests.businessRules.BusinessRule3Test;
import tests.businessRules.BusinessRule4Test;
import tests.businessRules.BusinessRule5Test;
import tests.businessRules.BusinessRule6Test;
import tests.businessRules.BusinessRule7Test;
import tests.businessRules.BusinessRule8Test;

@RunWith(Suite.class)
@SuiteClasses({ BusinessRule1Test.class, BusinessRule2Test.class,
        BusinessRule3Test.class, BusinessRule4Test.class, BusinessRule5Test.class,
        BusinessRule6Test.class, BusinessRule7Test.class, BusinessRule8Test.class })
public class BusinessRuleTests {

}
