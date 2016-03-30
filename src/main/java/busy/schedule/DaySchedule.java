package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Day schedule model. It keeps a reference of all hour schedules for a specific day of a week
 * schedule.
 * 
 * @author malkomich
 *
 */
public class DaySchedule implements Serializable {

    private static final long serialVersionUID = -1325712421867799828L;

    private int id;
    private int dayOfWeek;
    private WeekSchedule weekSchedule;
    private List<HourSchedule> hourScheduleList;

    public DaySchedule() {
        hourScheduleList = new ArrayList<HourSchedule>();
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setWeekSchedule(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public void addHourSchedule(HourSchedule hourSchedule) {
        hourScheduleList.add(hourSchedule);
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
