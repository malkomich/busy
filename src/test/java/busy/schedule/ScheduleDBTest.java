package busy.schedule;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.role.Role;
import busy.util.SecureSetter;

/**
 * Test Cases for the ScheduleDao implementation class.
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
public class ScheduleDBTest extends AbstractDBTest {

    @Autowired
    private ScheduleDaoImpl repository;

    private Service service;
    private Role role;

    @Before
    public void setUp() {

        service = new Service();
        service.setId(1);

        role = new Role();
        role.setId(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithServiceInvalid() {

        Schedule schedule = new Schedule();
        service.setId(INVALID_ID);
        schedule.setRole(role);

        repository.save(schedule, service.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithRoleNull() {

        Schedule schedule = new Schedule();

        repository.save(schedule, service.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithRoleInvalid() {

        Schedule schedule = new Schedule();
        role.setId(INVALID_ID);
        schedule.setRole(role);

        repository.save(schedule, service.getId());
    }
    
    @Test
    public void insertSuccessfully() {

        Schedule schedule = new Schedule();
        schedule.setRole(role);

        repository.save(schedule, service.getId());
    }

}
