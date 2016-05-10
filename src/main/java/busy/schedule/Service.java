package busy.schedule;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import busy.role.Role;
import busy.user.User;

public class Service implements Serializable {

    private static final long serialVersionUID = -811217121096779784L;

    private int id;
    private DateTime startTime;
    private ServiceType serviceType;
    private Role role;
    private List<User> bookings;

    public Service() {
        bookings = new ArrayList<>();
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public List<User> getBookings() {
        return bookings;
    }

    public void addBooking(User user) {
        bookings.add(user);
    }

    public Timestamp getStartTimestamp() {
        return (startTime != null) ? new Timestamp(startTime.getMillis()) : null;
    }

    public Integer getServiceTypeId() {
        return (serviceType != null) ? serviceType.getId() : null;
    }

    public Integer getRoleId() {
        return (role != null) ? role.getId() : null;
    }

}
