package busy.company;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import busy.AbstractDBTest;

/**
 * Test Case for the CompanyDao implementation class.
 * 
 * @author malkomich
 *
 */
public class CompanyDBTest extends AbstractDBTest {

	@Autowired
	protected CompanyDaoImpl repository;
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithNameNull() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithNameTooLong() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailNull() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailTooLong() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithEmailDuplicated() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCifTooLong() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithCifDuplicated() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithPhoneTooLong() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithActiveNull() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithAddressNull() {
		
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void insertWithAddressInvalid() {
		
	}
	
	@Test
	public void insertSuccessfully() {
		
	}
}
