package busy.booking;

import java.util.List;

import busy.user.User;

/**
 * Booking persistence interface.
 * 
 * @author malkomich
 *
 */
public interface BookingDao {

    /**
     * Gets the list of bookings made by a given user in the weeks specified.
     * 
     * @param user
     *            user attached to the bookings
     * @param weeks
     *            variable number of weeks to search for
     * @return The list of resultant bookings
     */
    List<Booking> findByUserAndWeeks(User user, int... weeks);

}
