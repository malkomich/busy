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
     * Persists a new booking.
     * 
     * @param userBooking
     *            the user which will be assigned to the booking
     * @param schedule
     *            the schedule object to be booked
     */
    void save(User userBooking, Schedule schedule);

}
