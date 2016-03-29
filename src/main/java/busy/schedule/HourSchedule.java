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

    public int getId() {
        return id;
    }

    public void setStartTime(Time startTime) {
        // TODO Auto-generated method stub

    }

    public void setEndTime(Time endTime) {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

}
