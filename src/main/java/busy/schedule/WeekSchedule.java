package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Week schedule model. It keeps a reference of all day schedules for a specific week of a year
 * schedule.
 * 
 * @author malkomich
 *
 */
public class WeekSchedule implements Serializable {

    private static final long serialVersionUID = 2435465415591273204L;

    private int id;
    private int weekOfYear;
    private boolean isDefault;
    private List<DaySchedule> dayScheduleList;

    public WeekSchedule() {
        dayScheduleList = new ArrayList<DaySchedule>();
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void addDaySchedule(DaySchedule daySchedule) {
        dayScheduleList.add(daySchedule);
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
