package busy.company;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import busy.AbstractDBTest;
import busy.util.SecureSetter;

/**
 * Test Case for the CompanyDao implementation class.
 * 
 * @author malkomich
 *
 */
@DatabaseSetup("../company/categorySet.xml")
public class CompanyDBTest extends AbstractDBTest {

	@Autowired
	private CompanyDaoImpl repository;
	
	private Category category;
	
	@Before
	public void setUp() {
		
		category = new Category();
		SecureSetter.setId(category, 1);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithTradeNameTooLong() {
		
		Company company = new Company();
		company.setTradeName("Abcdefghijklmnñopqrstuvwxyz Abc");
		company.setBusinessName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithBusinessNameNull() {
		
		Company company = new Company();
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithBusinessNameTooLong() {
		
		Company company = new Company();
		company.setBusinessName("Abcdefghijklmnñopqrstuvwxyz Abcdefghijklmnñopqrstuv");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithBusinessNameDuplicated() {
		
		Company company1 = new Company("Boom", "Boom S.A.", "jefe@boom.com", "B12345678", category);
		Company company2 = new Company("Boom", "Boom S.A.", "jefe2@boom.com", "B12345679", category);
		
		repository.save(company1);
		repository.save(company2);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailNull() {
		
		Company company = new Company();
		company.setBusinessName("Boom S.A.");
		company.setCif("B12345678");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailTooLong() {
		
		Company company = new Company();
		company.setBusinessName("Boom S.A.");
		company.setEmail("abcdefghijklmnñopqrstuvwxyz_abcdefghijkl@domain.com");
		company.setCif("B12345678");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailDuplicated() {
		
		Company company1 = new Company("Boom", "Boom S.A.", "jefe@boom.com", "B12345678", category);
		Company company2 = new Company("Boom", "Boom S.L.", "jefe@boom.com", "B12345679", category);
		
		repository.save(company1);
		repository.save(company2);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCifTooLong() {
		
		Company company = new Company();
		company.setBusinessName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B123456789");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCifDuplicated() {
		
		Company company1 = new Company("Boom", "Boom S.A.", "jefe@boom.com", "B12345678", category);
		Company company2 = new Company("Boom", "Boom S.L.", "jefe2@boom.com", "B12345678", category);
		
		repository.save(company1);
		repository.save(company2);
	}
	
	@Test
	public void insertSuccessfully() {
		
		Company company = new Company();
		company.setTradeName("Boom");
		company.setBusinessName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		company.setCategory(category);
		
		repository.save(company);
	}
	
	@Test
	public void updateCompanyNotExists() {
		
		Company company = new Company();
		SecureSetter.setId(company, INVALID_ID);
		company.setBusinessName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		
		repository.save(company);
		
		assertFalse(repository.exists(company));
	}
	
	@Test
	public void activateCompany() {
		
		Company company = new Company();
		company.setBusinessName("Boom S.A.");
		company.setEmail("jefe@boom.com");
		company.setCif("B12345678");
		
		repository.save(company);
		
		SecureSetter.setAttribute(company, "setActive", Boolean.class, true);
		
		repository.save(company);
		
		assertTrue(repository.findByCif("B12345678").isActive());
	}
}
