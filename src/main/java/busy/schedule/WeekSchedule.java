package busy.schedule;

import java.io.Serializable;

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

    public int getId() {
        return id;
    }

    public void setWeekOfYear(int weekOfYear) {
        // TODO Auto-generated method stub

    }

    public void setDefault(boolean isDefault) {
        // TODO Auto-generated method stub

    }

    public void addDaySchedule(DaySchedule daySchedule) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
