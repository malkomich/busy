package busy.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Branch;
import busy.role.Role;
import busy.util.SecureSetter;

/**
 * Test Cases for the ServiceDao implementation class.
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
public class ServiceDBTest extends AbstractDBTest {

    @Autowired
    private ServiceDaoImpl repository;

    private DateTime initDay, endDay;
    private Role role;
    private ServiceType serviceType;

    @Before
    public void setUp() {

        initDay = new DateTime(2016, 1, 1, 0, 0);
        endDay = new DateTime(2016, 2, 1, 0, 0);

        Branch branch = new Branch();
        role = new Role();
        SecureSetter.setId(role, 1);
        SecureSetter.setAttribute(role, "setManager", Boolean.class, true);
        SecureSetter.setId(branch, 1);
        role.setBranch(branch);

        serviceType = new ServiceType();
        SecureSetter.setId(serviceType, 1);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findWithNullInitDay() {

        repository.findBetweenDays(null, endDay, role, serviceType);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findWithNullEndDay() {

        repository.findBetweenDays(initDay, null, role, serviceType);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findWithNullRole() {

        repository.findBetweenDays(initDay, endDay, null, serviceType);
    }

    /**
     * With null service type, it should find services of all service types.
     */
    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findWithNullServiceType() {

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, null);

        assertEquals(1, services.size());

        List<TimeSlot> timeSlots = services.get(0).getTimeSlots();

        assertEquals(4, timeSlots.size());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findByInvalidRole() {

        role = new Role();
        SecureSetter.setId(role, INVALID_ID);

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, serviceType);

        assertTrue(services.isEmpty());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findBetweenInvalidDays() {

        List<Service> services = repository.findBetweenDays(endDay, initDay, role, serviceType);

        assertTrue(services.isEmpty());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findBetweenDateTimesWithNoServices() {

        initDay = new DateTime(2017, 1, 1, 0, 0);
        endDay = new DateTime(2017, 2, 1, 0, 0);

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, serviceType);

        assertTrue(services.isEmpty());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findByInvalidServiceType() {

        SecureSetter.setId(serviceType, INVALID_ID);

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, serviceType);

        assertTrue(services.isEmpty());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findServicesSuccessfully() {

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, serviceType);

        assertEquals(1, services.size());
        
        List<TimeSlot> timeSlots = services.get(0).getTimeSlots();

        assertEquals(4, timeSlots.size());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findNotBookedServices() {

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, serviceType);

        int bookings = 0;
        for (Service service : services) {
            for (TimeSlot timeSlot : service.getTimeSlots()) {
                for (Schedule schedule : timeSlot.getSchedules()) {
                    bookings += schedule.getBookings().size();
                }
            }
        }

        assertEquals(0, bookings);
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    @DatabaseSetup("../schedule/bookingSet.xml")
    public void findBookedServicesSuccessfully() {

        List<Service> services = repository.findBetweenDays(initDay, endDay, role, serviceType);

        int bookings = 0;
        for (Service service : services) {
            for (TimeSlot timeSlot : service.getTimeSlots()) {
                for (Schedule schedule : timeSlot.getSchedules()) {
                    bookings += schedule.getBookings().size();
                }
            }
        }

        assertEquals(3, bookings);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithServiceTypeNull() {

        Service service = new Service();

        repository.save(service);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithServiceTypeInvalid() {

        Service service = new Service();
        SecureSetter.setId(serviceType, INVALID_ID);
        service.setServiceType(serviceType);

        repository.save(service);
    }

    @Test
    public void insertSuccessfully() {

        Service service = new Service();
        service.setServiceType(serviceType);

        repository.save(service);
    }

}
