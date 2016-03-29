package busy.schedule;

import java.io.Serializable;
import java.sql.Time;

/**
 * Hour schedule model. It defines a time schedule in a specific day.
 * 
 * @author malkomich
 *
 */
public class HourSchedule implements Serializable {

    private static final long serialVersionUID = 6866084642788052617L;

    private int id;
    private Time startTime;
    private Time endTime;

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
