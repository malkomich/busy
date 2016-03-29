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
     * Gets the list of bookings made by a given user in the weeks specified.
     * 
     * @param branch
     *            branch attached to the bookings
     * @param weeks
     *            variable number of weeks to search for
     * @return The list of resultant bookings
     */
    List<Booking> findByBranchAndWeeks(Branch branch, int... weeks);

}
