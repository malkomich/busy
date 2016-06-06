package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import busy.role.Role;
import busy.user.User;
import busy.util.Replicable;

/**
 * Schedule model. It represent a service unitary scheduled for a specific role.
 * 
 * @author malkomich
 *
 */
public class Schedule implements Serializable, Replicable<Schedule> {

    private static final long serialVersionUID = 5167696953323992336L;

    private int id;
    private Role role;

    private List<User> bookings;

    public Schedule() {
        bookings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getBookings() {
        return bookings;
    }

    public void addBooking(User user) {
        bookings.add(user);
    }

    public Integer getRoleId() {
        return (role != null) ? role.getId() : null;
    }

    @Override
    public Schedule replicate() {

        Schedule sTarget = new Schedule();
        sTarget.role = role;
        return sTarget;
    }

}
