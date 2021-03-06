package busy.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.location.Address;
import busy.util.SecureSetter;

/**
 * Test Case for the BranchDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../location/countrySet.xml")
@DatabaseSetup("../location/citySet.xml")
@DatabaseSetup("../location/addressSet.xml")
@DatabaseSetup("../company/categorySet.xml")
@DatabaseSetup("../company/companySet.xml")
public class BranchDBTest extends AbstractDBTest {

    @Autowired
    private BranchDaoImpl repository;

    private Address address;
    private Company company;

    @Before
    public void setUp() {

        address = new Address();
        address.setId(1);

        company = new Company();
        company.setId(1);
    }

    // Write operation tests

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithCompanyNull() {

        Branch branch = new Branch();
        branch.setAddress(address);

        repository.save(branch);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithCompanyInvalid() {

        Branch branch = new Branch();

        Company invalidCompany = new Company();
        invalidCompany.setId(INVALID_ID);
        branch.setCompany(invalidCompany);

        branch.setAddress(address);

        repository.save(branch);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithAddressNull() {

        Branch branch = new Branch();
        branch.setCompany(company);

        repository.save(branch);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithAddressInvalid() {

        Branch branch = new Branch();
        branch.setCompany(company);

        Address invalidAddress = new Address();
        invalidAddress.setId(INVALID_ID);
        branch.setAddress(invalidAddress);

        repository.save(branch);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithPhoneTooLong() {

        Branch branch = new Branch();

        branch.setCompany(company);
        branch.setAddress(address);
        branch.setPhone("6543219876543");

        repository.save(branch);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithCompanyAndAddressDuplicated() {

        Branch branch1 = new Branch(company, address, "654321987");
        repository.save(branch1);

        Branch branch2 = new Branch(company, address, "654987123");
        repository.save(branch2);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertWithHeadquarterDuplicated() {

        Branch branch1 = new Branch(company, address, "654321987");
        SecureSetter.setAttribute(branch1, "setHeadquarters", Boolean.class, true);
        repository.save(branch1);

        Address address2 = new Address();
        address2.setId(2);
        Branch branch2 = new Branch(company, address2, "654987123");
        SecureSetter.setAttribute(branch2, "setHeadquarters", Boolean.class, true);
        repository.save(branch2);

    }

    @Test
    public void insertSuccessfull() {

        Branch branch = new Branch();

        branch.setCompany(company);
        branch.setAddress(address);

        repository.save(branch);
    }

    // Read operation tests

    @Test
    @DatabaseSetup("../company/branchSet.xml")
    public void findByInvalidCompany() {

        List<Branch> branches = repository.findByCompanyId(INVALID_ID);

        assertTrue(branches.isEmpty());
    }

    @Test
    @DatabaseSetup("../company/branchSet.xml")
    public void findByCompanySuccessfully() {

        List<Branch> branches = repository.findByCompanyId(1);

        assertEquals(2, branches.size());
    }
}
