package busy.role;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Branch;
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Test Case for the RolDao implementation class.
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
public class RoleDBTest extends AbstractDBTest {

    @Autowired
    protected RoleDaoImpl repository;

    private User user;
    private Branch branch;

    @Before
    public void setUp() {

        user = new User();
        SecureSetter.setId(user, 1);

        branch = new Branch();
        SecureSetter.setId(branch, 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithPersonInvalid() {

        Role role = new Role();

        User userInvalid = new User();
        SecureSetter.setId(userInvalid, INVALID_ID);
        role.setUser(userInvalid);

        role.setBranch(branch);

        repository.save(role);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithBranchNull() {

        Role role = new Role();
        role.setUser(user);

        repository.save(role);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithBranchInvalid() {

        Role role = new Role();
        role.setUser(user);
        Branch branchInvalid = new Branch();
        SecureSetter.setId(branchInvalid, INVALID_ID);
        role.setBranch(branchInvalid);

        repository.save(role);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithActivityTooLong() {

        Role role = new Role();
        role.setUser(user);
        role.setBranch(branch);
        role.setActivity("Abcdefghijklmnñopqrstuvwxyz Abc");

        repository.save(role);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithPersonAndBranchDuplicated() {

        Role role1 = new Role(user, branch, "Profesor de guitarra");
        Role role2 = new Role(user, branch, "Limpiabotas");

        repository.save(role1);
        repository.save(role2);
    }

    @Test
    public void insertWithPersonSuccessfully() {

        Role role = new Role();
        role.setUser(user);
        role.setBranch(branch);

        repository.save(role);
    }

    @Test
    public void insertWithoutPersonSuccessfully() {

        Role role = new Role();
        role.setBranch(branch);

        repository.save(role);
    }

}
