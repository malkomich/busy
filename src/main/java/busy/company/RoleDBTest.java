package busy.company;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.user.User;
import busy.util.SecureSetter;

/**
 * Test Case for the RolDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../user/userSet.xml")
public class RoleDBTest extends AbstractDBTest {

	@Autowired
	protected RoleDaoImpl repository;
	
	private User user;
	private Company company;
	
	@Before
	public void setUp() {
		user = new User();
		SecureSetter.setId(user, 1);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPersonInvalid() {
		
		Role role = new Role();
		User userInvalid = new User();
		SecureSetter.setId(userInvalid, 2);
		role.setUser(userInvalid);
		role.setCompany(company);
		role.setWorkerName("Nombre");
		
		repository.save(role);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCompanyNull() {
		
		Role role = new Role();
		role.setUser(user);
		role.setWorkerName("Nombre");
		
		repository.save(role);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCompanyInvalid() {
		
		Role role = new Role();
		role.setUser(user);
		Company companyInvalid = new Company();
		SecureSetter.setId(companyInvalid, 2);
		role.setCompany(companyInvalid);
		role.setWorkerName("Nombre");
		
		repository.save(role);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithWorkerNameTooLong() {
		
		Role role = new Role();
		role.setCompany(company);
		role.setWorkerName("NombreLargoNombreLargoNombreLargoooO");
		
		repository.save(role);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPersonAndCompanyDuplicated() {
		
		Role role1 = new Role(user, company, "Nombre");
		Role role2 = new Role(user, company, "NombreDiferente");
		
		repository.save(role1);
		repository.save(role2);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCompanyAndWorkerNameDuplicated() {
		
		Role role1 = new Role(user, company, "Nombre");
		Role role2 = new Role(null, company, "Nombre");
		
		repository.save(role1);
		repository.save(role2);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPersonAndWorkerNameNull() {
		
		Role role = new Role();
		role.setCompany(company);
		
		repository.save(role);
	}
	
	@Test
	public void insertWithPersonSuccessfully() {
		
		Role role = new Role();
		role.setUser(user);
		role.setCompany(company);
		
		repository.save(role);
	}
	
	@Test
	public void insertWithWorkerNameSuccessfully() {
		
		Role role = new Role();
		role.setCompany(company);
		role.setWorkerName("Nombre");
		
		repository.save(role);
	}
	
	@Test
	public void insertWithPersonAndWorkerNameSuccessfully() {
		
		Role role = new Role();
		role.setUser(user);
		role.setCompany(company);
		role.setWorkerName("Nombre");
		
		repository.save(role);
	}
	
}
