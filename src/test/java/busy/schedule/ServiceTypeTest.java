package busy.schedule;

import org.junit.Before;
import org.junit.Test;

import busy.company.Company;

public class ServiceTypeTest {

    private static final String NAME = "Engineer";
    private static final int MAX_DURATION = 1440; // 60min*24h

    private Company company;

    @Before
    public void setUp() {

        company = new Company();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithInvalidBookingsPerRole() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setCompany(company);
        serviceType.setMaxBookingsPerRole(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithInvalidDuration() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setCompany(company);
        serviceType.setDuration(0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createWithHighDuration() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setDescription("A description");
        serviceType.setMaxBookingsPerRole(2);
        serviceType.setDuration(MAX_DURATION + 1);
        serviceType.setCompany(company);
    }

    @Test
    public void createSuccessfully() {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(NAME);
        serviceType.setDescription("A description");
        serviceType.setMaxBookingsPerRole(2);
        serviceType.setDuration(MAX_DURATION);
        serviceType.setCompany(company);
    }

}
