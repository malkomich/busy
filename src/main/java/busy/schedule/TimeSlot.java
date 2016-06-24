package busy.schedule;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalTime;

public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 5941480462690666125L;

    private int id;
    
    @NotNull(message = "{schedule.service.start_time.empty}")
    private LocalTime startTime;
    private Service service;
    
    @NotEmpty(message= "{schedule.service.roles.empty}")
    private List<Schedule> schedules;

    public TimeSlot() {
        schedules = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public Object getStartTimestamp() {
        return (startTime != null) ? new Timestamp(startTime.getMillisOfSecond()) : null;
    }

}
