package busy.role;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.company.Branch;
import busy.user.User;

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
        user.setId(1);

        branch = new Branch();
        branch.setId(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithPersonInvalid() {

        Role role = new Role();

        User userInvalid = new User();
        userInvalid.setId(INVALID_ID);
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
        branchInvalid.setId(INVALID_ID);
        role.setBranch(branchInvalid);

        repository.save(role);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithPersonAndBranchDuplicated() {

        Role role1 = new Role(user, branch);
        Role role2 = new Role(user, branch);

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
