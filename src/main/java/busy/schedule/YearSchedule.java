package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import busy.company.Branch;

/**
 * Year schedule model. It keeps a reference of all week schedules for a year of a specific branch
 * of a company.
 * 
 * @author malkomich
 *
 */
public class YearSchedule implements Serializable {

    private static final long serialVersionUID = -4521495627905495857L;

    private int id;
    private Branch branch;
    private int year;
    private List<WeekSchedule> weekScheduleList;

    public YearSchedule() {
        weekScheduleList = new ArrayList<WeekSchedule>();
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<WeekSchedule> getWeekScheduleList() {
        return weekScheduleList;
    }

    public void addWeekSchedule(WeekSchedule weekSchedule) {
        weekScheduleList.add(weekSchedule);
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the sublist of week schedules with the given week numbers of year
     * 
     * @param weeks
     *            array of weeks of year requested
     * @return The list of resultant week schedules
     */
    public List<WeekSchedule> getWeekSchedules(int[] weeks) {

        Arrays.sort(weeks);
        List<WeekSchedule> weekScheduleList = new ArrayList<WeekSchedule>();
        for (WeekSchedule schedule : this.weekScheduleList) {
            if (Arrays.binarySearch(weeks, schedule.getWeekOfYear()) >= 0) {
                weekScheduleList.add(schedule);
            }
        }
        return weekScheduleList;
    }
}
