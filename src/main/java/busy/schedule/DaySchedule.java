package busy.schedule;

import java.io.Serializable;

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
    
    public void setDayOfWeek(int dayOfWeek) {
        // TODO Auto-generated method stub
        
    }

    public int getId() {
        return id;
    }

    public void addHourSchedule(HourSchedule hourSchedule) {
        // TODO Auto-generated method stub
        
    }
    
    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
