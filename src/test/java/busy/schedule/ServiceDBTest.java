package busy.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.role.Role;
import busy.util.SecureSetter;

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

        role = new Role();
        SecureSetter.setId(role, 1);

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

        assertFalse(services.isEmpty());
        assertEquals(4, services.size());
    }

    @Test
    @DatabaseSetup("../schedule/serviceSet.xml")
    public void findByInvalidRole() {

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

        assertFalse(services.isEmpty());
        assertEquals(4, services.size());
    }

}
