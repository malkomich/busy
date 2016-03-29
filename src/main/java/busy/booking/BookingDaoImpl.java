package busy.booking;

import java.util.List;

import org.springframework.stereotype.Repository;

import busy.company.Branch;

/**
 * Booking persistence implementation for Database storing.
 * 
 * @author malkomich
 *
 */
@Repository
public class BookingDaoImpl implements BookingDao {

    /*
     * (non-Javadoc)
     * @see busy.booking.BookingDao#findByUserAndWeeks(busy.user.User, int[])
     */
    @Override
    public List<Booking> findByBranchAndWeeks(Branch branch, int... weeks) {
        // TODO Auto-generated method stub
        return null;
    }

}
