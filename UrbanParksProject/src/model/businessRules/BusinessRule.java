package model.businessRules;

/**
 * Business Rules are essential logic enforeced within the application. 
 * Classes implementing this interface will represent these rules.
 */
public abstract class BusinessRule {
	
	public BusinessRule()
	{
		//Do Nothing.
	}
	
	/**
	 * Perform a check of the business rule.
	 * 
	 * @return true if the business rule is satisfied, false otherwise.
	 */
	public boolean test(Object theTestedObject)
	{
		return false;
	}
}
