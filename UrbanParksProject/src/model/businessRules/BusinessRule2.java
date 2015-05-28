package model.businessRules;

import java.util.Calendar;
import java.util.List;

import model.Job;
import model.JobList;

/**
 * A job may not be added if the total number of pending jobs during that week (3 days on
 * either side
 * of the job days) is currently 5. In other words, during any consecutive 7 day period
 * there can be no
 * more than 5 jobs.
 */
public class BusinessRule2 {

    /**
     * The amount of time that constitutes a week.
     */
    public static final int LIMITING_DURATION = 7;

    /**
     * The maximum number of jobs in a week.
     */
    public static final int MAX_JOBS_IN_WEEK = 5;

    public boolean test(Job theJob, JobList theJobList) {
        // count increases for every other job that is within 3 days before start and 3
        // days after end.
        int count = 0;

        // 3 days before start and 3 days after end
        Calendar aStart = (Calendar) theJob.getStartDate().clone();

        aStart.add(Calendar.DAY_OF_MONTH, -1 * LIMITING_DURATION / 2);

        Calendar aEnd = (Calendar) theJob.getEndDate().clone();
        aEnd.add(Calendar.DAY_OF_MONTH, LIMITING_DURATION / 2);

        List<Job> aList = theJobList.getCopyList();
        for (Job j : aList) {


            long startDifference = j.getStartDate().getTimeInMillis() - aStart.getTimeInMillis();
            startDifference = convertMilliToDays(startDifference);
            long startDifference2 = aEnd.getTimeInMillis() - j.getStartDate().getTimeInMillis();
            startDifference2 = convertMilliToDays(startDifference2);
            
            // if ending date of j is within 3 days before theJob, increment count
            // if starting date of j is within 3 days after theJob, increment count
            if ((startDifference <= LIMITING_DURATION / 2 && startDifference >= 0)
                    || (startDifference2 <= LIMITING_DURATION / 2 && startDifference2 >= 0)) {
                count++;
            }
            
            if (j.getEndDate().equals(j.getStartDate()))
            {   
                // System.out.println("Count is " + count);
                if (count >= MAX_JOBS_IN_WEEK) {
                    return false;
                }
            }
            else
            {
                long endDifference = j.getEndDate().getTimeInMillis() - aStart.getTimeInMillis();
                endDifference = convertMilliToDays(endDifference);
                long endDifference2 = aEnd.getTimeInMillis() - j.getEndDate().getTimeInMillis();
                endDifference2 = convertMilliToDays(endDifference2);                
                
                // if ending date of j is within 3 days before theJob, increment count
                // if starting date of j is within 3 days after theJob, increment count
                // System.out.println("Count is " + count);
                if ((endDifference <= LIMITING_DURATION / 2 && endDifference >= 0)
                        || (endDifference2 <= LIMITING_DURATION / 2 && endDifference2 >= 0)) {
                    count++;
                }
                if (count >= MAX_JOBS_IN_WEEK) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Convert milliseconds to days.
     * 
     * @param theMilli
     *            the number of milliseconds
     * @return the number of days
     */
    private long convertMilliToDays(long theMilli) {
        return theMilli /= (3600 * 24 * 1000);
    }
}
