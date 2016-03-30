package busy.booking;

import java.io.Serializable;

import busy.schedule.HourSchedule;
import busy.user.User;

/**
 * Booking model. It defines a booking from a user in a specific hour schedule.
 * 
 * @author malkomich
 *
 */
public class Booking implements Serializable {

    private static final long serialVersionUID = -710554949407300960L;

    private User user;
    private HourSchedule hourSchedule;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HourSchedule getHourSchedule() {
        return hourSchedule;
    }

    public void setHourSchedule(HourSchedule hourSchedule) {
        this.hourSchedule = hourSchedule;
    }

}
