package busy.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Company;
import busy.util.OperationResult;
import busy.util.OperationResult.ResultCode;
import busy.util.SecureSetter;

/**
 * Test Cases for the ServiceTypeDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../company/categorySet.xml")
@DatabaseSetup("../company/companySet.xml")
public class ServiceTypeDBTest extends AbstractDBTest {

    private static final String NAME = "Engineer";

    @Autowired
    private ServiceTypeDaoImpl repository;

    private Company company;

    @Before
    public void setUp() {

        company = new Company();
        SecureSetter.setId(company, 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNameNull() {

        ServiceType serviceType = new ServiceType();
        serviceType.setCompany(company);

        repository.save(serviceType);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithCompanyNull() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);

        repository.save(serviceType);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNameAndCompanyDuplicated() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setCompany(company);

        repository.save(serviceType);

        serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setCompany(company);

        repository.save(serviceType);
    }

    @Test
    public void createSuccessfully() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setCompany(company);

        serviceType = repository.save(serviceType);
        
        assertNotNull(serviceType);
        assertNotNull(serviceType.getMaxBookingsPerRole());
        assertNotNull(serviceType.getDuration());
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    public void findByInvalidCompany() {

        SecureSetter.setId(company, INVALID_ID);

        List<ServiceType> serviceTypes = repository.findByCompany(company);

        assertTrue(serviceTypes.isEmpty());
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    public void findByCompanySuccessfully() {

        List<ServiceType> serviceTypes = repository.findByCompany(company);

        assertFalse(serviceTypes.isEmpty());
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    public void deleteNotExisting() {

        ServiceType serviceType = new ServiceType();
        SecureSetter.setId(serviceType, INVALID_ID);

        OperationResult deleted = repository.delete(serviceType);

        assertEquals(ResultCode.NOT_EXISTS, deleted.getCode());
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    @DatabaseSetup("../user/userSet.xml")
    @DatabaseSetup("../location/countrySet.xml")
    @DatabaseSetup("../location/citySet.xml")
    @DatabaseSetup("../location/addressSet.xml")
    @DatabaseSetup("../company/branchSet.xml")
    @DatabaseSetup("../role/roleSet.xml")
    @DatabaseSetup("../schedule/serviceSet.xml")
    @DatabaseSetup("../schedule/bookingSet.xml")
    public void deleteWithBookings() {

        ServiceType serviceType = new ServiceType();
        SecureSetter.setId(serviceType, 1);

        OperationResult deleted = repository.delete(serviceType);

        assertEquals(ResultCode.SERVICE_TYPE_WITH_BOOKINGS, deleted.getCode());
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    public void deleteSuccessfully() {

        ServiceType serviceType = new ServiceType();
        SecureSetter.setId(serviceType, 1);

        OperationResult deleted = repository.delete(serviceType);

        assertEquals(ResultCode.OK, deleted.getCode());
    }
}
