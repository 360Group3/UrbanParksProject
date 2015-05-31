package model.businessRules;

import java.util.GregorianCalendar;

import model.Job;

/**
 * A job may not be scheduled that lasts more than two days.
 */
public class BusinessRule4 {

    /**
     * The maximum numbers of days a job can last.
     */
    public static final int MAX_DURATION = 2;

    public boolean test(Job theJob) {
        GregorianCalendar tooLong = (GregorianCalendar) theJob.getStartDate().clone();
        tooLong.add(GregorianCalendar.DATE, MAX_DURATION);

        return theJob.getEndDate().before(tooLong);
    }
}
