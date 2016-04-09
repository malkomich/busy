package busy.booking;

import java.io.Serializable;

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
    private Integer serviceId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

}
