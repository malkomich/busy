package busy.schedule;

import busy.user.User;

/**
 * Booking persistence interface.
 * 
 * @author malkomich
 *
 */
public interface BookingDao {

    /**
     * Persists a new booking or update an existing one.
     * 
     * @param schedule
     *            the schedule object to be booked
     * @param userBooking
     *            the user which will be assigned to the booking
     */
    void save(Schedule schedule, User userBooking);

}
