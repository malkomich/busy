package busy.schedule;

import java.io.Serializable;
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

    public void setBranch(Branch branch) {
        // TODO Auto-generated method stub

    }

    public void setYear(int year) {
        // TODO Auto-generated method stub

    }

    public void setWeekSchedules(List<WeekSchedule> weekSchedules) {
        // TODO Auto-generated method stub

    }

    public void addWeekSchedule(WeekSchedule weekSchedule) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        // TODO Auto-generated method stub

    }
}
