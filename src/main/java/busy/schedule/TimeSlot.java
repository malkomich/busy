package busy.schedule;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import busy.role.Role;

public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 5941480462690666125L;

    private int id;
    private DateTime startTime;
    private Service service;
    private List<Schedule> schedules;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull(message = "{schedule.service.start_time.empty}")
    public LocalTime getStartTime() {
        return (startTime != null) ? startTime.toLocalTime() : null;
    }

    public DateTime getStartDateTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(LocalTime startTime) {
        if (startTime != null && service != null) {
            LocalDate date = (service.getDate() != null) ? service.getDate() : new LocalDate();
            this.startTime = date.toDateTime(startTime);
        }
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        if (startTime != null) {
            startTime = startTime.withDate(service.getDate());
        }
    }

    public List<Schedule> getSchedules() {

        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @NotEmpty(message = "{schedule.service.roles.empty}")
    public List<Role> getRoles() {

        List<Role> roles = new ArrayList<>();
        for (Schedule schedule : getSchedules()) {
            roles.add(schedule.getRole());
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {

        if (roles != null) {
            for (Role role : roles) {
                if (!containsRole(role)) {
                    Schedule schedule = new Schedule();
                    schedule.setRole(role);
                    addSchedule(schedule);
                }
            }
        }
    }

    public void addSchedule(Schedule schedule) {
        getSchedules().add(schedule);
    }

    public Timestamp getStartTimestamp() {
        return (startTime != null) ? new Timestamp(startTime.getMillis()) : null;
    }

    private boolean containsRole(Role role) {

        for (Schedule schedule : getSchedules()) {
            if (role.equals(schedule.getRole())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAvailable() {

        int maxBookings = service.getServiceType().getMaxBookingsPerRole();

        for (Schedule schedule : getSchedules()) {
            if (schedule.getBookings().size() < maxBookings) {
                return true;
            }
        }

        return false;
    }

    public List<Schedule> getAvailableSchedules() {

        int maxBookings = service.getServiceType().getMaxBookingsPerRole();

        List<Schedule> availableSchedules = new ArrayList<>();
        
        for (Schedule schedule : getSchedules()) {
            if (schedule.getBookings().size() < maxBookings) {
                availableSchedules.add(schedule);
            }
        }
        
        return availableSchedules;
    }

}
