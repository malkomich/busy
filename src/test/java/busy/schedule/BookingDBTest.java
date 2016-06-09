package busy.schedule;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.user.User;
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
        SecureSetter.setId(schedule, 1);
        
        userBooking = new User();
        SecureSetter.setId(userBooking, 1);

    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithScheduleInvalid() {

        SecureSetter.setId(schedule, INVALID_ID);

        repository.save(schedule, userBooking);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithUserInvalid() {

        SecureSetter.setId(userBooking, INVALID_ID);

        repository.save(schedule, userBooking);
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void insertSuccessfully() {

        repository.save(schedule, userBooking);
    }
}
