package busy.schedule;

import java.util.List;

import busy.company.Branch;

/**
 * Schedule persistence interface.
 * 
 * @author malkomich
 *
 */
public interface ScheduleDao {

    /**
     * Gets the schedule for a complete year given the year and the branch attached to the schedule.
     * 
     * @param branch
     *            the branch attached to the schedule
     * @param year
     *            the year requested
     * @return The resultant year schedule
     */
    YearSchedule findYearFromBranch(Branch branch, int year);

    /**
     * Gets the list of week schedules given the year and a variable number of weeks of year.
     * 
     * @param branch
     *            the branch attached to the schedules
     * @param year
     *            the year requested
     * @param weeks
     *            the weeks which schedules are wanted
     * @return The list of resultant week schedules
     */
    List<WeekSchedule> findWeeksFromBranch(Branch branch, int year, int... weeks);

    /**
     * Gets the week schedule selected as the default one.
     * 
     * @param branch
     *            the branch which default default week schedule is attached to
     * @return The resultant default week schedule
     */
    WeekSchedule findDefaultWeek(Branch branch);

}
