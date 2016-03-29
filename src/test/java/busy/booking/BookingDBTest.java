package busy.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Branch;
import busy.util.SecureSetter;

/**
 * Test Cases for the BookingDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../company/categorySet.xml")
@DatabaseSetup("../company/companySet.xml")
@DatabaseSetup("../location/countrySet.xml")
@DatabaseSetup("../location/citySet.xml")
@DatabaseSetup("../location/addressSet.xml")
@DatabaseSetup("../company/branchSet.xml")
@DatabaseSetup("../schedule/scheduleSet.xml")
@DatabaseSetup("../booking/bookingSet.xml")
public class BookingDBTest extends AbstractDBTest {

    private static final int[] WEEKS = {1, 2};
    private static final int[] INVALID_WEEKS = {50};
    private static final int[] PARTIALLY_VALID_WEEKS = {1, 50};

    @Autowired
    private BookingDaoImpl repository;

    private Branch branch;

    @Before
    public void setUp() {

        branch = new Branch();
        SecureSetter.setId(branch, 1);
    }

    @Test
    public void findByInvalidBranch() {

        Branch wrongBranch = new Branch();
        SecureSetter.setId(wrongBranch, INVALID_ID);

        List<Booking> bookings = repository.findByBranchAndWeeks(wrongBranch, WEEKS);
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void findByInvalidWeeks() {

        List<Booking> bookings = repository.findByBranchAndWeeks(branch, INVALID_WEEKS);
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void findByPartiallyValidWeeks() {

        List<Booking> bookings = repository.findByBranchAndWeeks(branch, PARTIALLY_VALID_WEEKS);
        assertFalse(bookings.isEmpty());
    }

    @Test
    public void findByBranchAndWeeksSuccessfully() {

        List<Booking> bookings = repository.findByBranchAndWeeks(branch, WEEKS);
        assertFalse(bookings.isEmpty());
    }

}
