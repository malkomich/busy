package busy.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.user.User;

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
@DatabaseSetup("../user/userSet.xml")
@DatabaseSetup("../role/roleSet.xml")
@DatabaseSetup("../schedule/serviceTypeSet.xml")
@DatabaseSetup("../schedule/serviceSet.xml")
public class BookingDBTest extends AbstractDBTest {

    @Autowired
    private BookingDaoImpl repository;

    private Schedule schedule;
    private User userBooking;

    @Before
    public void setUp() {

        schedule = new Schedule();
        schedule.setId(1);
        
        userBooking = new User();
        userBooking.setId(1);

    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithScheduleInvalid() {

        schedule.setId(INVALID_ID);

        repository.save(userBooking, schedule);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithUserInvalid() {

        userBooking.setId(INVALID_ID);

        repository.save(userBooking, schedule);
    }
    
    @Test
    public void insertSuccessfully() {

        assertTrue(schedule.getBookings().isEmpty());
        
        repository.save(userBooking, schedule);
        
        assertFalse(schedule.getBookings().isEmpty());
    }
}
