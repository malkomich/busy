package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    
    public void setYear(int year) {
        this.year = year;
    }

    public void addWeekSchedule(WeekSchedule weekSchedule) {
        weekScheduleList.add(weekSchedule);
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }
}
