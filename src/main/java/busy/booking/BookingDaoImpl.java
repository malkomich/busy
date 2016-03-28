package busy.booking;

import java.util.List;

import busy.user.User;

/**
 * Booking persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
public class BookingDaoImpl implements BookingDao {

    /*
     * (non-Javadoc)
     * @see busy.booking.BookingDao#findByUserAndWeeks(busy.user.User, int[])
     */
    @Override
    public List<Booking> findByUserAndWeeks(User user, int... weeks) {
        // TODO Auto-generated method stub
        return null;
    }

}
