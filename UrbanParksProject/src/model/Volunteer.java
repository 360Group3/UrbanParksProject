package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This is a volunteer.
 * 
 * @author Reid Thompson
 * @author Arsh Singh
 * @version 5.10.2015
 */
public class Volunteer extends User {

	public Volunteer(String theEmail, String theFirstName, String theLastName) {
		super(theFirstName, theLastName, theEmail);
		super.setUserType("Volunteer");
	}

	@Override
	public boolean equals(Object theO)
	{
		if (!(theO instanceof Volunteer))
			return false;

		Volunteer theOther = (Volunteer) theO;

		return (super.getFirstName().equals(theOther.getFirstName())
				&& super.getLastName().equals(theOther.getLastName())) 
				|| super.getEmail().equals(theOther.getEmail());
	}

	@Override
	public String toString()
	{
		return super.getFirstName() + " " + super.getLastName();
	}

}
