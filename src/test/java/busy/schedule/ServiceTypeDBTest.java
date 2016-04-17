package busy.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Company;
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

        repository.save(serviceType);
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

        boolean deleted = repository.delete(serviceType);

        assertFalse(deleted);
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    @DatabaseSetup("../user/userSet.xml")
    @DatabaseSetup("../booking/bookingSet.xml")
    public void deleteWithBookings() {

        ServiceType serviceType = new ServiceType();
        SecureSetter.setId(serviceType, 1);

        boolean deleted = repository.delete(serviceType);

        assertFalse(deleted);
    }

    @Test
    @DatabaseSetup("../schedule/serviceTypeSet.xml")
    public void deleteSuccessfully() {

        ServiceType serviceType = new ServiceType();
        SecureSetter.setId(serviceType, 1);

        boolean deleted = repository.delete(serviceType);

        assertTrue(deleted);
    }
}
