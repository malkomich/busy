package busy.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Booking service logic implementation.
 * 
 * @author malkomich
 *
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    public void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

}
