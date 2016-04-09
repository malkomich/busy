package busy.booking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.service.Service;
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
@DatabaseSetup("../user/userSet.xml")
@DatabaseSetup("../role/roleSet.xml")
@DatabaseSetup("../schedule/serviceSet.xml")
@DatabaseSetup("../booking/bookingSet.xml")
public class BookingDBTest extends AbstractDBTest {

    private static final int NUM_SERVICES_BOOKED = 3;
    private static final int SERVICE_NOT_BOOKED = 4;

    @Autowired
    private BookingDaoImpl repository;

    private List<Service> serviceList;

    @Before
    public void setUp() {

        serviceList = new ArrayList<Service>();
        for (int i = 1; i <= NUM_SERVICES_BOOKED; i++) {
            Service service = new Service();
            SecureSetter.setId(service, i);
            serviceList.add(service);
        }
    }

    @Test
    public void findByNoServices() {

        Map<Service, List<Booking>> bookingMap = repository.findByServices(new ArrayList<Service>());
        assertTrue(bookingMap.isEmpty());
    }

    @Test
    public void findByInvalidService() {

        Service wrongService = new Service();
        SecureSetter.setId(wrongService, INVALID_ID);
        List<Service> wrongServiceList = new ArrayList<Service>();
        wrongServiceList.add(wrongService);

        Map<Service, List<Booking>> bookingMap = repository.findByServices(wrongServiceList);
        assertTrue(bookingMap.get(wrongService).isEmpty());
    }

    @Test
    public void findByNotBookedService() {

        Service notBookedService = new Service();
        SecureSetter.setId(notBookedService, SERVICE_NOT_BOOKED);
        serviceList.add(notBookedService);

        Map<Service, List<Booking>> bookingMap = repository.findByServices(serviceList);
        assertTrue(bookingMap.get(notBookedService).isEmpty());
    }

    @Test
    public void findByPartiallyValidServices() {

        Service wrongService = new Service();
        SecureSetter.setId(wrongService, INVALID_ID);
        serviceList.add(wrongService);

        Map<Service, List<Booking>> bookingMap = repository.findByServices(serviceList);
        assertEquals(NUM_SERVICES_BOOKED + 1, bookingMap.size());
        assertTrue(bookingMap.get(wrongService).isEmpty());
    }

    @Test
    public void findByServicesSuccessfully() {

        Map<Service, List<Booking>> bookingMap = repository.findByServices(serviceList);
        assertEquals(NUM_SERVICES_BOOKED, bookingMap.size());

        for (List<Booking> list : bookingMap.values()) {
            assertFalse(list.isEmpty());
        }
    }

}
