package busy.booking;

import java.io.Serializable;

import busy.service.Service;
import busy.user.User;

/**
 * Booking model. It defines a booking from a user in a specific service.
 * 
 * @author malkomich
 *
 */
public class Booking implements Serializable {

    private static final long serialVersionUID = -710554949407300960L;

    private User user;
    private Service service;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
