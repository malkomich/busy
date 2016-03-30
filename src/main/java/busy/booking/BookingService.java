package busy.booking;

import java.util.List;

import busy.company.Branch;

/**
 * Booking service logic interface. Connects the UI Application layer with the Persistence one,
 * according to the business's processes and workflows.
 * 
 * @author malkomich
 *
 */
public interface BookingService {

    /**
     * Gets all the bookings in a branch of the specific month of a year.
     * 
     * @param branch
     *            the branch attached to the bookings
     * @param year
     *            the year which the month belong to
     * @param month
     *            the month which bookings are requested
     * @return The list of resultant bookings
     */
    List<Booking> findBookingsByBranchAndYearAndMonth(Branch branch, int year, int month);

}
