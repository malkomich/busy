package busy.booking;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import busy.company.Branch;

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

    /*
     * (non-Javadoc)
     * @see busy.booking.BookingService#findBookingsByBranchAndYearAndMonth(busy.company.Branch,
     * int, int)
     */
    @Override
    public List<Booking> findBookingsByBranchAndYearAndMonth(Branch branch, int year, int month) {

        DateTime dateTime = new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(1);
        int firstWeek = dateTime.getWeekOfWeekyear();
        int lastWeek = dateTime.plusMonths(1).minusDays(1).getWeekOfWeekyear();

        int[] weeks = new int[lastWeek - firstWeek + 1];
        for (int i = firstWeek; i <= lastWeek; i++) {
            weeks[i - firstWeek] = i;
        }

        return bookingDao.findByBranchAndYearAndWeeks(branch, year, weeks);
    }

}
