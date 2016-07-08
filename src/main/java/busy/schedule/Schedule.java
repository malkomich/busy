package busy.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import busy.role.Role;
import busy.user.User;

/**
 * Schedule model. It represent a service unitary scheduled for a specific role.
 * 
 * @author malkomich
 *
 */
public class Schedule implements Serializable {

    private static final long serialVersionUID = 5167696953323992336L;

    private int id;
    private Role role;

    private List<User> bookings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getBookings() {
        if(bookings == null) {
            bookings = new ArrayList<>();
        }
        return bookings;
    }
    
    public void setBookings(List<User> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(User user) {
        getBookings().add(user);
    }

    public Integer getRoleId() {
        return (role != null) ? role.getId() : null;
    }

}
