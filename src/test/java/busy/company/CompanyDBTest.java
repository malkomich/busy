package busy.company;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.location.Address;
import busy.util.SecureSetter;

/**
 * Test Case for the CompanyDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../location/countrySet.xml")
@DatabaseSetup("../location/citySet.xml")
@DatabaseSetup("../location/addressSet.xml")
public class CompanyDBTest extends AbstractDBTest {

	@Autowired
	protected CompanyDaoImpl repository;
	
	private Address address;
	
	@Before
	public void setUp() {
		address = new Address();
		SecureSetter.setId(address, 1);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithNameNull() {
		
		Company company = new Company();
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setPhone("666543210");
		company.setAddress(address);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithNameTooLong() {
		
		Company company = new Company();
		company.setName("Abcdefghijklmnñopqrstuvwxyz Abcdefghijklmnñopqrstuv");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setPhone("666543210");
		company.setAddress(address);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailNull() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setCif("B12345678");
		company.setPhone("666543210");
		company.setAddress(address);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailTooLong() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setEmail("abcdefghijklmnñopqrstuvwxyz_abcdefghijkl@domain.com");
		company.setCif("B12345678");
		company.setPhone("666543210");
		company.setAddress(address);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailDuplicated() {
		
		Company company1 = new Company("Boom S.A.", "jefe@boom.com", "B12345678", "666543210", address);
		Company company2 = new Company("Boom S.A.", "jefe@boom.com", "B12345679", "666543210", address);
		
		repository.save(company1);
		repository.save(company2);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCifTooLong() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B123456789");
		company.setPhone("666543210");
		company.setAddress(address);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCifDuplicated() {
		
		Company company1 = new Company("Boom S.A.", "jefe@boom.com", "B12345678", "666543210", address);
		Company company2 = new Company("Boom S.A.", "jefe2@boom.com", "B12345678", "666543210", address);
		
		repository.save(company1);
		repository.save(company2);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPhoneTooLong() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setPhone("6665432109999");
		company.setAddress(address);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithAddressNull() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setPhone("666543210");
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithAddressInvalid() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setPhone("666543210");
		Address addressInvalid = new Address();
		SecureSetter.setId(addressInvalid, 2);
		company.setAddress(addressInvalid);
		
		repository.save(company);
	}
	
	@Test
	public void insertSuccessfully() {
		
		Company company = new Company();
		company.setName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setPhone("666543210");
		company.setAddress(address);
		
		repository.save(company);
	}
}
