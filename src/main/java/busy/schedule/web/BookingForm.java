package busy.schedule.web;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import busy.schedule.Schedule;

public class BookingForm {

    private int timeSlotId;
    private DateTime dateTime;

    @NotNull(message = "{schedule.booking.role.empty}")
    private Schedule schedule;

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public void setTimeSlotId(String timeSlotId) {
        this.timeSlotId = Integer.parseInt(timeSlotId);
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
