package busy.booking;

import java.util.List;

import busy.company.Branch;

/**
 * Booking persistence interface.
 * 
 * @author malkomich
 *
 */
public interface BookingDao {

    /**
     * Gets the list of bookings made the weeks specified in the given branch.
     * 
     * @param branch
     *            branch attached to the bookings
     * @param year
     *            the year which the month belong to
     * @param weeks
     *            variable number of weeks to search for
     * @return The list of resultant bookings
     */
    List<Booking> findByBranchAndYearAndWeeks(Branch branch, int year, int... weeks);

}
